package com.example.storeeverything.Repository;

import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note,String> {
    @Query("{'title':?0}")
    Note findNoteByTitle(String title);

    public long count();

    List<Note> findByUserIdOrderByCreatedAtAsc(Integer userId);

    List<Note> findByUserIdOrderByCreatedAtDesc(Integer userId);
    List<Note> findByUserIdAndAndCategory_Id(Integer userId,Integer categoryId);

    List<Note> findByUserId(Integer userId);
}
