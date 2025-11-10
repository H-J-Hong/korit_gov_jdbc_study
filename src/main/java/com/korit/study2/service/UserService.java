package com.korit.study2.service;

import com.korit.study2.dao.UserDao;
import com.korit.study2.dto.GetUserListRespDto;
import com.korit.study2.dto.SigninReqDto;
import com.korit.study2.dto.SignupReqDto;
import com.korit.study2.entity.User;
import com.korit.study2.util.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public class UserService {
    private static UserService instance;
    private UserDao userDao;

    private UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public static UserService getInstance() {
        if(instance == null) {
            instance = new UserService(UserDao.getInstance());
        }
        return instance;
    }
//    1. 중복 확인 메소드
//    1-1. username 중복 확인
//    1-2. email 중복 확인

    public boolean isValidUsername(String username) {
        Optional<User> foundUser = userDao.findUserByUsername(username);
        return foundUser.isEmpty();
    }

    public boolean isValidEmail(String email) {
        Optional<User> foundUser = userDao.findUserByEmail(email);
        return foundUser.isEmpty();
    }

    public Optional<User> login(SigninReqDto signinReqDto) {
        if (!isValidUsername(signinReqDto.getUsername()) && passwordCheck(signinReqDto)) {
            return Optional.of(signinReqDto.toEntity());
        }
        else {
            System.out.println("유효하지 않은 입력정보입니다.");
            return Optional.empty();
        }

    }

    public boolean passwordCheck(SigninReqDto signinReqDto) {
        return PasswordEncoder.match(
                signinReqDto.getPassword(),
                userDao.findUserByUsername(signinReqDto.getUsername()).get().getPassword()
        );
    }

//    2. 회원 가입 메소드
//    3. 회원 전체 조회 메소드
//    4. 회원 키워드 검색 메소드

    public int signup(SignupReqDto signupReqDto) {
        return userDao.addUser(signupReqDto.toEntity());
    }

    public void showAllUsers() {
        userDao.getAllUsers().forEach(System.out::println);
    }

    public void showUserByKeyword(String keyword) {
        userDao.findUsersByKeyword(keyword).forEach(System.out::println);
    }




}
