# 基础镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 将jar包复制到工作目录
ADD dashboard-0.0.2-SNAPSHOT.jar .

EXPOSE 8181

# 设置容器启动命令
CMD ["java", "-jar", "/app/dashboard-0.0.2-SNAPSHOT.jar","--spring.profiles.active=prod"]
