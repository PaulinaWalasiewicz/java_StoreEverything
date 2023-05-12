package com.example.storeeverything.controllers;

import com.example.storeeverything.Repository.NoteRepository;
import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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


    // Endpoint to retrieve all notes for a user, sorted by title descending
    @GetMapping("/user/{userId}/title/desc")
    public List<Note> getNotesByTitleDesc(@PathVariable Integer userId) {
        List<Note> notes = noteRepository.findByUserOrderByTitleDesc(userId);
        return notes;
    }

    // Endpoint to retrieve all notes for a user, sorted by title ascending
    @GetMapping("/user/{userId}/title/asc")
    public List<Note> getNotesByTitleAsc(@PathVariable Integer userId) {
        List<Note> notes = noteRepository.findByUserOrderByTitleAsc(userId);
        return notes;
    }

    // Endpoint to retrieve all notes for a user, sorted by category alphabetically descending
    @GetMapping("/user/{userId}/category/desc")
    public List<Note> getNotesByCategoryDesc(@PathVariable Integer userId) {
        List<Note> notes = noteRepository.findByUserOrderByCategoryDesc(userId);
        return notes;
    }

    // Endpoint to retrieve all notes for a user, sorted by category alphabetically ascending
    @GetMapping("/user/{userId}/category/asc")
    public List<Note> getNotesByCategoryAsc(@PathVariable Integer userId) {
        List<Note> notes = noteRepository.findByUserOrderByCategoryAsc(userId);
        return notes;
    }


//    // Endpoint to retrieve all notes from all users, filtered by current date
//    @GetMapping("/fromToday")
//    public List<Note> getNotesFromToday() {
//        LocalDate date = LocalDate.now();
//        LocalDateTime dt = LocalDateTime.now();
//        System.out.println(date);
//        List<Note> notes = noteRepository.findByCreatedAt_Date(dt);
//        System.out.println(notes);
//        return notes;
//    }

//    @GetMapping("/mostPopularCategory")
//    public  List<Note> getMPC(){
//
////        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
////
////        GroupOperation groupOperation = Aggregation.group("category.name").count().as("count");
////        Aggregation aggregation = Aggregation.newAggregation(groupOperation);
////
////        AggregationResults<Document> result = mongoTemplate.aggregate(aggregation, "notes", Document.class);
////        List<Document> categoryCounts = result.getMappedResults();
//    }
}
