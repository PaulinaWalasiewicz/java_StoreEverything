package com.example.storeeverything.service;

import com.example.storeeverything.Repository.CategoryRepository;
import com.example.storeeverything.Repository.NoteRepository;
import com.example.storeeverything.Repository.UserRepository;
import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoteService implements CommandLineRunner {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    public void createNotes(){
        System.out.println("Data creation started...");
        noteRepository.save(new Note(1, LocalDateTime.now(),"title1","test for note 1",categoryRepository.findCategoryByName("Home"),userRepository.findUserByUsername("User01")));
        noteRepository.save(new Note(2, LocalDateTime.now(),"title2","test for note 2",categoryRepository.findCategoryByName("School"),userRepository.findUserByUsername("User01")));
        noteRepository.save(new Note(3, LocalDateTime.now(),"title3","test for note 3",categoryRepository.findCategoryByName("Home"),userRepository.findUserByUsername("User01")));
        System.out.println("Data creation complete...");
    }

    public void showAllNotes(){
        noteRepository.findAll().forEach(note -> System.out.println(note.getTitle()));

    }
    @Override
    public void run(String... args) throws Exception {
                System.out.println("-------------CREATE GROCERY ITEMS------\n");
        //createNotes();

        System.out.println("\n------------SHOW ALL GROCERY ITEMS---\n");
        showAllNotes();
    }
}
