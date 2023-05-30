package com.example.storeeverything.Repository;

import com.example.storeeverything.data.Category;
import com.example.storeeverything.data.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface NoteRepository extends MongoRepository<Note,String> {
    @Query("{'title':?0}")
    Note findNoteByTitle(String title);

    public long count();

    List<Note> findByUserIdOrderByCreatedAtAsc(String userId);

    List<Note> findByUserIdOrderByCreatedAtDesc(String userId);
    List<Note> findByUserIdAndAndCategory_Id(String userId,String categoryId);

    Note findNoteById(String id);
    List<Note> findByUserOrderByTitleDesc(String userId);
    List<Note> findByUserOrderByTitleAsc(String userId);

    List<Note> findByUserOrderByCategoryDesc(String userId);
    List<Note> findByUserOrderByCategoryAsc(String userId);

//    List<Note> findByCreatedAtDate(LocalDateTime createdAt_date);


}
