package com.korit.study3.dto;

import com.korit.study3.entity.Post;
import lombok.*;

@Data
public class AddPostDto {
    private String title;
    private String content;
    private String username;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .username(username)
                .build();
    }
}
