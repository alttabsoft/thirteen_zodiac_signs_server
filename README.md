# thirteen_zodiac_signs_server

게임 13 zodiac signs 의 백엔드 서버 애플리케이션입니다.

## 프로젝트 명칭 관련
자바 패키지 명은 하이픈이 들어가선 안되며, 숫자가 선행될 수 없어서 영문 + 언더스코어로 구성했습니다.

## 구동 관련
```application-credential.yml``` 파일이 필요합니다.


```yml
DB_URL: 접속할 DB의 URL
DB_USERNAME: DB 접속 계정명
DB_PASSWORD: DB 접속 계정 비밀번호
DB_DRIVER: JDBC DB 드라이버

MAIL_SMTP : SMTP를 제공하는 서비스 업체의 SMTP 주소
MAIL_PORT : 메일 포트
# 생성한다고 바로 되는 건 아님. 시간이 좀 걸린다는 것을 알아둘 것
MAIL_USERNAME : 메일을 보내는 계정명
MAIL_PASSWORD : 메일을 보낼 계정의 비밀번호

JWT_KEY : JWT 토큰키 #길고 복잡해야합니다.
JWT_HEADER : JWT 토큰 헤더명

SAVE_DIRECTORY: 클라이언트로부터 받을 파일 경로
FRONT_WEB_URL: JAVAScript 웹페이지

COMPANY_NAME : 회사명

MINIO_URL: MINIO가 작동중인 PC의 URL
MINIO_USERNAME: MINIO 접속에 필요한 계정명
MINIO_PASSWORD: MINIO 접속에 필요한 비밀번호
MINIO_COMMON_BUCKET_NAME: 파일 저장되는 Bucket명
MINIO_OBJECT_PART_SIZE: MINIO 에 업로드할 때 분할 업로드 되는 크기 (byte)
MINIO_DEFAULT_CHUNK_SIZE: MINIO 에서 다운로드할 때 분할 다운로드 되는 크기 (byte)
```

## 예시
해당 예시에서는 mysql을 사용
```yml
DB_URL: jdbc:mysql://127.0.0.1:3306/db-name
DB_USERNAME: admin
DB_PASSWORD: 1234
DB_DRIVER: com.mysql.cj.jdbc.Driver

MAIL_SMTP : smtp.gmail.com
MAIL_PORT : 587

# 메일 전송용 계정을 생성한다고 바로 되지는 않습니다. 인증까지 30분 정도 시간이 필요합니다.
MAIL_USERNAME : something.awesome.to.verify@gmail.com
MAIL_PASSWORD : 1234 5678 1234 5678

JWT_KEY : sadjivcjsxzocjas12ds798ay1ASD
JWT_HEADER : Authorization

SAVE_DIRECTORY: C:/somewhere/to/save/directory/
FRONT_WEB_URL: http:/localhost:5173

COMPANY_NAME : company name

MINIO_URL: http://localhost:9000
MINIO_USERNAME: miniousername
MINIO_PASSWORD: miniopassword
MINIO_COMMON_BUCKET_NAME: miniobucket
MINIO_OBJECT_PART_SIZE: 1000000
MINIO_DEFAULT_CHUNK_SIZE: 1000000
```
