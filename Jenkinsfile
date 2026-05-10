pipeline {

    agent any

    tools {
        jdk 'jdk21'
    }

    environment {
        JAVA_HOME = "/usr/lib/jvm/java-21-openjdk-amd64"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"

        SONAR_TOKEN = credentials('sonar')
        DOCKER_PASS = credentials('docker-pass')
    }

    stages {

        stage('Check Java Version') {
            steps {
                sh 'java -version'
                sh 'javac -version'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Scan') {

            steps {

                withSonarQubeEnv('sonar-server') {

                    sh """
                    mvn sonar:sonar \
                    -Dsonar.projectKey=food-delivery \
                    -Dsonar.host.url=http://13.49.80.135:9000 \
                    -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t foodapp:v1 .'
            }
        }

        stage('Docker Push') {
            steps {

                sh '''
                echo $DOCKER_PASS | docker login -u zeeshancloud15 --password-stdin
                '''

                sh 'docker tag foodapp:v1 zeeshancloud15/foodapp:v1'

                sh 'docker push zeeshancloud15/foodapp:v1'
            }
        }
    }
}
