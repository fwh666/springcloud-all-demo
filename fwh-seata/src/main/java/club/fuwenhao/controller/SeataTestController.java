package club.fuwenhao.controller;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fwh
 * @email fuwenhao594@163.com
 * @date 2020/5/27 4:26 下午
 */
@RestController
@RequestMapping("seata")
public class SeataTestController {
    @GlobalTransactional(name = "test", rollbackFor = Exception.class)
    public String test() {
        //调用本地service服务
        //调用其他service服务
        //手动异常
        System.out.println(1/0);
        return "test";
    }
}
