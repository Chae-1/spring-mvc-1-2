스프링 MVC - 기본 기능
==

## 로깅(Logging)
운영 시스템에서는 `System.out.println()`같은 시스템 콘솔을 사용해 필요 정보를 출력하지 않고, 로깅 라이브러리를 사용한다.

스프링에 로깅 라이브러리 자동으로 등록돼있다. 
- `spring-boot-starter-logging`이 해당 라이브러리다.

수 많은 로그 라이브러리들을 인터페이스를 통해 통합해서 제공해주는 `SLF4J` 라이브러리를 사용한다.

스프링 부트에서는 구현체로 `LogBack`을 사용하고 있다.

`@RestController`
- `@Controller`는 반환 값이 String 이면, View 이름으로 인식하고 이를 렌더링한다.
- `@RestController`는 Http 바디에 바로 입력한다.

**로그의 포맷**
- 시간, 로그 레벨, 프로세스 ID, 쓰레드, 클래스 명, 로그 메세지
- Level : trace > debug > info > warn > error

**장점**
- 부가 정보들을 함께 볼 수 있다.
- 로그 레벨에 따라 로그를 설정할 수 있다.
- 별도의 파일이나, 네트워크로 로그 정보를 전송할 수 있다.
- 콘솔보다 성능이 우수하다.
- ex) log.info("meesage = {}", message);

## 요청 매핑
요청이 왔을 때, 호출할 컨트롤러를 지정하는 어노테이션
- `@Controller` vs `@RestController`
  - `@Controller`는 반환 값이 String 이면, 뷰 이름으로 렌더링을 한다.
  - `@RestControlller`는 반환 값이 무엇이든 Http Message Body를 통해 응답한다.

**@RequestMapping의 주요 속성**
- `headers` : 특정 헤더 값이 들어왔을 때 요청을 처리할 수 있도록 추가 매핑을 한다.
- `consumes`: 요청 데이터의 Content-Type을 지정한다. 
  - ex) consumes = application/json인 경우, 요청 메시지 바디는 항상 json 타입이어야 한다.
- `produces`: 클라이언트가 받을 수 있는 응답 타입을 지정. 즉, 클라이언트의 Accept 헤더 값을 추가적으로 매핑
  - ex) produces = application/json인 경우, 응답 메시지 바디는 항상 application/json이어야 한다.
- `params`: 클라이언트가 특정 쿼리 파라미터를 전송해야 요청을 처리하도록 추가 매핑한다.
- `method`: Http 요청 메서드를 지정해서 매핑한다.
  - 편의 기능으로 `@GetMapping`, `@PostMapping`, `@DeleteMapping`, `@PutMapping`, `@PatchMapping`이 존재한다.
  

**@PathVariable**
예를 들어 URI가 users/10 같이 리소스 경로에 식별자가 존재할 때, 이를 템플릿화 할 수 있다.
- users/{userId} 처럼 템플릿화 하고, 메서드 파리미터에 `@PathVariable`을 사용하면 이를 추출할 수 있다.
- 파라미터 이름과 `@PathVariable`의 값이 같다면, 생략 가능하다.


## HTTP 요청 파라미터 - 쿼리 파리미터, HTML Form
Http 요청 데이터를 통해 클라이언트에서 서버로 데이터를 전송하는 방법은 크게 3가지가 존재한다.
- GET - 쿼리 파라미터
  - url?username=chae&age=10
  - url의 쿼리 파리미터에 데이터를 포함해서 전달
- POST - HTML Form
  - content-type : x-www-form-url-encoded
- HTTP Message Body 직접 전송


스프링 MVC도 서블릿 방식과 마찬가지로, GET 방식과 HTML Form 방식을 조회하는 방법은 동일하다.

스프링은 `@RequestParam` 애노테이션을 이용해 쿼리 파라미터를 바로 조회할 수 있다.

### Get, Post-Html Form 방식에서의 데이터 조회
**@RequestParam**
- name 속성을 통해 쿼리 파라미터를 지정
- 쿼리 파라미터가 존재하지 않을 수도 있다. 이때는 required 속성을 이용해 필수 값이 아니라고 설정한다.
- defaultValue 속성으로 defaultValue를 지정할 수 있다.

**@ModelAttribute**
요청 파라미터를 받아 객체를 생성하는 것이 아니라, 스프링이 이 과정을 자동화해주는 기능이 존재한다.

`@ModelAtrribute`를 이용하면, 스프링에서 자동으로 객체에 쿼리 파라미터를 받아서 셋팅해준다.
- `@ModelAttribute`는 생략 할 수 있다.
- 애노테이션을 생략했을 때 자동으로 단순 타입일 경우 `@RequestParam`을 적용되고, Argument Resolver에 등록되지 않은 객체는 `@ModelAttribute`가 적용된다.

### Http Message Body 조회
Http message Body에 직접 데이터를 담아서 요청할 수 있다.
- 보통, JSON, XML, TEXT가 전달된다.
- POST, PUT, PATCH에서 사용

`@RequestParam`, `@ModelAttribute`는 Html Form, 쿼리 파라미터 조회시 사용되고, Message Body의 내용을 읽을 수 없다.

Message Body의 내용을 그대로 읽어 들일 수 있는 애노테이션인 `@RequestBody`가 존재한다.

그리고 클래스로는 HttpEntity가 존재한다.

**HttpEntity**
- Http Header, Body를 편리하게 조회할 수 있는 클래스
- 응답, 요청 양쪽에서 사용 가능하다.
- `@RequestParam`, `@ModelAttribute`와는 다르다.
- 이를 상속한 클래스에는 RequestEntity, ResponseEntity가 존재한다. 각각, 요청, 응답에서 사용하는 HttpEntity이다.
- `ResponseEntity`는 상태 코드를 바로 지정할 수 있다.
- `RequestEntity`는 HttpMethod, uri 정보가 추가된 클래스

**@RequestBody, @ResponseBody**

`@RequestBody`를 통해 Http Message Body의 정보를 편리하게 조회할 수 있다.
- JSON 타입의 데이터가 넘어 올때도 사용이 가능하다. 즉, `@ModelAttribute` 처럼 객체를 받을 수 있다.

`@ResponseBody`를 통해 편리하게 Http Message Body에 정보를 삽입할 수 있다.

이들 모두, 메세지 컨버터가 동작한다.

## HTTP 응답 - 메시지 바디, HTTP API
- 서블릿 방식으로 처리, ResponseEntity를 활용, @ResponseBody를 활용하는 것으로 HTTP Message Body로 넘길 수 있다.
- 응답 코드를 동적으로 처리해야 할 경우, HttpEntity를 활용하고 대부분의 경우에는 @ResponseBody를 활용한다.


## HTTP 메세지 컨버터
뷰 템플릿으로 HTML을 생성해서 응답하는 것이 아닌, HTTP API처럼 JSON 데이터를 HTTP 메시지 바디에서 직접 읽거나 쓰는 경우 메세지 컨버터를 사용한다.

**@ResponseBody**
- 요청이 오면, 컨트롤러가 호출된다.
- ViewResolver 대신, `HttpMessageConverter`가 동작한다.
- 반환타입이 String 이면, `StringHttpMessageConverter`, 객체라면 `MappingJackson2HttpMessageConverter`
- byte 처리 등, 기타 여러 컨버터가 존재한다.

스프링 MVC는 다음의 경우 HTTP 메시지 컨버터를 적용
- HTTP 요청: `@RequestBody`, `HttpEntity(RequestEntity)`
- HTTP 응답: `@ResponseBody`, `HttpEntity(ResponseEntity)`

메시지 컨버터는 `HttpMessageConverter` 인터페이스이다.
- 요청, 응답에서 둘 다 사용된다.
- 메서드 목록
   - `canRead`, `canWrite`: 메시지 컨버터가 해당 클래스, 미디어 타입을 지원하는지 확인 
   - `read`, `write`: 메시지 컨버터를 통해 메시지를 읽고 쓰는 기능.

기본으로 등록된 컨버터는 다음과 같다.
1. ByteArrayMessageConverter
2. StringHttpMessageConverter
3. MappingJackson2HttpMessageConverter

등이 존재한다.

메세지 컨버터는 `@RequestParam`, `@ModelAttribute` 애노테이션 동작하는 것이 아니다.

`@ResponseBody`, `@RequestBody`, `HttpEntity` 일 때, 동작한다.

- `ByteArrayHttpMessageConverter`: `byte[]` 데이터를 처리한다.
  - 클래스 타입: `byte[]`, 미디어 타입: `*/*`
  - 요청 ex) @RequestBody byte[] data
  - 응답 ex) @ResponseBody return byte[], 쓰기 미디어 타입: `application/octet-stream`
- `StringHttpMessageConverter`: `String` 문자로 데이터를 처리한다.
  - 클래스 타입: `String`, 미디어 타입: `*/*`
  - 요청 ex) @RequestBody String messageBody
  - 응답 ex) @ResponseBody return message 쓰기 미디어 타입: `text/plain`
- `MappingJackson2HttpMessageConverter`: application/json
  - 클래스 타입: 객체, `HashMap`, 미디어 타입: `application/json`
  - 요청 ex) @RequestBody HelloData data
  - 응답 ex) @ResponseBody return helloData

**HTTP 요청 데이터 읽기**
    - HTTP 요청이 오고, `@RequestBody`, `HttpEntity`를 사용
- 메세지 컨버터 리스트에서 `canRead` 호출
  - 대상 클래스를 지원하는지
    - ex) `@RequestBody` (`byte[]`, `String`, 사용자 정의 클래스)
  - Content-Type을 지원하는지
    - ex) text/plain, application/json, */*
- `canRead()`조건을 만족하면, `read()`를 호출해서 객체 생성 후 반환.

**HTTP 응답 데이터 생성**
- 컨트롤러에서 `@ResponseBody`, `HttpEntity`로 반환될 경우 동작
- 메세지 컨버터가 메시지를 쓸 수 있는지 확인하기 위해 `canWrite()` 호출
  - 대상 클래스를 지원하는가
  - Http 요청의 Accept 미디어 타입을 지원하는 가
    - `@RequestMapping`의 `produces`
    - `text/plain`, `application/json`, `*/*`
- `write`를 호출해서 HTTP 응답 메시지 바디에 데이터를 생성한다.

**예시**

```text

  content-type : application/json
  
  @RequestMapping
  public String test1(@RequestBody String message) ...
```

`@RequestBody`의 적용 대상 클래스가 `String` 이면, content-type에 상관없이 `StringHttpMessageConverter`가 선택된다.


```text
content-type : text/plain

@RequestMapping
public String test2(@RequestBody byte[] message)
```
`@RequestBody` 적용 클래스가 `byte[]`이면, content-type에 상관없이, `ByteArrayHttpMessageConverter`가 선택된다.

```text
content-type : application/json

@RequestMapping
public String test2(@RequestBody HelloData data)
```

`@RequestBody` 적용 클래스가 사용자 정의 클래스이고, content-type이 application/json 이라면, `MappingJackson2HttpMessageConverter`가 선택된다.


## 요청 매핑 핸들러 어댑터 구조
HTTP 메시지 컨버터가 사용되는 위치
- 컨트롤러를 직접 호출하는 위치는 핸들러 어댑터에서 호출한다.
- 즉, `RequestMappingHandlerAdapter`에서 메시지 컨버터에 대한 모든 것이 처리된다.

![스프링mvc구조일부.png](%EC%8A%A4%ED%94%84%EB%A7%81mvc%EA%B5%AC%EC%A1%B0%EC%9D%BC%EB%B6%80.png)

`@RequestMapping`을 사용할 때, 핸들러로는 `RequestMappingHandlerMapping`, `RequestMappingHandlerAdapter`가 적용된다.

`RequestMappingHandlerAdapter`에서 Controller를 호출하는 데 필요한 인자 목록을 처리해주는 역할을 수행하는 것이 `ArgumentResolver`다.


### HandlerMethodArgumentResolver
컨트롤러가 다양한 파라미터를 활용할 수 있었던 이유가 바로 `ArgumentResolver` 덕분이다.
컨트롤러에서 필요한 다양한 파라미터를 생성해서 컨트롤러를 호출할 수 있도록 해준다.
- 이 덕분에 컨트롤러에서 `HttpServletRequest`, `Model`, `ModelAttribute` 같은 애노테이션을 처리할 수 있었다.
- `@RequestBody`, `HttpEntity` 같은 HTTP 메세지를 처리할 때, Http 메시지 컨버터를 사용한다.


### HandlerMethodReturnValueHandler
`ArgumentResolver`와 비슷한 방식으로 동작한다. 반환 값을 
- 응답에서 `@ResponseBody`, `HttpEntity`를 처리하는 할 때, 메시지 컨버터를 사용한다.

