pipeline {
    agent any

    tools {
        maven "maven"
    }

    environment {
        APP_NAME    = "spring-docker-jenkins"
        RELEASE_NO  = "1.0.0"
        DOCKER_USER = "kumarpremji"

        IMAGE_NAME = "${DOCKER_USER}/${APP_NAME}"
        IMAGE_TAG  = "${RELEASE_NO}-${BUILD_NUMBER}"
    }

    stages {

        stage('SCM checkout') {
            steps {
                checkout scmGit(
                    branches: [[name: 'main']],
                    extensions: [],
                    userRemoteConfigs: [[url: 'https://github.com/kumarprem66/spring-docker-jenkins.git']]
                )
            }
        }

        stage('Build Process') {
            steps {
                bat 'mvn clean install'
            }
        }
        stage('Build Image'){
            steps{
                script{
                    bat 'docker build -t %IMAGE_NAME%:%IMAGE_TAG% .'
                }
            }
        }

        stage('Deploy image to Hub'){
            steps{
                withCredentials([string(credentialsId: 'sdj', variable: 'dp')]) {
                    bat '''
                    echo %dp% | docker login -u %DOCKER_USER% --password-stdin
                    '''
                    bat 'docker push %IMAGE_NAME%:%IMAGE_TAG%'
                }
            }
        }

        stage('Deploy to kubernetes'){
            steps{
                bat 'kubectl apply -f k8s-app.yaml'
            }
        }

        stage('Verify Deployment'){
            steps{
                bat 'kubectl get pods'
            }
        }

    }

    post {
    always {
        emailext(
            attachLog: true,
            mimeType: 'text/html',
            subject: "Pipeline Status : ${currentBuild.currentResult}",
            to: 'premstart10@gmail.com',
            body: """
            <html>
            <body>
                <p>Build Status : ${currentBuild.currentResult}</p>
                <p>Build Number : ${BUILD_NUMBER}</p>
                <p>
                    Check the
                    <a href="${BUILD_URL}console">
                        console output
                    </a>
                </p>
            </body>
            </html>
            """
        )
    }
}
}