pipeline {

    agent any

    tools {
        maven 'maven-3.9.16'
    }

    environment {
        IMAGE_NAME = "psbd/student-management"
        IMAGE_VERSION = "1.0.${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Maven Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Unit Testing') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Artifact Versioning') {
    steps {
        bat 'copy target\\student-management-1.0.0.jar target\\student-management-%IMAGE_VERSION%.jar'
        bat 'copy target\\student-management-1.0.0.jar target\\app.jar'
    }
}

        stage('Security Scan') {
            steps {
                bat 'trivy fs .'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t %IMAGE_NAME%:%IMAGE_VERSION% .'
                bat 'docker tag %IMAGE_NAME%:%IMAGE_VERSION% %IMAGE_NAME%:latest'
            }
        }

        stage('Docker Image Scan') {
            steps {
                bat 'trivy image %IMAGE_NAME%:%IMAGE_VERSION%'
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {

                    bat 'docker login -u %DOCKER_USER% -p %DOCKER_PASSWORD%'

                    bat 'docker push %IMAGE_NAME%:%IMAGE_VERSION%'

                    bat 'docker push %IMAGE_NAME%:latest'
                }
            }
        }

        stage('Kubernetes Deploy') {
            steps {

                bat 'kubectl apply -f k8s/configmap.yaml'
                bat 'kubectl apply -f k8s/secret.yaml'
                bat 'kubectl apply -f k8s/mysql-deployment.yaml'
                bat 'kubectl apply -f k8s/mysql-service.yaml'
                bat 'kubectl apply -f k8s/deployment.yaml'
                bat 'kubectl apply -f k8s/service.yaml'

            }
        }

        stage('Deployment Status') {
            steps {

                bat 'kubectl get pods'

                bat 'kubectl get services'

            }
        }
    }

    post {

        success {
            echo 'Student Management CI/CD Pipeline completed successfully.'
        }

        failure {
            echo 'Pipeline failed. Check the stage logs.'
        }
    }
}
