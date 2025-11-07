package com.korit;

import com.korit.dao.UserDao;
import com.korit.entity.User;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = UserDao.getInstance();
        User user = User.builder()
                .username("hjhong2")
                .password("asdf1234")
                .age(45)
                .build();

        int count = userDao.addUser(user);
        System.out.println("추가된 행 갯수 : " + count);
        System.out.println("추가된 유저 정보 : " + user);
//        LocalDateTime
    }
}