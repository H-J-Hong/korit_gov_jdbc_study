package com.korit.study1.dao;

// DAO(Database Access Object)
// db에 접근하고 데이터를 조작하는데 사용되는 객체
// 일반적으로 데이터베이스에 대한 접근을 캡슐화
//

import com.korit.study1.entity.User;
import com.korit.study1.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public static UserDao instance;
    private UserDao() {}

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }
//    addUser(User user)
    public int addUser(User user) {
        String sql = "insert into user_tb(user_id, username, password, age, create_dt) values (0, ?, ?, ?, now())";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            Statement.RETURN_GENERATED_KEYS
//            DB가 생성한 자동 증분된 키를 돌려받겠다 라는 옵션
//            단 실제로 키가 생성되려면 insert시 db에서 autoIncrement가 적용되도록 설정
//            PreparedStatement
//            SQL문에 있는?(placeholder) 자리에 자바 값을 타입별로 안전하게 채운다.
//            이 방식은 SQL 인젝션을 방지하고 DB가 파라미터 타입을 안전하게 처리할 수 있게 돕는다.
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setInt(3,user.getAge());

            int updateInt = ps.executeUpdate();         // 실제 sql 변수에 입력된 쿼리문이 실행되도록 하는 구문 - 변경된 행의 수량을 반환

            try (ResultSet keys = ps.getGeneratedKeys()) {
                System.out.println(keys.toString());
                if(keys.next()) {
                    int id = keys.getInt(1);
                    user.setUserId(id);
                }
            }
            return updateInt;
        } catch (SQLException e) {
            e.printStackTrace();                        // 보안에 취약함.
            return 0;
        }
    }

    public User findUserByUsername(String username) {
        String sql = "select user_id, username, password, age, create_dt from user_tb where username = ?";
        try (
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)            // 조회만 하므로 리턴 받을 키는 필요 없음.
        ) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {                    // executeQuery() : 조회 등 테이블 변환 없는 작업 할때 사용하는 메소드
                return rs.next() ? toUser(rs) : null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
//    getUserAllList()

    public List<User> getUserAllList() {
        String sql = "select user_id, username, password, age, create_dt from user_tb";
        List<User> users = new ArrayList<>();
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                users.add(toUser(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return users.isEmpty() ? null : users;
    }

    public List<User> findUserByKeyword(String keyword) {
        String sql = "select user_id, username, password, age, create_dt from user_tb where username like ?";
        List<User> users = new ArrayList<>();

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);

        ) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println(rs.next());
                while(rs.next()) {
                    users.add(toUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return users.isEmpty() ? null : users;
    }


    private User toUser(ResultSet rs) throws SQLException {
        return User.builder()
                .userId(rs.getInt("user_id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .age(rs.getInt("age"))
                .createDt(rs.getTimestamp("create_dt").toLocalDateTime())
                .build();
    }

}
