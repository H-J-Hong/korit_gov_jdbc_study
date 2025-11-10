package com.korit.study1;

import com.korit.study1.dao.UserDao;
import com.korit.study1.entity.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = UserDao.getInstance();
//        User user = User.builder()
//                .username("hjhong2")
//                .password("asdf1234")
//                .age(45)
//                .build();
//
//        int count = userDao.addUser(user);
//        System.out.println("추가된 행 갯수 : " + count);
//        System.out.println("추가된 유저 정보 : " + user);

//        findUserByUsername(username)
        User foundUser = userDao.findUserByUsername("hjhong");
        System.out.println("foundUser =" + foundUser);
//        getUserAllList()
        List<User> allUser = userDao.getUserAllList();
        allUser.forEach(System.out::println);
//        findUserByKeyword(keyword)
        List<User> foundUsers = userDao.findUserByKeyword("hj");
        foundUsers.forEach(System.out::println);
//        LocalDateTime
    }
}