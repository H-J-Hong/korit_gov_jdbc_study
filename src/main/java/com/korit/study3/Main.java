package com.korit.study3;

/*
* 게시물
* id, title, content, username, create_dt
* 제목, 내용, 닉네임, 생성일, 필수값
* 제목 중복 불가
*
* 추가
* 제목 내용 닉네임
* 제목은 중복체크
*
* username으로 게시물 검색
* 키워드로 게시물 검색 (제목 "또는" 내용")
* id로 단건 검색
*
* 조회시 가장 최신 게시물 부터 정렬
*
* */

import com.korit.study3.dao.PostDao;
import com.korit.study3.dto.AddPostDto;
import com.korit.study3.dto.GetPostDto;
import com.korit.study3.service.PostService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        PostDao postDao = PostDao.getInstance();
        PostService postService = PostService.getInstance();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("[[[[[ 게시물 관리 프로그램 ]]]]]");
            System.out.println("1. 게시물 등록");
            System.out.println("2. 게시물 조회");
            System.out.println("q. 프로그램 종료");
            System.out.print(">>>>> ");
            String selectMenu = sc.nextLine();

            if ("q".equalsIgnoreCase(selectMenu)) {
                System.out.print("프로그램을 종료합니다. ");
                for (int i = 0 ; i < 5 ; i++) {
                    Thread.sleep(150);
                    System.out.print("*");
                }
                System.out.println();
                break;
            } else if ("1".equals(selectMenu)) {
                AddPostDto addPostDto = new AddPostDto();
                while (true) {
                    System.out.print("title >>>>> ");
                    addPostDto.setTitle(sc.nextLine());
                    if (postService.isValidPosting(addPostDto.getTitle())) break;
                    else System.out.println("동일한 title이 이미 존재 합니다.");
                }
                System.out.print("content >>>>> ");
                addPostDto.setContent(sc.nextLine());
                System.out.print("username >>>>> ");
                addPostDto.setUsername(sc.nextLine());
                int posting = postService.posting(addPostDto);
                if (posting == 0) {
                    System.out.println("게시물 등록에 실패하였습니다.");
                } else {
                    System.out.println("게시물 등록에 성공하였습니다.");
                    postService.showAllContents();
                }
            } else if ("2".equals(selectMenu)) {
                while (true) {
                    System.out.println();
                    System.out.println("검색할 방법을 선택하세요");
                    System.out.println("1. id로 찾기");
                    System.out.println("2. 제목 또는 내용을 키워드로 찾기");
                    System.out.println("3. username으로 찾기");
                    System.out.println("a. 전체 검색");
                    System.out.println("q. 빠져나가기");
                    System.out.print(">>>>> ");
                    String selectSearchMethod = sc.nextLine();
                    if ("q".equalsIgnoreCase(selectSearchMethod)) {
                        System.out.println("이전메뉴로 돌아갑니다.");
                        break;
                    }
                    else if ("1".equals(selectSearchMethod)) {
                        System.out.print("ID를 입력하세요 >>>>> ");
                        try {
                            int id = sc.nextInt();
                            sc.nextLine();
                            postService.showContentsById(id);
                        } catch (Exception e) {
                            System.out.println("잘못 입력 하셨습니다.");
                            sc.nextLine();
                        }

                    } else if ("2".equals(selectSearchMethod)) {
                        System.out.print("keyword를 입력하세요 >>>>> ");
                        try {
                            String keyword = sc.nextLine();
                            postService.showContentsByKeyword(keyword);
                        } catch (Exception e) {
                            System.out.println("잘못 입력 하셨습니다.");
                        }

                    } else if ("3".equals(selectSearchMethod)) {
                        System.out.print("username을 입력하세요 >>>>> ");
                        try {
                            String username = sc.nextLine();
                            postService.showContentsByUsername(username);
                        } catch (Exception e) {
                            System.out.println("잘못 입력 하셨습니다.");
                        }

                    } else if ("a".equalsIgnoreCase(selectSearchMethod)) {
                        try {
                            postService.showAllContents();
                        } catch (Exception e) {
                            System.out.println("잘못 입력 하셨습니다.");
                        }

                    }
                }
            }
        }
        System.out.println("프로그램이 종료되었습니다.");



    }
}
