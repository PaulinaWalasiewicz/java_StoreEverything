package com.example.storeeverything.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@Setter
@Document(collection = "notes")
public class Note {
    @Id
    private String id;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;

    private String title;
    private String content;
    @DBRef
    private Category category;
    @DBRef
    private  User user;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private String link;
}
