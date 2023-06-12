package com.example.storeeverything.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "notes")
public class Note {
    @Id
    private String id;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;

    @NonNull
    @Size(min=3,max=15,message = "Title must be between 3 and 15 characters")
    private String title;

    @NotNull
    @Size(min=1,max = 100,message = "Desctiption must be between 1 and 100 characters")
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
