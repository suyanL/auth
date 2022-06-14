export JENKINS_HOME="/root/.jenkins/workspace/springboot-jenkins"
export JENKINS_VERSION="v0.1"

echo "JENKINS_HOME:${JENKINS_HOME},JENKINS_VERSION:${JENKINS_VERSION}"

echo "开始打包"
mvn clean
mvn package

echo "开始制作镜像"
docker build -t 你的私有Harbor地址/yunlingfly/jenkins:$JENKINS_VERSION $JENKINS_HOME

echo "开始推镜像"
docker push 你的私有Harbor地址/yunlingfly/jenkins:$JENKINS_VERSION

echo "开始运行镜像"
kubectl apply -f $JENKINS_HOME/build/jenkins-svc.yaml
# 使用envsubst传递export的参数
envsubst < $JENKINS_HOME/build/jenkins-deployment.yaml | kubectl apply -f -