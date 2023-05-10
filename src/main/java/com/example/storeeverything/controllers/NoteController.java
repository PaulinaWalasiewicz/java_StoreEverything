package com.example.storeeverything.controllers;

import com.example.storeeverything.Repository.NoteRepository;
import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    @Autowired
    NoteRepository noteRepository;


    // Endpoint to retrieve notes for a user, sorted by category quantity in descending order
    @GetMapping("/user/{userId}/category/{categoryId}")
    public List<Note> findByUserIdAndAndCategory_Id(@PathVariable Integer userId,@PathVariable Integer categoryId) {
        List<Note> notes = noteRepository.findByUserIdAndAndCategory_Id(userId,categoryId);
        return notes;
    }

//    // Endpoint to retrieve all notes for a user, sorted by category usage in ascending order
//    // Endpoint to retrieve notes for a user, sorted by category quantity in descending order
//    @GetMapping("/user/{userId}/category/asc")
//    public List<Note> getNotesByCategoryAsc(@PathVariable Integer userId) {
//        List<Note> notes = noteRepository.findByUserId(userId);
//
//        List<Note> notesFiltred = new ArrayList<>();
//        List<Integer> count = new ArrayList<>();
//        for (Note note :notes ) {
//            if (note.getCategory().equals("home")){
//                System.out.println("home");
//            }
//        }
//
//        return notes;
//    }

    // Endpoint to retrieve all notes for a user, sorted by createdAt in descending order
    @GetMapping("/user/{userId}/date/desc")
    public List<Note> getNotesByDateDescending(@PathVariable Integer userId) {
        List<Note> notes = noteRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return notes;
    }

    // Endpoint to retrieve all notes for a user, sorted by createdAt in ascending order
    @GetMapping("/user/{userId}/date/asc")
    public List<Note> getNotesByDateAscending(@PathVariable Integer userId) {
        List<Note> notes = noteRepository.findByUserIdOrderByCreatedAtAsc(userId);
        return notes;
    }
}
