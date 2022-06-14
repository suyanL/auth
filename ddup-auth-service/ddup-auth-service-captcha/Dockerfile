# 基础镜像
FROM arm64v8/openjdk:8-jdk-oracle
# 工作目录
WORKDIR /
# 维护着信息
MAINTAINER suancyg
#在build镜像时可以通过 --build-args profile=xxx 进行修改
ARG profile
ARG port
ARG version
ARG project
ENV SPRING_PROFILES_ACTIVE=${profile}
#项目的端口
EXPOSE ${port}
WORKDIR /mnt

# 设置时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV LC_ALL "zh_CN.UTF-8"

COPY ./target/your-project-name-1.0-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java", "-jar", "/mnt/app.jar"]