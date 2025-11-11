package com.korit.study3.dto;

import com.korit.study3.entity.Post;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
public class GetPostDto {
    private Integer postId;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createDt;

    public Post toEntity() {
        return Post.builder()
                .postId(postId)
                .title(title)
                .content(content)
                .username(username)
                .createDt(createDt)
                .build();
    }
}
