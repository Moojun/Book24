# Stock 24

### 프로젝트 주제: 실시간 국내 주가 차트 조회 서비스



### 개발환경

* OS: MacOS
* IDE : Intellij
* 템플릿 엔진 : JSP
* 데이터베이스: MySQL, AWS RDS(MySQL)
* 웹 서버: AWS EC2
* 웹 어플리케이션 서버: Tomcat 9.0
* 도메인 주소: AWS Elastic IP
* Build Tool: Gradle



### 구현할 기능 설계

* 화면별 View 구현: JSP, css, Javascript
* 회원가입, 로그인, 로그아웃: Session + Database
* Session 기반으로 회원/비회원 기능 분화: Session + JSTL, EL
* 주식 검색(메인화면) 및 결과 화면에 표시: Database + Session + JSTL, EL
* 주식의 현재가를 실시간으로 가져오기: Jsoup library를 이용한 웹 크롤링
* 실시간 주가를 차트로 표시: Javascript anyChart library 사용
* 게시글 작성: Service 구현
* 마이페이지 구현(관심 목록 및 나의 게시글 목록): Session + Database
* 프로젝트 최종 배포: AWS EC2



### AWS cloud 환경 구축

<img width="846" alt="Screenshot 2023-03-05 at 11 05 07 PM" src="https://user-images.githubusercontent.com/80478750/222965278-d34f5e75-f46c-4633-b694-2398e11961cd.png">

* VPC: 독립적인 가상의 네트워크 공간으로, Public Subnet과 Private Subnet 등 의 네트워킹 환경을 설정하기 위해 생성하였다.
* Route table: 모든 Subnet은 Route table을 가져야 하며, Public Subnet과 Private Subnet을 구분하기 위해, Internet Gateway(IG)를 Route table에 연결한 뒤, IG를 Public Subnet에 추가로 규칙을 적용해서 외부에서도 접속할 수 있도록 하였으며, Private Subnet에는 외부에서 Database로 접근하는 것을 방지하기 위해 규칙을 적용하지 않았다.

* EC2: 웹 서버를 사용하기 위해 생성하였으며, Elastic IP 기능을 추가하여 웹 사이트에 고정된 IP 주소를 할당하였다
* RDS: 여러 관계형 데이터베이스 중 MySQL을 사용하였다. EC2에 MySQL을 설치하지 않고 굳이 RDS를 사용한 이유는, 보안을 더 철저하게 하기 위해서이다.



### EC2에 프로젝트 배포 과정

* [참고 링크](https://github.com/Moojun/TIL/blob/main/AWS/Develop-Environment/EC2%EC%97%90%20JSP%2C%20Servlet%20%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%20%EB%B0%B0%ED%8F%AC.md)

### ERD Cloud
![Stock24](https://user-images.githubusercontent.com/80478750/230726720-0c22b493-21f7-4393-80ca-17cf0bf91c36.png)



### Commit Convention

- `feat`: new feature for the user, not a new feature for build script
- `fix`: bug fix for the user, not a fix to a build script
- `docs`: changes to the documentation
- `style`: formatting, missing semi colons, etc; no production code change
- `refactor`: refactoring production code, e.g. renaming a variable
- `test`: adding missing tests, refactoring tests; no production code change
- `chore`: Changes to the build process or auxiliary tools and libraries such as documentation generation. (updating grunt tasks, etc; no production code change)
- `comment`: add or revise the comments
- `rename`: rename file or directory
- `remove`: remove file
- `code-change`: this option is added by me. If the code changes, but no production code changed(e.g. rename the title in jsp page, etc..)



### Reference link

* [git: Commit Convention message](https://www.conventionalcommits.org/en/v1.0.0/)
