# 👚 오늘 뭐 입지?
## 기획의도
* 기온에 따른 옷차림을 쉽게 확인할 수 있게 하기 위함
* 같은 지역의 사람들은 어떤 옷차림인지 게시글을 조회하여 참고할 수 있게 하기 위함

## 기술스택
* Java
* Springboot
* PostgreSQL
* JPA
* QueryDsl
* Swagger
* Ehcache
* Thymeleaf

## 특이사항
* 업무에서는 작성하지 않는데 테스트코드의 이점을 느껴보고자 테스트코드 작성
* Logback 설정에 대해 알아보며 Logback 도입([🔗 Logback 설정에 관하여](https://dakafakadev.tistory.com/144))
* API Documentation에 대한 필요로 Swagger 도입
* 기온에 대한 추천정보를 캐싱하여 빠르게 제공할 수 있도록 함(ehcache 사용)
* 업무에서 사용하는 MyBatis 외의 다른 방법을 사용해보고자 JPA를 사용
* 동적쿼리를 보다 편리하게 작성하기 위해 QueryDsl 사용
* 개발자 커리어를 시작하며 프로젝트를 시작하여 1년넘게 코드 리팩토링 수행 및 신규 기능 도입
