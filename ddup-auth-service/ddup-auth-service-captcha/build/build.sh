export JENKINS_HOME="/var/jenkins_home/workspace/ddup-auth/ddup-auth-service-captcha"
export JENKINS_VERSION="v0.1"

echo "JENKINS_HOME:${JENKINS_HOME},JENKINS_VERSION:${JENKINS_VERSION}"

echo "开始打包"
mvn clean
mvn package

echo "开始制作镜像"
docker build -t 10.211.55.19:30002/ddup/ddup-auth-service-captcha:$JENKINS_VERSION $JENKINS_HOME

echo "开始推镜像"
docker push 10.211.55.19:30002/ddup/ddup-auth-service-captcha:$JENKINS_VERSION

echo "开始运行镜像"
kubectl apply -f $JENKINS_HOME/build/svc.yaml
# 使用envsubst传递export的参数
envsubst < $JENKINS_HOME/build/deployment.yaml | kubectl apply -f -