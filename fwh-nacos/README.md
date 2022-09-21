## 使用：

- 通用配置：
    - 可以将通用的部分抽取出来使用
- 页面上的Data ID:
    - 对应的application-name 和 profile-active 的名称
    - 例如：config-app-local.yml
- 加载顺序：
    - 可以根据控制台输出的信息，判断优先加载的哪个、
    ```
        上述配置 加载的优先级
        1)order-center-dev.yml 精准配置 2)order-center.yml 同工程不同环境的通用配置 3)ext-config: 不同工程 通用配置
        3.1):common4.yml
        3.2): common3.yml
        4) shared-dataids 不同工程通用配置
        4.1)common2.yml 4.2)common1.yml
    ``` 
- 动态刷新：
    - 记得控制层上添加注解：@RefreshScope
- 配置回滚：
    - 页面上选择对应的版本回滚
- 内置数据库：
    - Derby连接启动nacos服务，选择data的数据
    - 注意：必须要停止服务，才能链接上
    - 集群情况：
        - 由于要保持数据同步，所以建立属于自己的数据库
- 生成密码：
    - 依赖security。 调用new passwordEncode方法生成。

## 备份：

- todo：nacos的配置内容