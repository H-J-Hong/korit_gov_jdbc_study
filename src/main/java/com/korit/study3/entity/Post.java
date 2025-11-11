package com.korit.study3.entity;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Post {
    private Integer postId;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createDt;
}
