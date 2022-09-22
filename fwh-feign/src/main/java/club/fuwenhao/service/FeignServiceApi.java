package club.fuwenhao.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author fwh
 * @email fuwenhao594@163.com
 * @date 2020/5/12 1:39 下午
 */
@FeignClient("fwh-feign")
public interface FeignServiceApi {
    /**
     * test
     *
     * @param
     * @return java.lang.String
     * @author fwh [2020/5/12 && 1:39 下午]
     */
    @GetMapping("test")
    String test();
}
