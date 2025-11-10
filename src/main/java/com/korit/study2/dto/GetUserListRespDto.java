package com.korit.study2.dto;

import com.korit.study2.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetUserListRespDto {
    private Integer userId;
    private String username;
    private String email;
    private LocalDateTime createDt;

//    public User toEntity() {
//        return User.builder()
//                .userId(userId)
//                .username(username)
//                .email(email)
//                .createDt(createDt)
//                .build();
//    }
}
