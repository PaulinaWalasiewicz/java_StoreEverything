package com.example.storeeverything.data;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotEmpty(message = "First name cannot be empty")
    @Size(min = 3, max = 20, message = "First name must be between 3 and 20 characters")
    @Pattern(regexp = "[A-Za-z]+", message = "First name must contain only letters")
    @NotBlank(message = "First name cannot be blank")
    @Capitalized(message = "First name must start with an uppercase letter")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    @Pattern(regexp = "[A-Za-z]+", message = "Last name must contain only letters")
    @NotBlank(message = "Last name cannot be blank")
    @Capitalized(message = "Last name must start with an uppercase letter")
    private String lastName;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;

    private String matchingPassword;

    private  String username;
    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;



}
