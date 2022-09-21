package club.fuwenhao.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fwh
 * @email fuwenhao594@163.com
 * @date 2020/5/13 11:34 上午
 */
@RestController
@RequestMapping("nacos")
@RefreshScope
public class TestController {
    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${server.port}")
    private String port;

    @Value("${is_true}")
    private String isTrue;

    @GetMapping("test")
    public String test() {
        if (isTrue != null) {
            return "结果为--->服务名称:" + serviceName + "服务端口:" + port + "isTure:" + isTrue;
        }
        return "结果为--->服务名称:" + serviceName + "服务端口:" + port;
    }
}
