package club.fuwenhao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author fwh
 * @email fuwenhao594@163.com
 * @date 2020/5/12 1:40 下午
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class,args);
        System.out.println("feign is start");
    }
}
