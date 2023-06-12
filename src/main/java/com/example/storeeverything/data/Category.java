package com.example.storeeverything.data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document("category")
public class Category {
    @Id
    private String id;

    @NotEmpty(message = "Name must not be empty")
    @Pattern(regexp = "^[a-z]+$", message = "Name must be all lowercase letters")
    private String name;
    public Category(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
