pipeline {
    agent any

    tools {
        maven 'maven3'
    }

    environment {
        SONARQUBE = 'sonar-qube'
        IMAGE_NAME = 'food-app'
        CONTAINER_NAME = 'food-app-container'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git 'https://github.com/your-repo/food-delivery.git'
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
                withSonarQubeEnv('sonar-qube') {
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

        stage('Build Package') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Stop Old Container') {
            steps {
                sh """
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true
                """
            }
        }

        stage('Docker Run') {
            steps {
                sh """
                    docker run -d -p 5000:5000 \
                    --name ${CONTAINER_NAME} \
                    ${IMAGE_NAME}
                """
            }
        }
    }

    post {
        success {
            echo 'CI/CD Pipeline SUCCESS ✅'
        }
        failure {
            echo 'CI/CD Pipeline FAILED ❌'
        }
    }
}
