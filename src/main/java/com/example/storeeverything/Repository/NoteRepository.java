package com.example.storeeverything.Repository;

import com.example.storeeverything.data.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface NoteRepository extends MongoRepository<Note,String> {
    @Query("{'title':?0}")
    Note findNoteByTitle(String title);

    public long count();

}
