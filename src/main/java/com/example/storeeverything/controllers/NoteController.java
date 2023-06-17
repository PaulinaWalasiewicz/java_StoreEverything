package com.example.storeeverything.controllers;
import com.example.storeeverything.Repository.CategoryRepository;
import com.example.storeeverything.Repository.NoteRepository;
import com.example.storeeverything.Repository.UserRepository;
import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.Note;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class NoteController {
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;


    private String GetUserID(Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String current_user_name= authentication.getName();
        String currentUserID= userRepository.findUserByUsername(current_user_name).getId();
        model.addAttribute("currentUserId",currentUserID);
        return  currentUserID;
    }
    @GetMapping("/")
    public String index(Model model,  HttpServletRequest request, HttpServletResponse response){

        String currentUserID= GetUserID(model);
        String sortDir=null;
        String sortType=null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sortDirection")) {
                    sortDir = cookie.getValue();

                }
                if (cookie.getName().equals("sortType")) {
                    sortType = cookie.getValue();
                }


            }
        }

        if (sortDir == null ){sortDir = "asc";}

        if (sortType == null) {
            // If the sort direction is still null, set a default value
            sortType = "date";
        }
        return sortedView(model,request,response,currentUserID,sortType,sortDir);
    }



    @GetMapping("/notes/")
    public String sortedView(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "user_id") String user_id, @RequestParam(value = "sort",defaultValue ="title") String sort, @RequestParam(value = "sortDir",defaultValue = "asc") String sortDir){


        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sortDirection")) {
                    // Change the value of the cookie
                    cookie.setMaxAge(0);

                    response.addCookie(cookie);
                }
            }
        }

            // Set the sorting direction in a cookie
            Cookie cookie_dir = new Cookie("sortDirection", sortDir);
            cookie_dir.setMaxAge(86400); // Cookie expiration time (in seconds)
            response.addCookie(cookie_dir);

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sortType")) {
                    // Change the value of the cookie
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
               }
            }
        }

            // Set the sorting type in a cookie
            Cookie cookie_dt = new Cookie("sortType", sort);
            cookie_dt.setMaxAge(86400); // Cookie expiration time (in seconds)
            response.addCookie(cookie_dt);




        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("newNote",new Note());


        List<Note> notes = new ArrayList();
        if(sort.equals("title")) {

            if (sortDir.equals("asc")) {
                notes = noteRepository.findByUserOrderByTitleAsc(user_id);
            }
            else {
                notes = noteRepository.findByUserOrderByTitleDesc(user_id);
            }
        } else if (sort.equals("category")) {
            List<Note> notes_temp = noteRepository.findByUserOrderByCategoryAsc(user_id);
            Map<String, Integer> categoryCounts = new LinkedHashMap<>();

//            count how many times categories are used
            for (Note n :notes_temp) {
                categoryCounts.put(String.valueOf(n.getCategory().getName()),categoryCounts.getOrDefault(String.valueOf(n.getCategory().getName()),0)+1);
            }

//            sort categories ascending or descending
            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(categoryCounts.entrySet());
            if (sortDir.equals("asc")) {
                Collections.sort(sortedEntries, Comparator.comparing(Map.Entry::getValue));

            }
            else {
                Collections.sort(sortedEntries, Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()));

            }
//            get sorted notes based on quantity of categories
            for (Map.Entry<String, Integer> entry : sortedEntries) {
                String category = entry.getKey();
                notes.addAll(notes_temp.stream().filter(n-> n.getCategory().getName().equals(category))
                        .collect(Collectors.toList()));

            }

        }
        else if (sort.equals("date")) {
            if (sortDir.equals("asc")) {
                notes = noteRepository.findByUserIdOrderByCreatedAtAsc(user_id);
            }
            else {
                notes = noteRepository.findByUserIdOrderByCreatedAtDesc(user_id);
            }

        }
//        System.out.println(notes);
        model.addAttribute("any",notes);
        model.addAttribute("sort",sort);
        model.addAttribute("sortDir",sortDir);
        String checkDirection = sortDir.equals("asc") ?"desc":"asc";
        model.addAttribute("checkDirection",checkDirection);
        model.addAttribute("currentUserId",GetUserID(model));

        return "index1";
    }

    @GetMapping("/addNote")
    public  void addNote(Model model){
        model.addAttribute("newNote",new Note());
    }

    @PostMapping("/saveNote")
    public String savenote(Model model,@ModelAttribute Note note){

        //TODO NOT SEING CHOOSEN CATEGORY
        if(note.getCategory() == null){
            note.setCategory(categoryRepository.findCategoryByName("Home"));
        }
        note.setCreatedAt(LocalDateTime.now());
        if(note.getUser() == null){
            note.setUser(userRepository.findUserById(GetUserID(model)));
        }

        noteRepository.save(note);
        return "redirect:/";
    }

    @RequestMapping ("/updateNote/{id}")
    @ResponseBody
    public Note updateNote(@PathVariable("id") String id,Model model){
        Note returnedNote = noteRepository.findNoteById(id);
        return returnedNote;

    }
    @PostMapping("/deleteNote/{id}")
    public String deleteNote(@PathVariable String id){
        noteRepository.delete(noteRepository.findNoteById(id));
        return "redirect:/";
    }
}
