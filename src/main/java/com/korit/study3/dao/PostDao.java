package com.korit.study3.dao;

import com.korit.study2.util.ConnectionFactory;
import com.korit.study3.dto.AddPostDto;
import com.korit.study3.dto.GetPostDto;
import com.korit.study3.entity.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostDao {
    private static PostDao instance;
    private PostDao() {}

    public static PostDao getInstance() {
        if (instance == null) instance = new PostDao();
        return instance;
    }

    public int addPost(Post post) {
        String sql = "insert into post_tb value (0, ?, ?, ?, now());";
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                ) {
            ps.setString(1,post.getTitle());
            ps.setString(2,post.getContent());
            ps.setString(3,post.getUsername());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Post> getAllPosts() {
        String sql = "select * from post_tb order by create_dt desc";
        List<Post> postList = new ArrayList<>();

        try(
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ) {
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) postList.add(toPost(rs));
            }
            return postList.isEmpty() ? null : postList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Post> findPostsByUsername (String username) {
        String sql = "select * from post_tb where username = ? order by create_dt desc";
        List<Post> postList = new ArrayList<>();
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
                ) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                postList.add(toPost(rs));
            }
            return postList.isEmpty() ? null : postList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Optional<Post> findPostById (int id) {
        String sql = "select * from post_tb where id = ?";
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(toPost(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.empty();
    }

    public List<Post> findPostsByKeyword (String keyword) {
        String sql = "select * from post_tb where content like ? or title like ? order by create_dt desc";
        List<Post> postList = new ArrayList<>();
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                postList.add(toPost(rs));
            }
            return postList.isEmpty() ? null : postList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Optional<Post> findPostByTitle (String title) {
        String sql = "select * from post_tb where title = ?";
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    return Optional.of(toPost(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.empty();
    }

    public Post toPost(ResultSet rs) throws SQLException {
        return Post.builder()
                .postId(rs.getInt("id"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .username(rs.getString("username"))
                .createDt(rs.getTimestamp("create_dt").toLocalDateTime())
                .build();
    }

//    public GetPostDto toGetPostDto(ResultSet rs) throws SQLException {
//        return GetPostDto.builder()
//                .postId(rs.getInt("id"))
//                .title(rs.getString("title"))
//                .content(rs.getString("content"))
//                .username(rs.getString("username"))
//                .createDt(rs.getTimestamp("create_dt").toLocalDateTime())
//                .build();
//    }
}
