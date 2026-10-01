pipeline {

    agent any

    environment {
        IMAGE_NAME = "psbd/student-management"
        IMAGE_VERSION = "1.0.11"
        KUBECONFIG = "C:\\Users\\admin\\.kube\\config"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/shruti-1234-patel/Devops-student-management-system-.git'
            }
        }

        stage('Automated Unit Testing') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Maven Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Artifact Versioning') {
            steps {
                bat '''
                copy target\\student-management-1.0.0.jar target\\student-management-1.0.11.jar
                copy target\\student-management-1.0.0.jar target\\app.jar
                '''
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
            }
        }

        stage('Trivy Image Scan') {
            steps {
                bat 'trivy image --timeout 10m %IMAGE_NAME%:%IMAGE_VERSION%'
            }
        }

        stage('Docker Push') {
            steps {
                bat 'docker push %IMAGE_NAME%:%IMAGE_VERSION%'
            }
        }

        stage('Kubernetes Check') {
            steps {
                bat 'kubectl config current-context'
                bat 'kubectl get nodes'
            }
        }

        stage('Kubernetes Deploy') {
            steps {
                bat 'kubectl apply -f deployment.yaml'
            }
        }

        stage('Deployment Status') {
            steps {
                bat 'kubectl get pods'
            }
        }
    }

    post {
        success {
            echo 'Student Management Application deployed successfully!'
        }

        failure {
            echo 'Pipeline failed. Please check the Jenkins console log.'
        }
    }
}
