package club.fuwenhao.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MailUtils {

    private static final Logger log = LoggerFactory.getLogger(MailUtils.class);

    private MailUtils() {
    }

    public static String sendJDYunMail(String mailReceiver, String title, String mail) {
        try {
            title = URLEncoder.encode(title, "UTF-8");
            mail = URLEncoder.encode(mail, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LogUtil.error(log, "转码出现异常：{}",e);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("https://chanye.xjoycity.com/admin/ai/sendMail?")
                .append("emailAddress=").append(mailReceiver)
                .append("&title=").append(title)
                .append("&content=").append(mail);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type","text/html;charset=UTF-8");
        return HttpUtils.post(sb.toString(),"");
    }

}
