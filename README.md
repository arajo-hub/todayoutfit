# 👚 오늘 뭐 입지?
## 기획의도
* 기온에 따른 옷차림을 쉽게 확인할 수 있게 하기 위함
* 같은 지역의 사람들은 어떤 옷차림인지 게시글을 조회하여 참고할 수 있게 하기 위함

## 목적
**1️⃣ 업무에서 사용하지 않는 기술 및 개발방법론을 사용해보고 싶었습니다.**
* 업무에서 사용하지 않는 기술 및 개발방법론을 사용하여 각 기술의 장단점을 이해하고 사용하고 싶었습니다. 그래서 본 프로젝트에는 업무에서 사용하는 MyBatis 대신 JPA와 QueryDsl을 사용했고, 테스트코드를 작성했습니다. 테스트코드는 1년여간 리팩토링을 진행하며 코드 변경에 대한 빠른 피드백을 받을 수 있다는 장점을 느낄 수 있었습니다.

**2️⃣ 업무에서 배운 것을 활용해보고 싶었습니다.**
* 업무를 하며 배운 Logback 설정을 본 프로젝트에 활용하여 관리하기 용이한 로그를 남길 수 있도록 했습니다. 그리고 같은 기온인데도 항상 DB에서 추천정보를 조회하기 때문에 소요되는 시간을 줄일 수 없을까 해서 업무에서 배운 ehcache를 사용했습니다.

**3️⃣ 꾸준한 코드 작성과 리팩토링을 해보고 싶었습니다.**
* 업무외에도 꾸준히 코드를 작성하고 리팩토링하며 제 코드를 더 개선할 방법을 찾고 싶었습니다. 개발자 커리어를 시작하며 프로젝트를 시작하여 본인이 작성한 코드의 문제점을 찾고 개선해가는 과정에서 더 나은 코드 작성 능력을 갖추게 되었습니다.

## 문제 및 해결
**2️⃣ Open API 키 노출을 어떻게 해결할 수 있을까 고민하고 있습니다.**
* 본 프로젝트 중 Open API를 사용하는 부분이 있는데, 이때 Open API 키를 외부에 노출하지 않고 사용하는 방법을 계속 고민하고 있습니다. 변화를 거쳐 현재는 환경변수로 키를 입력받아 사용하고 있는데 더 안전한 방법이 없는지 찾아보고 있습니다.

## 기술스택
* Java
* Springboot
* PostgreSQL
* JPA
* QueryDsl
* Swagger
* Ehcache
* Thymeleaf
