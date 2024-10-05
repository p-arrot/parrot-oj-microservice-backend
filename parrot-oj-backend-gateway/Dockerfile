# 使用官方的JDK 17镜像作为基础
FROM openjdk:17

# 设置工作目录
WORKDIR /app

# 将jar包复制到工作目录
ADD target/parrot-oj-backend-gateway-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

# 设置容器启动命令
CMD ["java", "-jar", "/app/parrot-oj-backend-gateway-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]
