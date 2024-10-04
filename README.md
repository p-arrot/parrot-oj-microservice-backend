# 鹦鹉Code在线算法练习系统
## 项目简介
基于Spring Cloud微服务+MQ+Docker+Sentinel+京东Hot Key+Elastic Search的算法题目评测系统。实现用户功能、题目管理与判题服务。
- 上线网址：http://www.parrotcode.icu（若无法访问，请尝试：http://111.229.65.33）
- 项目仓库：https://github.com/p-arrot/parrot-oj-microservice-backend.git
- 代码沙箱仓库：https://github.com/p-arrot/oj-code-sandbox.git
## 快速开始
### 1. 启动依赖环境
进入项目目录，输入命令`docker-compose -f docker-compose.env.yml up -d`启动项目依赖环境。
而后连接mysql数据库，执行/mysql-init和/hotkey-dashboard中的sql文件。
### 2. 启动项目
1. 利用maven构建工具打包项目
2. 在项目目录下执行`docker-compose -f docker-compose.service.yml up -d`启动项目。
3. 启动代码沙箱（地址位于项目简介），默认端口为8281.
### 3. 登录
1. 默认管理员账号：admin，密码：1234