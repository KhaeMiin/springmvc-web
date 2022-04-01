# springmvc1-web

## 스프링 MVC - 웹 페이지 만들기(간단하게)

## 프로젝트 설정
1. Project:
	- Gradle Project
	- Language: Java 11
	- Spring Boot: 2.6.5
2.  Dependencies
	- SpringWeb
	- Thymeleaf
	- Lombok
3. Pachaging: **Jar**
	- 내장 서버(톰캣등)을 사용하고, 'webapp'경로도 사용하지 않습니다. (내장 서버 사용에 최적화)

### JSP가 아닌 Thymeleaf를 쓰는 이유는?
- JSP와 Thymeleaf의 가장 큰 차이점은 JSP와 달리 **Servlet Code**로 변환되지 않는다
- 순수 HTML구조를 유지한다.**(Natural Tamplate)**
- **따라서 비즈니스 로직과 분리되어 오로지 View에 집중할 수 있다.**
- 서버상에서 동작하지 않아도 된다.(JSP는 서버를 통해 Servlet코드로 변환되어 랜더링 된다)
- Jar 패키징이 가능하다.(사실 이것의 장점을 아직 잘 모르겠다. 더 공부해야 할 듯)

### Thymeleaf의 동작 원리
![image](https://user-images.githubusercontent.com/91078445/161205107-6fa3e42d-7fca-44d2-88df-22272bcaecb3.png)

1. Web Browser가 Http Method(GET)을 통해 hello 전달
2. 이것을 톰켓 서버가 맨 처음 받아 Spring Boot에 전달
3. Spring Boot는 helloController에게 내용을 전달 해 주고, Method안에 있는 문자열 형식 hello를 반환
4. 반환된 값과 model을 통해 data:hello!!가 작성된 내용을 viewResolver에게 전달
5. viewResolver는 uri가 hello인 것이 반환값과 같기 때문에 그것에 맞는 화면인 main/resources/templates/hello.html을 찾아서 반환

### JSP의 동작 원리
#### 1. JSP에 해당하는 서블릿이 존재하지 않은 경우
1. JSP 페이지로 부터 자바 코드를 생성한다.(변환 translation 단계)
2. 자바 코드를 컴파일 하여 서블릿 클래스를 생성한다. (컴파일 단계)
3. 서블릿에 클라이언트 요청을 전달한다.
4. 서블릿이 요청을 처리한 결과를 응답으로 생성한다.
5. 응답을 웹 브라우저로 전송한다.

#### 2. JSP에 해당하는 서블릿이 존재하는 경우
1. 서블릿에 클라이언트 요청을 전달
2. 서블릿이 처리한 결과를 응답으로 생성
3. 응답을 브라우저로 전송

<br>

- JSP 페이지를 요청하는 경우 직접 실행하는 것이 아니라 , **JSP를 자바 소스 코드로 변환**한 뒤 **컴파일** 해서 생성한 **서블릿을 실행**하는 것이다.
- JSP를 실행한다는 말은 컴파일한 결과인 서블릿 클래스를 실행한다는 의미이다.
(서블릿 : 클라이언트의 요청을 처리하여 결과를 처리해주는 역할을 하는 자바 프로그램, 흔히 CGI라고도 함)

