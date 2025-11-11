package com.korit.study3.service;

import com.korit.study3.dao.PostDao;
import com.korit.study3.dto.AddPostDto;
import com.korit.study3.entity.Post;

import java.util.List;
import java.util.Optional;

public class PostService {
    private static PostService instance;
    private PostDao postDao;
    private PostService(PostDao postDao) {
        this.postDao = postDao;
    };

    public static PostService getInstance() {
        if (instance == null) instance = new PostService(PostDao.getInstance());
        return instance;
    }

    public int posting(AddPostDto addPostDto) {
        if (isValidPosting(addPostDto.getTitle())) return postDao.addPost(addPostDto.toEntity());
        else System.out.println("다시 확인해주세요.");
        return 0;
    }

    public void showAllContents() {
        postDao.getAllPosts().forEach(System.out::println);
    }

    public void showContentsById(int id) {
        Optional<Post> post = postDao.findPostById(id);
        if (post.isPresent()) System.out.println(post.get());
        else System.out.println("검색에 실패하였습니다.");
    }

    public void showContentsByKeyword(String keyword) {
        List<Post> posts = postDao.findPostsByKeyword(keyword);
        if (posts != null) posts.forEach(System.out::println);
        else System.out.println("검색에 실패하였습니다.");
    }

    public void showContentsByUsername(String username) {
        List<Post> posts = postDao.findPostsByUsername(username);
        if (posts != null) posts.forEach(System.out::println);
        else System.out.println("검색에 실패하였습니다.");
    }

    public boolean isValidPosting(String title) {
        return postDao.findPostByTitle(title).isEmpty();
    }

}
