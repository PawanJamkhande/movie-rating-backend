package com.movierating.dto;

import lombok.Data;

@Data
public class UserResponse {
// safe user object sent back to client (no password field)
    private Long userId;
    private String fullName;
    private String email;
    private String mobile;
    private String role;

}
