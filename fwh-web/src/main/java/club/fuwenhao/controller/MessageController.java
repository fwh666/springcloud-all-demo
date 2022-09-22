package club.fuwenhao.controller;

import club.fuwenhao.aspect.WebLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: fwh-parent
 * @description: 短信验证功能
 * @author: fwh
 * @date: 2021-05-18 14:27
 **/
@WebLog
@RestController
public class MessageController {
    @Autowired
    private JavaMailSender mailSender;


    @PostMapping("/messageCheck")
    public String messageCheck(@RequestParam("phone") String phone) {
        //手机号发短信
        //短信验证码校验
        //校验通过
        return null;
    }

    /**
     * 发送邮件
     *
     * @param
     * @return java.lang.Boolean
     * @author fuwenhao
     * @date 2021/5/18 3:57 下午
     */
    @PostMapping("/mailSend")
    public Boolean mailSend() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@163.com");
        message.setTo("test@qq.com");
        message.setSubject("it is a test for spring boot");
        message.setText("你好，我是小黄，我正在测试发送邮件。");
        try {
            mailSender.send(message);
        } catch (MailException e) {
            e.getMessage();
            return false;
        }
        return true;
    }
}
