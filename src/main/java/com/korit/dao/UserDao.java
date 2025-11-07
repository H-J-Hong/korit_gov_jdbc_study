package com.korit.dao;

// DAO(Database Access Object)
// db에 접근하고 데이터를 조작하는데 사용되는 객체
// 일반적으로 데이터베이스에 대한 접근을 캡슐화
//

import com.korit.entity.User;
import com.korit.util.ConnectionFactory;

import java.sql.*;

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
//    findUserByUsername()
//    getUserAllList()

}
