package com.korit.entity;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class User {
    private Integer userId;
    private String username;
    private String password;
    private Integer age;
    private LocalDateTime createDt;
}
