pipeline {
    agent any

    environment {
        SONARQUBE = 'sonarqube'
        IMAGE_NAME = 'food-app'
        CONTAINER_NAME = 'food-app-container'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git 'https://github.com/Zeeshancloud15/food-delivery.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=food-delivery \
                        -Dsonar.projectName=food-delivery \
                        -Dsonar.sources=src \
                        -Dsonar.java.binaries=target/classes
                    '''
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Docker Run') {
            steps {
                sh """
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true
                    docker run -d -p 5000:5000 --name ${CONTAINER_NAME} ${IMAGE_NAME}
                """
            }
        }
    }

    post {
        success {
            echo 'SUCCESS ✅'
        }
        failure {
            echo 'FAILED ❌'
        }
    }
}
