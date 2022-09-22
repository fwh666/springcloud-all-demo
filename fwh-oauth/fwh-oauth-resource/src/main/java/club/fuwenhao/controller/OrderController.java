package club.fuwenhao.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fwh
 * @email fuwenhao594@163.com
 * @date 2020/5/29 2:16 下午
 */
@RestController
@Slf4j
public class OrderController {
    @PostMapping("orderComplete")
    public String orderComplete() {
        return "订单完成";
    }
}
