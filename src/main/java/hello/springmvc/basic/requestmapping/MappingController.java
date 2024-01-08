package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MappingController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/hello-basic", method = RequestMethod.GET)
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    /**
     * 편리한 축약 애노테이션
     * @GetMapping
     * @PostMapping
     * @DeleteMapping
     * @PatchMapping
     * @PutMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mappingGetV2");
        return "ok";
    }

    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     *
     * 최근 HTTP API는 리소스 경로에 식별자를 넣는 스타일을 선호한다.
     * ?userId=userA >> Query Parameter
     * /userA/1 >> Path Variable
     * 파라미터 이름과, 경로 변수가 일치하면 @PathVariable의 value을 생략할 수 있다.
     * @PathVariable("userId")
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId) {
        log.info("userId = {}", userId);
        return "ok";
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("userId = {}, orderId = {}", userId, orderId);
        return "ok";
    }

    /**
     * 특정 쿼리 파라미터로 추가 매핑
     * mode=debug인, 쿼리 파라미터가 존재해야 요청을 수행한다.
     *
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더 파리미터로 추가 매핑
     * mode라는 헤더의 값이 debug 일 때, 요청을 수행한다.
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑, Media Type, consumes
     * - 클라이언트가 보내는 메시지 바디의 미디어 타입에 따라 호출 여부를 결정한다.
     * consumes = "application/json" -> 메세지 바디가 json 타입이어야 한다.
     * consumes = "text/html" -> 메시지 바디가 html 이어야 한다.
     * consumes = "*\/*" -> 상관 없다.
     * MediaType.APPLICATION_JSON_VALUE
     *
     * @return
     */
    @PostMapping(value = "/mapping-consume", consumes="text/html")
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }


    /**
     * produces -> 해당 요청의 결과가 무엇인지 추가 매핑
     * Accept 헤더와 연관이 있다. -> 클라이언트가 어떤 컨텐츠를 받아들일 수 있는지
     * Accept : application/json -> produces="application/json"
     * @return
     */
    @PostMapping(value = "/mapping-produce", produces="text/html")
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
