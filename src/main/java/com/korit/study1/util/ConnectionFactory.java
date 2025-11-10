package com.korit.study1.util;

/*
* 정적 초기화 블록
* - 클래스가 처음으로 로딩이 될때 단 한번만 실행 된다.
* - 요즘은 mysql connector/j를 쓰면 대부분 자동 등록이 된다.
* */

import com.korit.study1.config.DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    * DriverManager.getConnection()은 매번 새로운 연결을 만든다.
    * Connection 은 값이 아니라 DB와의 세션/자원이므로 반드시 사용이 끝나면 close() 호출
    * 멤버 변수로 등록해놓고 오래 들고 있지 않아야 한다.
    *
    * SQLException - 연결 실패(잘못된 URL / 계정, DB 다운, 방화벽 등)시 발생 되는 예외
    * */

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBConfig.URL, DBConfig.USERNAME, DBConfig.PASSWORD);
    }
}
