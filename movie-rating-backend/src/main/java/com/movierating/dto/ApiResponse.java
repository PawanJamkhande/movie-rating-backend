package com.movierating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
// standard API response wrapper used by every controller
    private boolean success;

    private String message;

    private Object data;

}
