pipeline {

    agent any

    environment {

        SONARQUBE = 'sonar-qube'

        IMAGE_NAME = 'food-app'

        CONTAINER_NAME = 'food-app-container'

        DOCKER_HUB_USER = 'zeeshancloud15'

        DOCKER_IMAGE = 'zeeshancloud15/food-app:latest'

        // Kubernetes Server
        K8S_SERVER = 'ubuntu@13.49.183.246'

        // S3 Bucket
        S3_BUCKET = 'zeeshanagency'
    }

    stages {

        stage('Checkout Code') {

            steps {

                git branch: 'main',
                url: 'https://github.com/Zeeshancloud15/food-delivery.git'
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

                sh '''
                    docker build -t ${DOCKER_IMAGE} .
                '''
            }
        }

        stage('Docker Login & Push') {

            steps {

                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-cred',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {

                    sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin

                        docker push ${DOCKER_IMAGE}
                    '''
                }
            }
        }

        stage('Upload Backup to S3') {

            steps {

                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-id',
                    accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                    secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                ]]) {

                    sh '''
                        aws s3 cp target/*.jar s3://${S3_BUCKET}/jar/

                        aws s3 cp deployment.yaml s3://${S3_BUCKET}/k8s/

                        aws s3 cp service.yaml s3://${S3_BUCKET}/k8s/

                        aws s3 cp hpa.yaml s3://${S3_BUCKET}/k8s/

                        aws s3 cp ingress.yaml s3://${S3_BUCKET}/k8s/
                    '''
                }
            }
        }

        stage('Docker Run Local Test') {

            steps {

                sh '''
                    docker stop ${CONTAINER_NAME} || true

                    docker rm ${CONTAINER_NAME} || true

                    docker run -d \
                    -p 8081:8080 \
                    --name ${CONTAINER_NAME} \
                    ${DOCKER_IMAGE}
                '''
            }
        }

        stage('Deploy to Kubernetes') {

            steps {

                sshagent(['k8s-ssh1']) {

                    sh """
                        ssh -o StrictHostKeyChecking=no ${K8S_SERVER} '

                        kubectl apply -f https://raw.githubusercontent.com/Zeeshancloud15/food-delivery/main/deployment.yaml &&

                        kubectl apply -f https://raw.githubusercontent.com/Zeeshancloud15/food-delivery/main/service.yaml &&

                        kubectl apply -f https://raw.githubusercontent.com/Zeeshancloud15/food-delivery/main/hpa.yaml &&

                        kubectl apply -f https://raw.githubusercontent.com/Zeeshancloud15/food-delivery/main/metalb.yaml &&



                        kubectl rollout restart deployment food-app
                        '
                    """
                }
            }
        }

        stage('Monitoring Verification') {

            steps {

                sshagent(['k8s-ssh1']) {

                    sh """
                        ssh -o StrictHostKeyChecking=no ${K8S_SERVER} '

                        echo "===== NODES ====="

                        kubectl get nodes

                        echo "===== PODS ====="

                        kubectl get pods -o wide

                        echo "===== DEPLOYMENTS ====="

                        kubectl get deployment

                        echo "===== SERVICES ====="

                        kubectl get svc

                        echo "===== HPA ====="

                        kubectl get hpa

                        echo "===== INGRESS ====="

                        kubectl get ingress
                        '
                    """
                }
            }
        }

        stage('Verify Kubernetes') {

            steps {

                sshagent(['k8s-ssh1']) {

                    sh """
                        ssh -o StrictHostKeyChecking=no ${K8S_SERVER} '

                        echo "===== PODS ====="

                        kubectl get pods

                        echo "===== SERVICES ====="

                        kubectl get svc

                        echo "===== HPA ====="

                        kubectl get hpa

                        echo "===== INGRESS ====="

                        kubectl get ingress
                        '
                    """
                }
            }
        }
    }

    post {

        success {

            echo 'SUCCESS 🚀 Full CI/CD + Docker + S3 + Kubernetes + Ingress + Monitoring Deployment Done'
        }

        failure {

            echo 'FAILED ❌ Check Jenkins Logs'
        }

        always {

            cleanWs()
        }
    }
}
