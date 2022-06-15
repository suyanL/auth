# 基础镜像
FROM arm64v8/openjdk:8-jdk-oracle
# 工作目录
WORKDIR /
# 维护着信息
MAINTAINER suancyg
# 将系统编码设置为c.utf-8，默认的POSIX不支持中文
ENV LANG C.UTF-8
ENV LANGUAGE C.UTF-8
ENV LC_ALL C.UTF-8
# 将子项目打包的jar包拷贝到项目根目录
COPY target/ddup-auth-service-captcha-1.0.0-SNAPSHOT.jar /ddup-auth-service-captcha.jar
# 设置容器启动时执行的命令,-Dfile.encoding=utf-8
CMD ["java", "-jar", "jenkins.jar"]