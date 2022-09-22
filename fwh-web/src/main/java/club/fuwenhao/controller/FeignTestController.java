//package club.fuwenhao.controller;
//
//import club.fuwenhao.service.FeignServiceApi;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author fwh
// * @email fuwenhao594@163.com
// * @date 2020/5/12 1:36 下午
// */
//@RestController
//@RequestMapping("feign")
//public class FeignTestController {
//    @Autowired
//    private FeignServiceApi feignServiceApi;
//
//    @GetMapping("test")
//    public String test(){
//        final String test = feignServiceApi.test();
//        return test;
//    }
//}
