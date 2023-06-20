package com.example.storeeverything.controllers;
import com.example.storeeverything.Repository.CategoryRepository;
import com.example.storeeverything.Repository.NoteRepository;
import com.example.storeeverything.Repository.UserRepository;
import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.Note;
import com.example.storeeverything.data.SortSettings;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    public String index(Model model, HttpSession session){

        String currentUserID= GetUserID(model);

        SortSettings sortSettings = (SortSettings) session.getAttribute("sortSettings");
        if(sortSettings==null){
            sortSettings = new SortSettings();
            sortSettings.setSortField("date");
            sortSettings.setSortDirection("asc");
            session.setAttribute("sortSettings", sortSettings);
        }

        model.addAttribute("sortSettings", sortSettings);


        String sortDir= (String) sortSettings.getSortDirection();
        String sortType= (String) sortSettings.getSortField();


        return sortedView(model,session,currentUserID,sortType,sortDir);
    }



    @GetMapping("/notes/")
    public String sortedView(Model model, HttpSession session, @RequestParam(value = "user_id") String user_id, @RequestParam(value = "sort",defaultValue ="title") String sort, @RequestParam(value = "sortDir",defaultValue = "asc") String sortDir){




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
            //ALPHABETICALLY

            if (sortDir.equals("asc")) {
                notes = noteRepository.findByUserOrderByCategoryAsc(user_id);
            }
            else {
                notes = noteRepository.findByUserOrderByCategoryDesc(user_id);
            }


            //CODE FOR SORTING NOTES ACCORDING TO CATEGORY POPULARITY !!!


//            List<Note> notes_temp = noteRepository.findByUserOrderByCategoryAsc(user_id);
//            Map<String, Integer> categoryCounts = new LinkedHashMap<>();
//
////            count how many times categories are used
//            for (Note n :notes_temp) {
//                categoryCounts.put(String.valueOf(n.getCategory().getName()),categoryCounts.getOrDefault(String.valueOf(n.getCategory().getName()),0)+1);
//            }
//
////            sort categories ascending or descending
//            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(categoryCounts.entrySet());
//            if (sortDir.equals("asc")) {
//                Collections.sort(sortedEntries, Comparator.comparing(Map.Entry::getValue));
//
//            }
//            else {
//                Collections.sort(sortedEntries, Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()));
//
//            }
////            get sorted notes based on quantity of categories
//            for (Map.Entry<String, Integer> entry : sortedEntries) {
//                String category = entry.getKey();
//                notes.addAll(notes_temp.stream().filter(n-> n.getCategory().getName().equals(category))
//                        .collect(Collectors.toList()));
//
//            }

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

        SortSettings sortSettings = new SortSettings();
        sortSettings.setSortField(sort);
        sortSettings.setSortDirection(sortDir);
        session.setAttribute("sortSettings", sortSettings);

        // Add the sortSettings object to the model to use it in your view
        model.addAttribute("sortSettings", sortSettings);

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
