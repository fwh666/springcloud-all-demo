# 使用：
## endpoint：
/oauth/authorize(授权端，授权码模式使用)
/oauth/token(令牌端，获取 token)
/oauth/check_token(资源服务器用来校验token)
/oauth/confirm_access(用户发送确认授权)
/oauth/error(认证失败)
/oauth/token_key(如果使用JWT，可以获的公钥用于 token 的验签)
## 密码模式：
- 获取token：
    - URL：http://127.0.0.1:8888/oauth/token
    - 请求方法：post
    - 请求头：
        - Authorization：Basic YXBpLWdhdGV3YXk6c21seg==
        - Content-Type： application/x-www-form-urlencoded
    - 请求体：
        - grant_type ：password
        - scope： read
        - username：admin
        - password：admin
- 验证token： 
    - URL：http://127.0.0.1:8888/oauth/check_token?token=8a04ffb0-b8d1-4204-9ea5-5aa8cb218652
    - 请求方法：get
    - 请求头：
            - Authorization：Basic YXBpLWdhdGV3YXk6c21seg==
- 获取用户信息：（手写代码）
    - URL：http://127.0.0.1:8888/user/getCurrentUser?access_token=8a04ffb0-b8d1-4204-9ea5-5aa8cb218652
## 授权码模式：
- 获取授权码code：
    - URL：http://127.0.0.1:8888/oauth/authorize?response_type=code&client_id=portal_app&redirect_uri=http://portal.tuling.com:8855/callBack&state=http://portal.tuling.com:8855/product/1
        - response_type=code 表示授权码模式
        - client_id 客户端id
        - redirect_uri 重定向地址
        - state 用于拼接传递，前面传递过来后续使用
        - 响应：http://portal.tuling.com:8855/callBack?code=ooF9o7
            - code对应值为授权码
- 根据授权码code获取token：
    - URL：http://127.0.0.1:8888/oauth/token/
    - 请求方法：post
    - 请求头：
        - Authorization：Basic YXBpLWdhdGV3YXk6c21seg==
        - Content-Type： application/x-www-form-urlencoded
    - 请求体：
        - grant_type ：authorization_code
        - code： 上面获取的值
        - redirect_uri： 重定向地址URL
## JWT
- 生成证书命令：
    - keytool -genkeypair -alias jwt -keyalg RSA -keysize 2048 - keystore D:/jwt/jwt.key
- 查看证书命令：
    - keytool -v -list -keystore jwt.key
- AuthServerInDbConfig服务配置tokenStore中配置JWT
- TokenEnhancer实现类。token的增强器，可以根据自己的业务定制 
## 参考地址：
- https://www.cnblogs.com/Irving/p/9430460.html