pipeline {
    agent any

    environment {
        SONARQUBE = 'sonar-qube'
        IMAGE_NAME = 'food-app'
        CONTAINER_NAME = 'food-app-container'
        DOCKER_HUB_USER = 'zeeshancloud15'
        DOCKER_IMAGE = 'zeeshancloud15/food-app:latest'

        // Kubernetes Master
        K8S_SERVER = 'ubuntu@16.170.213.84'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Zeeshancloud15/food-delivery.git'
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

        stage('Docker Login & Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-cred',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin

                        docker tag food-app ${DOCKER_IMAGE}
                        docker push ${DOCKER_IMAGE}
                    '''
                }
            }
        }

        stage('Docker Run (Local Test)') {
            steps {
                sh '''
                    docker stop ${CONTAINER_NAME} || true
                    docker rm ${CONTAINER_NAME} || true

                    docker run -d \
                    -p 8081:8080 \
                    --name ${CONTAINER_NAME} \
                    food-app
                '''
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sshagent(['k8s-ssh1']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no  ${K8S_SERVER}
                        kubectl apply -f https://raw.githubusercontent.com/Zeeshancloud15/food-delivery/main/deployment.yaml &&
                        kubectl apply -f https://raw.githubusercontent.com/Zeeshancloud15/food-delivery/main/service.yaml &&
                        kubectl rollout restart deployment food-app
                        "
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'SUCCESS 🚀 Full CI/CD + Kubernetes Deployment Done'
        }

        failure {
            echo 'FAILED ❌ Check logs'
        }
    }
}
