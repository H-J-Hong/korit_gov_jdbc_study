package com.korit.study2.dto;

import com.korit.study2.entity.User;
import lombok.Data;

@Data
public class SigninReqDto {
    private String username;
    private String password;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .build();


    }
}
