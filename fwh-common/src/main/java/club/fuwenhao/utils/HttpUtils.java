package club.fuwenhao.utils;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    private HttpUtils() {
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @return java.lang.String
     * @author fwh [2021/2/5 && 5:03 下午]
     */
    public static String post(String url, String params) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String result = "";
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(params, "UTF-8");
        httpPost.setEntity(entity);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            LogUtil.info(log, "响应状态为:{}", response.getStatusLine());
            if (responseEntity != null) {
                LogUtil.info(log, "响应内容长度为:{}", responseEntity.getContentLength());
                result = EntityUtils.toString(responseEntity, "UTF-8");
                LogUtil.info(log, "响应内容为:{}", result);
            }
        } catch (ClientProtocolException e) {
            LogUtil.error(log, "客户端出现异常：{}", e);
        } catch (ParseException e) {
            LogUtil.error(log, "转换异常：{}", e);
        } catch (IOException e) {
            LogUtil.error(log, "IO流异常：{}", e);
        } finally {
            try {
                // 释放资源
                httpClient.close();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LogUtil.error(log, "流关闭异常：{}", e);
            }
        }
        return result;
    }
}
