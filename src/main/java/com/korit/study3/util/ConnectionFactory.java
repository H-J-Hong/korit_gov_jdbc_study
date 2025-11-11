package com.korit.study3.util;

import com.korit.study3.config.DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBConfig.URL,DBConfig.USERNAME,DBConfig.PASSWORD);
    }
}
