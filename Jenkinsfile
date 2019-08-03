node('haimaxy-jnlp') {
    stage('Clone') {
        echo "1. Clone Stage"
        checkout scm
        script {
            build_tag = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
            if (env.BRANCH_NAME != 'master') {
                build_tag = "${env.BRANCH_NAME}-${build_tag}"
            }
        }
        sh "cd jk && mvn clean package && cp -rpf target/ROOT.war /home/jenkins/workspace/${JOB_NAME}/  && cd .."
    }
    stage('Test') {
        echo "2. Test Stage"
    }
    stage('Build') {
        echo "3.Build Docker Image Stage"
        sh "docker build -t gengxiaofang/tomcat8_jk_test:${build_tag} ."
    }
    
    stage('Push') {
        echo "4.Push Docker Image Stage"
        withCredentials([usernamePassword(credentialsId: 'dockerhub_auth', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
            sh "docker login -u ${dockerHubUser} -p ${dockerHubPassword}"
            sh "docker push gengxiaofang/tomcat8_jk_test:${build_tag}"
        }
    }
    
    stage('Deploy') {
        echo "5.Deploy Stage"
        if (env.BRANCH_NAME == 'master') {
            input "确认要部署上线吗？"
        }
        sh "sed -i 's/<BUILD_TAG>/${build_tag}/' k8s.yaml"
        sh "sed -i 's/<BRANCH_NAME>/${env.BRANCH_NAME}/' k8s.yaml"
        sh "sleep 10"
        sh "kubectl apply -f k8s.yaml"
    }
}
