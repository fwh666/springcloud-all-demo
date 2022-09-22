## 功能：
- 流控
- 降级
- 熔断
- 授权
## 使用：  
- 依赖：   
```
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    </dependency>
```
- 配置：   
cloud:
    sentinel:
      transport:
        dashboard: 127.0.0.1:8080
- 服务端启动：    
- 服务端配置：
    - 流控：
    - 降级：
    - 热点：
    - 授权：
## 参考：
- https://github.com/alibaba/Sentinel/wiki/%E4%BB%8B%E7%BB%8D 