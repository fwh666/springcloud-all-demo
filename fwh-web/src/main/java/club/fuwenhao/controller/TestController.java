package club.fuwenhao.controller;

import club.fuwenhao.aspect.WebLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;

/**
 * @author fwh
 * @email fuwenhao594@163.com
 * @date 2020/5/9 9:57 上午
 */
@RestController
@RequestMapping("web")
@WebLog(description = "测试控制层")
public class TestController {
    @GetMapping("hello")
    public String hello(HttpServletRequest request, @RequestParam("argName") String argName) {
        return "方法：" + request.getMethod() + "参数argName为：" + argName + ",hello:" + LocalTime.now();
    }
}
