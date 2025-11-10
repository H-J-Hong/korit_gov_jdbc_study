package com.korit.study2.dto;

import com.korit.study2.entity.User;
import com.korit.study2.util.PasswordEncoder;
import lombok.Data;

@Data
public class SignupReqDto {
    private String username;
    private String password;
    private String passwordConfirm;
    private String email;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(PasswordEncoder.encode(password))
                .email(email)
                .build();
    }
}
