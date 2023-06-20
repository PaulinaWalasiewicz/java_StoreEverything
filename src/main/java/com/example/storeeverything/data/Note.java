package com.example.storeeverything.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
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

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;

    @NonNull
    @Size(min=3,max=20,message = "Title must be between 3 and 20 characters")
    private String title;

    @NotNull
    @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
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


    @Null(message = "Link must be empty or a valid URL")
    @Pattern(regexp = "(http|https)://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}(/\\S*)?", message = "Invalid link format")
    private String link;
}
