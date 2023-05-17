package com.example.storeeverything.controllers;
import com.example.storeeverything.Repository.NoteRepository;
import com.example.storeeverything.data.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Controller
public class NoteController {
    @Autowired
    NoteRepository noteRepository;

    @GetMapping("/")
    public String index(Model model){
        return sortedView(model,1,"date","asc");
    }



    @GetMapping("/notes/")
    public String sortedView(Model model,@RequestParam(value = "user_id") Integer user_id, @RequestParam(value = "sort",defaultValue ="title") String sort, @RequestParam(value = "sortDir",defaultValue = "asc") String sortDir){

        List<Note> notes = new ArrayList();
        if(sort.equals("title")) {

            if (sortDir.equals("asc")) {
                notes = noteRepository.findByUserOrderByTitleAsc(user_id);
            }
            else {
                notes = noteRepository.findByUserOrderByTitleDesc(user_id);
            }
        } else if (sort.equals("category")) {
            if (sortDir.equals("asc")) {
                notes = noteRepository.findByUserOrderByCategoryAsc(user_id);
            }
            else {
                notes = noteRepository.findByUserOrderByCategoryDesc(user_id);
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
        System.out.println(notes);
        model.addAttribute("any",notes);
        model.addAttribute("sort",sort);
        model.addAttribute("sortDir",sortDir);
        String checkDirection = sortDir.equals("asc") ?"desc":"asc";
        model.addAttribute("checkDirection",checkDirection);

        return "index";
    }
}
