package club.fuwenhao.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

/**
 * @author fwh
 * @email fuwenhao594@163.com
 * @date 2020/5/12 1:41 下午
 */
@RestController
public class FeignServiceApiImpl implements FeignServiceApi {
    @Override
    @GetMapping("test")
    public String test() {
        return "当前时间："+LocalTime.now().toString();
    }
}
