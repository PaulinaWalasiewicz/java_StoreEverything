package com.example.storeeverything.controllers;
import com.example.storeeverything.Repository.CategoryRepository;
import com.example.storeeverything.Repository.NoteRepository;
import com.example.storeeverything.Repository.UserRepository;
import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/")
    public String index(Model model){
        return sortedView(model,"1","date","asc");
    }



    @GetMapping("/notes/")
    public String sortedView(Model model,@RequestParam(value = "user_id") String user_id, @RequestParam(value = "sort",defaultValue ="title") String sort, @RequestParam(value = "sortDir",defaultValue = "asc") String sortDir){

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

        return "index1";
    }

    @GetMapping("/addNote")
    public  String addNote(){
        return "addnewnote";
    }

    @PostMapping("/saveNote")
    public String savenote(@ModelAttribute Note note){

        //TODO
        if(note.getCategory() == null){
            note.setCategory(categoryRepository.findCategoryByName("Home"));
        }
        note.setCreatedAt(LocalDateTime.now());
        if(note.getUser() == null){
            note.setUser(userRepository.findUserById("1"));
        }

        noteRepository.save(note);
        return "redirect:/";
    }

    @RequestMapping("/updateNote/{id}")
    public String updateNote(@PathVariable("id") String id,Model model){
        Note returnedNote = noteRepository.findNoteById(id);
        model.addAttribute("any",returnedNote);
        return "updateNote";

    }
    @RequestMapping("/deleteNote/{id}")
    public String deleteNote(@PathVariable String id){
        noteRepository.delete(noteRepository.findNoteById(id));
        return "redirect:/";
    }
}
