package com.example.storeeverything.data;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
@Document(collection = "notes")
public class Note {
    private @Id @Generated Integer id;

    private LocalDateTime createdAt;
    private String title;
    private String content;



    @DBRef
    private Category category;
    @DBRef
    private  User user;


}
