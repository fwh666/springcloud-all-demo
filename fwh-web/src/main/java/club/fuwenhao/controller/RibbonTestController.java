package club.fuwenhao.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 服务提供者不需要加载Ribbon依赖
 *
 * @author fwh
 * @email fuwenhao594@163.com
 * @date 2020/5/12 10:25 上午
 */
@RestController
@RequestMapping("ribbon")
public class RibbonTestController {
    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${server.port}")
    private String port;

    @GetMapping("test")
    public String test(HttpServletRequest request) {
        final StringBuffer requestURL = request.getRequestURL();
        final Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder stringBuilder = new StringBuilder();
        parameterMap.forEach((key, value) -> {
            stringBuilder.append(key).append(":").append(value);
        });
        return requestURL + "---->" + stringBuilder.toString();
    }

    @GetMapping("hello")
    public String hello(HttpServletRequest request) {
        return request.getRequestURL() + serviceName + port;
    }
}
