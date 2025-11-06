# 일정 관리 애플리케이션 (Schedule Management API)
Spring Boot 기반의 RESTful API 일정 관리 시스템입니다. 일정 CRUD 기능과 댓글 기능을 제공하며, 비밀번호 기반 인증을 통해 일정을 안전하게 관리할 수 있습니다.
## 🎯 주요 기능
### 일정 관리
- 일정 생성, 조회, 수정, 삭제 (CRUD)
- 작성자명으로 일정 검색
- 수정일 기준 내림차순 정렬
- 비밀번호 기반 수정/삭제 권한 관리
### 댓글 기능
- 일정에 대한 댓글 작성 및 관리
- 일정 조회 시 댓글 목록 포함
### 데이터 검증
- 입력값 유효성 검증 (Validation)
- 전역 예외 처리 (GlobalExceptionHandler)
- 적절한 HTTP 상태 코드 반환
## 🛠 기술 스택
- Framework: Spring Boot 3.5.7
- Language: Java 17
- Database: MySQL 8.0
- ORM: Spring Data JPA (Hibernate)
- Build Tool: Gradle
- Validation: Spring Validation
- Lombok: 코드 간소화
## 📡 API 명세
### 일정 API
| 기능                 | Method | URL                     | Request Body          | Response       |
|----------------------|--------|-------------------------|-----------------------|----------------|
| 일정 생성            | POST   | /schedules              | CreateScheduleRequest | 201 Created    |
| 일정 전체 조회       | GET    | /schedules              | -                     | 200 OK         |
| 일정 검색 (작성자명) | GET    | /schedules?name={name}  | -                     | 200 OK         |
| 일정 단건 조회       | GET    | /schedules/{scheduleId} | -                     | 200 OK         |
| 일정 수정            | PATCH  | /schedules/{scheduleId} | UpdateScheduleRequest | 200 OK         |
| 일정 삭제            | DELETE | /schedules/{scheduleId} | DeleteScheduleRequest | 204 No Content |
### Request/Response 예시
#### 일정 생성 (POST /schedules)
```
// Request
{
    "title":"놀이동산 가는날",
    "content":"누나랑 엄마랑 같이 노는 날",
    "name":"동이",
    "password":"asd1243"
}

// Response
{
    "title": "놀이동산 가는날",
    "name": "동이",
    "password": "asd1243",
    "created_date": "2025-11-06T12:00:35.540722",
    "id": 1,
    "contents": "누나랑 엄마랑 같이 노는 날"
}
```
#### 댓글 생성 (GET /schedules/{scheduleId}/comments)
```
// Request
{
    "content":"정말 기대되는 일정이야 동이야",
    "commentAuthor" : "누나",
    "password":"asd1243"
}
// Response
{
    "commentId": 2,
    "content": "정말 기대되는 일정이야 동이야",
    "commentAuthor": "누나",
    "created_date": "2025-11-06T12:05:08.151606"
}
```
<img width="542" height="195" alt="image" src="https://github.com/user-attachments/assets/ac181d3e-47ec-48f9-8bfa-fd9446f2798f" />

일정당 댓글은 10개 이하로 11개부터 댓글 작성시 전역예외처리 메세지 출력
### 일정 단건 조회 (GET /schedules/{scheduleId})
```
// Response
{
    "scheduleId": 1,
    "title": "누나랑 엄마랑 같이 노는 날",
    "content": "동이",
    "name": "놀이동산 가는날",
    "createdDate": "2025-11-06T12:00:35.540722",
    "updatedDate": "2025-11-06T12:00:35.540722",
    "comments": [
        {
            "commentId": 1,
            "content": "정말 기대되는 일정이야 동이야",
            "commentAuthor": "누나",
            "createdDate": "2025-11-06T12:05:06.568197",
            "updatedDate": "2025-11-06T12:05:06.568197"
        },
        {
            "commentId": 2,
            "content": "정말 기대되는 일정이야 동이야",
            "commentAuthor": "누나",
            "createdDate": "2025-11-06T12:05:08.151606",
            "updatedDate": "2025-11-06T12:05:08.151606"
        },
        {
            "commentId": 3,
            "content": "정말 기대되는 일정이야 동이야",
            "commentAuthor": "누나",
            "createdDate": "2025-11-06T12:05:58.465203",
            "updatedDate": "2025-11-06T12:05:58.465203"
        }
    ]
}
```
### 일정 수정 (PATCH /schedules/{scheduleId})
```
// Request
{
    "title": "놀이동산이 아니고 병원이었다..",
    "name" : "세상에서 제일 불쌍한 고양이",
    "password":"asd1243"
}
// Response
{
    "scheduleId": 1,
    "name": "놀이동산이 아니고 병원이었다..",
    "title": "세상에서 제일 불쌍한 고양이",
    "updatedDate": "2025-11-06T12:00:35.540722"
}
```
<img width="528" height="186" alt="image" src="https://github.com/user-attachments/assets/770e085b-ecc9-48ed-8eb0-08bdffe49982" />

처음 일정 생성 됐을 때 비밀번호와 수정할 때 입력한 비밀번호가 맞지 않을 때 전역예외처리 메세지 출력
### 일정 삭제 (DELETE /schedules/{scheduleId})
```
// Request
{
    "password":"asd1243"
}
```
<img width="552" height="122" alt="image" src="https://github.com/user-attachments/assets/fa8a4b08-ccd1-4e52-a65c-6f6dad6d031d" />

## 📊 ERD
<img width="746" height="180" alt="image" src="https://github.com/user-attachments/assets/9a3113f4-3ace-471a-84c6-9ae38a56d6cd" />

## 📁 프로젝트 구조
```
src
├── main
│   ├── java
│   │   └── com.example.scheduleproject
│   │       ├── ScheduleProjectApplication.java
│   │       ├── comment                 # 💬 댓글 도메인
│   │       │   ├── controller          # 댓글 관련 API
│   │       │   ├── dto                 # 요청/응답 DTO
│   │       │   ├── entity              # 댓글 엔티티
│   │       │   ├── repository          # 댓글 Repository
│   │       │   └── service             # 댓글 비즈니스 로직
│   │       ├── schedule                # 🗓 일정 도메인
│   │       │   ├── controller          # 일정 관련 API
│   │       │   ├── dto                 # 요청/응답 DTO
│   │       │   ├── entity              # 일정 엔티티
│   │       │   ├── repository          # 일정 Repository
│   │       │   └── service             # 일정 비즈니스 로직
│   │       ├── common
│   │       │   └── entity              # BaseEntity 등 공통 엔티티
│   │       └── exception               # ⚠️ 전역 예외 처리
│   │           ├── ErrorResponse.java
│   │           └── GlobalExceptionHandler.java
│   └── resources
│       ├── application.properties      # 설정 파일
│       ├── static                      # 정적 리소스
└──     └── templates                   # 템플릿 파일 (선택)
```
## 💡 주요 구현 사항
### 1. 3 Layer Architecture
- Controller: HTTP 요청/응답 처리, 입력 검증
- Service: 비즈니스 로직, 트랜잭션 관리
- Repository: 데이터베이스 접근
각 계층이 명확한 책임을 가지고 있어 유지보수성과 확장성이 높습니다.
### 2. N+1 문제 해결
일정 조회 시 댓글을 함께 조회할 때 발생하는 N+1 문제를 Fetch Join으로 해결했습니다.
```Java
@Query("SELECT s FROM Schedule s " +
            "LEFT JOIN FETCH s.comments " +
            "WHERE s.scheduleId = :scheduleId")
    Optional<Schedule> findByIdWithComments(@Param("scheduleId") Long scheduleId);
```
### 3. 입력값 검증
Spring Validation을 사용한 선언적 검증을 구현했습니다.
```Java
@NotBlank(message = "일정 제목은 필수입니다.")
@Size(max = 30, message = "일정 제목은 최대 30자까지 입력 가능합니다.")
private String title;
```
- 일정 제목: 최대 30자, 필수
- 일정 내용: 최대 200자, 필수
- 댓글 내용: 최대 100자, 필수
- 작성자명, 비밀번호: 필수
### 4. 전역 예외 처리
@RestControllerAdvice를 사용하여 일관된 에러 응답을 제공합니다.
```Java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(...) {
        // 400 Bad Request
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(...) {
        // 401 Unauthorized (비밀번호 불일치)
    }
}
```
### 5. JPA Auditing
생성일/수정일을 자동으로 관리합니다.
```Java
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    private LocalDateTime createdDate;
    
    @LastModifiedDate
    private LocalDateTime updatedDate;
}
```
### 6. RESTful API 설계
- 리소스 중심 URL 설계
- 적절한 HTTP 메서드 사용 (GET, POST, PATCH, DELETE)
- 명확한 HTTP 상태 코드 반환
- @RequestParam, @PathVariable, @RequestBody 적절한 활용
## 📌 개발 중 해결한 문제
### 1. N+1 문제
문제: 일정 조회 시 댓글을 가져오기 위해 추가 쿼리가 실행되는 문제
해결: Fetch Join을 사용하여 한 번의 쿼리로 일정과 댓글을 함께 조회
### 2. 필드명 불일치
문제: Entity 필드명이 스네이크 케이스로 되어 있어 JPA 쿼리 메서드가 작동하지 않음
해결: 필드명을 카멜 케이스로 변경하고 @Column으로 DB 컬럼명 매핑
### 3. LAZY 로딩과 트랜잭션
문제: 트랜잭션 범위 밖에서 지연 로딩 시도로 LazyInitializationException 발생
해결: @Transactional(readOnly = true)를 Service 메서드에 적용
***
개발 기간: 2024.11.04 - 2024.11.05
개발자: 성주연
