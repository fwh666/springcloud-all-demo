# 简易逻辑
## 涉及服务：
- fwh-Ribbon ： 作为服务的消费者-需要依赖Ribbon
- fwh-web： 作为服务的提供者-不需要依赖Ribbon
    - 使用不同的端口启动 8080、9090

## Ribbon服务配置：
- 引用依赖
- 配置文件
- 启动注解

## 测试流程
- 请求：http://localhost:8181/ribbon/hello
    - 默认轮询方式。分别访问8080、9090端口的服务

## 参见文档：
- http://c.biancheng.net/view/5356.html