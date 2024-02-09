### 환경 설정

- Spring Boot 3.2.2 
- Gradle 
- Java 17

### 의존성 정보

- Spring Web 
- Lombok 
- Spring Data JPA 
- H2 Database 
- MySQL Driver 
- Validation

### 코드 컨벤션

- 지역변수 및 파라미터에 ```final``` 키워드 적용
- 클래스 내부 첫 줄에 개행 적용
- ```repository```에서 find 조회 시 ```Optional``` 반환
- 컬랙션 반환 시 Immutable 고려

### 커밋 컨벤션

- 제목 타입
  - feat: 새로운 기능 추가
  - fix: 버그 수정
  - docs: 문서 수정
  - refactor: 코드 리펙토링
  - test: 테스트 코드 작성
  - style: 단순 수정
  - chore: 코드 수정 없이 설정 변경


- Commit 예시

```feat: Member Repository 기능 및 테스트 구현```

- Pull Request 예시

```
feat: 회원 CRUD 기능을 구현

* feat: Member Repository 기능 및 테스트 구현

* feat: Member Service 기능 및 테스트 구현

* feat: Member Controller 기능 및 인수테스트 구현
```
