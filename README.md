# 프로젝트 정보


## 개요

프로젝트명 : 게시판 with Java Spring

인원 : BE 1명


## DB SCHEME (24.10.01 ver)

![RDB ERD SCHEME(24 10 01 ver)](https://github.com/user-attachments/assets/2db3a503-71cd-43d3-b31a-df4ec7eef3e3)




## 프로젝트 스펙

- JDK 17


- SPRING BOOT 3.3.4


- MYSQL 8.0.39



# 프로젝트 목표

> ✨ 견고한 프로그램을 만들어보자

## 주안점

- **에러 핸들링**

    Controller 단 이하의 모든 exception을
    Controller로 되던지고 
    GlobalExceptionHandler 구현하여 처리

  
- **유효성 검사**

    1차 : DTO 유효성 검사

    2차 : Entity 유효성 검사

    3차 : DB 유효성 검사



- **동시성 문제**

  문제 발생 시에 문제 및 해결과정 기술 예정


- **로깅**

    logback -> log4j2 마이그레이션 (예정)


- **모니터링**

    Prometheus를 통한 메트릭 데이터 수집 (예정)

    Grafana를 통한 메트릭 데이터 시각화 (예정)



- **보안 (암호화, 인증/인가)**

    JCEKS, PKCS12 키스토어 구현

    AES 키 및 대칭키 암호화 구현

    RSA 키 및 비대칭키 암호화 구현


- **테스트 코드**

    구현 예정