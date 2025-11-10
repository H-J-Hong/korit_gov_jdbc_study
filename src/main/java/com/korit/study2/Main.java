package com.korit.study2;

import com.korit.study2.dao.UserDao;
import com.korit.study2.dto.SigninReqDto;
import com.korit.study2.dto.SignupReqDto;
import com.korit.study2.entity.User;
import com.korit.study2.service.UserService;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserService userService = UserService.getInstance();
        UserDao userDao = UserDao.getInstance();
        Optional<User> principal = null;

        while (true) {
            System.out.println("[[[ 회원 관리 ]]]");
            System.out.println("1. 회원 가입");
            System.out.println("2. 로그인");
            System.out.println("3. 전체 회원 조회");
            System.out.println("4. 회원 검색");
            System.out.println("q. 프로그램 종료");
            System.out.print(">>>>> ");
            String selectMenu = sc.nextLine();

            if("q".equalsIgnoreCase(selectMenu)) {
                System.out.println("프로그램 종료");
                break;
            } else if ("1".equals(selectMenu)) {
                System.out.println("[ 회원 가입 ]");
                //                todo : 회원 가입 메소드 호출
                SignupReqDto signupReqDto = new SignupReqDto();
                while (true) {
                    System.out.print("username >>>>> ");
                    signupReqDto.setUsername(sc.nextLine());
                    if (userService.isValidUsername(signupReqDto.getUsername())) break;
                    else System.out.println("username이 유효하지 않습니다.");
                }
                while (true) {
                    System.out.print("password >>>>> ");
                    signupReqDto.setPassword(sc.nextLine());
                    System.out.print("password confirmation >>>>> ");
                    signupReqDto.setPasswordConfirm(sc.nextLine());

                    if(signupReqDto.getPassword().equals(signupReqDto.getPasswordConfirm())) break;
                    else System.out.println("password가 유효하지 않습니다.");
                }
                while (true) {
                    System.out.print("email >>>>> ");
                    signupReqDto.setEmail(sc.nextLine());
                    if (userService.isValidEmail(signupReqDto.getEmail())) break;
                    else System.out.println("email이 유효하지 않습니다.");
                }
                if (userService.signup(signupReqDto) == 0) {
                    System.out.println("회원 가입에 실패하였습니다.");
                } else System.out.println("회원 가입이 성공적으로 이루어졌습니다.");
            } else if ("2".equals(selectMenu)) {
                System.out.println("[ 로그인 ]");
//                todo : 로그인 메소드 호출
                SigninReqDto signinReqDto = new SigninReqDto();
                while (true) {
                    System.out.print("username >>>>> ");
                    signinReqDto.setUsername(sc.nextLine());
                    System.out.print("password >>>>> ");
                    signinReqDto.setPassword(sc.nextLine());
                    if (userService.login(signinReqDto).isPresent()) {
                        principal = Optional.of(userService.login(signinReqDto).get());
                        break;
                    }
                    else System.out.println("유효하지 않은 입력정보입니다.");
                }

                try {
                    if (principal.isPresent()) {
                        System.out.println("로그인에 성공하였습니다.");
                        System.out.println(principal);
                    }
                } catch (Exception e) {
                    System.out.println("로그인에 실패하였습니다.");
                }
            } else if ("3".equals(selectMenu)) {
                System.out.println("[ 전체 회원 조회 ]");
                try {
                    if (principal.isPresent()) userService.showAllUsers();
                } catch(Exception e) {
                    System.out.println("로그인 되지 않았습니다.");
                }
//                todo : 전체 회원 조회 메소드 호출
            } else if ("4".equals(selectMenu)) {
                System.out.println("[ 회원 검색 ]");
                System.out.print("keyword >>>>> ");
                String keyword = sc.nextLine();
                try {
                    if (principal.isPresent()) userService.showUserByKeyword(keyword);
                } catch(Exception e) {
                    System.out.println("로그인 되지 않았습니다.");
                }

//                todo : 회원 검색 메소드 호출
            }
        }
        System.out.println("프로그램 종료 완료!");
    }
}
