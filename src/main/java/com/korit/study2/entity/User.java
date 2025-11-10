package com.korit.study2.entity;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createDt;
}
