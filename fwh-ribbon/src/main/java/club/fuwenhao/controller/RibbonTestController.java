package club.fuwenhao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author fwh
 * @email fuwenhao594@163.com
 * @date 2020/5/12 10:39 上午
 */
@RestController
@RequestMapping("ribbon")
public class RibbonTestController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test")
    private Object test(){
        final ResponseEntity<String> forEntity = restTemplate.getForEntity("http://fwh-web/ribbon/test", String.class);
        System.out.println(forEntity.toString());
        return forEntity;
    }
    @GetMapping("hello")
    private Object hello(){
        final ResponseEntity<String> forEntity = restTemplate.getForEntity("http://fwh-web/ribbon/hello", String.class);
        System.out.println(forEntity.toString());
        return forEntity;
    }
}
