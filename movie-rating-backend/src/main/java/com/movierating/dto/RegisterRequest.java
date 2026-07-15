package com.movierating.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Enter valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "^[6-9]\\d{9}$",
            message = "Enter valid mobile number")
    private String mobile;

    @Size(min = 6, max = 15,
            message = "Password must be between 6 and 15 characters")
    private String password;

    @NotBlank(message = "Role is required")
    private String role;
}
