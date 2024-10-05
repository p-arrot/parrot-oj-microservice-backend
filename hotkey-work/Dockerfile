# 基础镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 将jar包复制到工作目录
ADD worker-0.0.4-SNAPSHOT.jar .

EXPOSE 8180

# 设置容器启动命令
CMD ["java", "-jar", "/app/worker-0.0.4-SNAPSHOT.jar","--spring.profiles.active=prod"]



