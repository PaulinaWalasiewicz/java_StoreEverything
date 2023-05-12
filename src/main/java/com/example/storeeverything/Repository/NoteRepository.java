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

    List<Note> findByUserIdOrderByCreatedAtAsc(Integer userId);

    List<Note> findByUserIdOrderByCreatedAtDesc(Integer userId);
    List<Note> findByUserIdAndAndCategory_Id(Integer userId,Integer categoryId);

    List<Note> findByUserId(Integer userId);

    List<Note> findByUserOrderByTitleDesc(Integer userId);
    List<Note> findByUserOrderByTitleAsc(Integer userId);


    List<Note> findByUserOrderByCategoryDesc(Integer userId);
    List<Note> findByUserOrderByCategoryAsc(Integer userId);

//    List<Note> findByCreatedAtDate(LocalDateTime createdAt_date);


}
