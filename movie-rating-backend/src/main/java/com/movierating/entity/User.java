package com.movierating.entity;

import jakarta.persistence.*; // imports all JPA annotations like entity, table , id, generated value, column
import jakarta.validation.constraints.Email; // validation annotations like notblank, email, pattern
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*; // it generates the code automatically so we need to only wrote main thing and other things likee getter setter tostring will be written by lombok

@Entity // this tells the system that this class represents the databse table. If we would never write this then hibernate will treat this class as a normal one and completely ignore it.
@Table(name = "users") // this shows the table name is users
@Data // this handles the getter setter tostring equals and other methods 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // we used Long bcz it is the wrapper class and wrapper class can hold null values 

    @NotBlank(message = "Full Name is required")
    @Column(nullable = false)
    private String fullName;

    @Email(message = "Enter a valid email")
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Enter valid mobile number")
    @Column(nullable = false, unique = true)
    private String mobile;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    // ADMIN or USER
    @Column(nullable = false)
    private String role;

}
