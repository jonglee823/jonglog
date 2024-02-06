# JONGLOG

### Summary
JPA 실습을 위한 포트폴리오 repo입니다.

## 프로젝트 전체 구조

## 기술스택
>Java, Spring Boot, JPA, QueryDSL , Lombok, Gradle, Junit5

## 프로젝트 주요 관심사
- JPA를 이용한 CRUD 구현
- Junit5를 이용한 테스트 코드 구현

## 화면 설계 및 진행상황
| Feature | Request | 주소                     | 설명                  | 체크    | 비고       
|---------|---------|------------------------|---------------------|-------|----------|
| 메인화면    | GET     | /        | 메인화면                | ☑️    |          |
| 상세 화면   | GET     | /read/:postId        | postId 특정 게시글 1개 조회 | ☑️    |          |
| 등록 화면   | GET     | /write                 | 게시글 등록 화면 호출        | ☑️    |          |
| 수정 화면   | GET     | /edit/:postId        | 게시글 수정 화면 호출        | ☑️ |          |


### 게시글 CRUD API
| Feature   | Request | 주소                     | 설명                  | 체크    | 비고       
|-----------|---------|------------------------|---------------------|-------|----------|
| 게시글 1개 조회 | GET     | /posts/{postId}        | postId 특정 게시글 1개 조회 | ☑️    |          |
| 게시글 전체 조회 | GET     | /posts                 | 게시글 등록              | ☑️    |          |
| 게시글 수정    | PATCH   | /posts/{postId}        | 게시글 수정              | ☑️ |          |
| 게시글 삭제    | POST    | /posts/delete/{postId} | 게시글 삭제              | ☑️    | Soft Delete |

### TODO LIST
- 검색기능 + 페이지네이션
- 카테고리 출력
- 답글 조회 & 등록 & 수정 & 삭제
- 답글 비밀번호 암호화
- longin check
- 임시 저장


## 프로젝트 구조
- 추후 추가 예정

## ERD
- 추후 추가 예정
- https://www.erdcloud.com/d/9BcGko86PbWsrpTYo
