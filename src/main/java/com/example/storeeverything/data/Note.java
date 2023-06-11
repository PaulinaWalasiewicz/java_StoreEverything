package com.example.storeeverything.data;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@Setter
@Document(collection = "notes")
public class Note {
    @Id
    private String id;

    private LocalDateTime createdAt;
    private String title;
    private String content;



    @DBRef
    private Category category;
    @DBRef
    private  User user;


}
