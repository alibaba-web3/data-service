web3 data process service

系统架构:

![](https://i.328888.xyz/2023/02/27/eOx3c.png)

目录结构：
start: SpringBoot 启动目录

web: Controller 目录，对外接口

service: 业务逻辑

framework: 外部服务调用

dal: 数据库连接层，包含 mapper 和 SQL

crawler: 服务调度，定时执行特定任务

deployment:

```bash
mvn clean compile package

nohup java -jar start/target/start-0.0.1-SNAPSHOT.jar &
```

online endpoint:
http://8.222.145.211

stage endpoint:
http://8.222.145.211:8082

frontend:
https://alibaba-web3.vercel.app

swagger:
http://8.222.145.211/swagger-ui/index.html#/

Jenkins:
http://8.222.145.211:9000