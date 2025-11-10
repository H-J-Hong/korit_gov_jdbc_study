package com.korit.study2.dao;

import com.korit.study2.dto.SignupReqDto;
import com.korit.study2.util.ConnectionFactory;
import com.korit.study2.dto.GetUserListRespDto;
import com.korit.study2.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {
    private static UserDao instance;

    private UserDao() {}

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }
//    username 조회
    public Optional<User> findUserByUsername(String username) {
        String sql = "select * from user2_tb where username = ?";

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(toUser(rs));
                }
            }
            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


//    email 조회

    public Optional<User> findUserByEmail(String email) {
        String sql = "select * from user2_tb where email = ?";

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(toUser(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return Optional.empty();
    }
//    user 추가
    public int addUser(User user) {
        String sql = "insert into user2_tb(user_id,username,password,email,create_dt) value (0, ?, ?, ?, now())";
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                ){
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());

            int updateInt = ps.executeUpdate();

//            try (ResultSet keys = ps.getGeneratedKeys()){
//                if (keys.next()) {
//                    int id = keys.getInt(1);
//                    user.setUserId(id);
//                }
//            }
            return updateInt;
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }

    }
//    user 전체 조회
    public List<GetUserListRespDto> getAllUsers() {
        String sql = "select user_id,username,email,create_dt from user2_tb";
        List<GetUserListRespDto> getUserListRespDtos = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                getUserListRespDtos.add(toGetUserListRespDto(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return getUserListRespDtos.isEmpty() ? null : getUserListRespDtos;
    }

//    user username으로 조회

    public List<GetUserListRespDto> findUsersByKeyword(String username) {
        String sql = "select * from user2_tb where username like ?";
        List<GetUserListRespDto> getUserListRespDtos = new ArrayList<>();
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
                ) {
            ps.setString(1, "%" + username + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                getUserListRespDtos.add(toGetUserListRespDto(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return getUserListRespDtos.isEmpty() ? null : getUserListRespDtos ;
    }


    public User toUser(ResultSet rs) throws SQLException {
        return User.builder()
                .userId(rs.getInt("user_id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .email(rs.getString("email"))
                .createDt(rs.getTimestamp("create_dt").toLocalDateTime())
                .build();
    }

    public GetUserListRespDto toGetUserListRespDto(ResultSet rs) throws SQLException {
//        GetUserListRespDto getUserListRespDto = new GetUserListRespDto();
//        getUserListRespDto.setUserId(rs.getInt("user_id"));
//        getUserListRespDto.setUsername(rs.getString("username"));
//        getUserListRespDto.setEmail(rs.getString("email"));
//        getUserListRespDto.setCreateDt(rs.getTimestamp("create_dt").toLocalDateTime());
        return GetUserListRespDto.builder()
                .userId(rs.getInt("user_id"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .createDt(rs.getTimestamp("create_dt").toLocalDateTime())
                .build();

    }


}
