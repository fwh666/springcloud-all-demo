package club.fuwenhao.controller;

import club.fuwenhao.vo.Result;
import club.fuwenhao.vo.SystemErrorType;
import club.fuwenhao.vo.TokenInfo;
import club.fuwenhao.config.MDA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fwh
 * @email fuwenhao594@163.com
 * @date 2020/5/28 9:59 上午
 */
@RestController
@Slf4j
public class LoginController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/login")
    @ResponseBody
    public Result login(@RequestParam(value = "username") String userName,
                        @RequestParam(value = "password") String password,
                        HttpServletRequest request) {

        ResponseEntity<TokenInfo> response;
        try {

            //通过网关去认证服务器上获取tokenInfo的信息
            response = restTemplate.exchange(MDA.AUTH_SERVER_URL, HttpMethod.POST, warpRequest(userName, password), TokenInfo.class);

        } catch (Exception e) {
            log.error("userName:{},password:{}去认证服务器登陆异常,异常信息:{}", userName, password, e);
            return Result.fail(SystemErrorType.LOGIN_FAIL);
        }

        settingToke2Session(response, request, userName);

        return Result.success(SystemErrorType.LOGIN_SUCCESS);
    }

    private void settingToke2Session(ResponseEntity<TokenInfo> responseEntity, HttpServletRequest request, String userName) {
        //把token存放到session中
        TokenInfo tokenInfo = responseEntity.getBody();
        tokenInfo.setLoginUser(userName);
        request.getSession().setAttribute(MDA.TOKEN_INFO_KEY, responseEntity.getBody());
    }
    private HttpEntity<MultiValueMap<String, String>> warpRequest(String userName, String password) {
        //封装oauth2 请求头 clientId clientSecret
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBasicAuth(MDA.CLIENT_ID,MDA.CLIENT_SECRET);


        //封装请求参数
        MultiValueMap<String, String> reqParams = new LinkedMultiValueMap<>();
        reqParams.add("username",userName);
        reqParams.add("password",password);
        reqParams.add("grant_type","password");
        reqParams.add("scope","read");

        //封装请求参数
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(reqParams, httpHeaders);

        return entity;
    }
}
