pipeline {
    agent any

    environment {
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
        MAVEN_HOME = "/opt/maven"
        SCANNER_HOME = "/opt/sonar-scanner"

        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${PATH}"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/Zeeshancloud15/food-delivery.git'
            }
        }

        stage('Check Java Version') {
            steps {
                sh 'java -version'
            }
        }

        stage('Check Maven Version') {
            steps {
                sh 'mvn -version'
            }
        }

        stage('Build Application') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Run Test Cases') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar-qube') {

                    sh '''
                    $SCANNER_HOME/bin/sonar-scanner \
                    -Dsonar.projectKey=food-delivery \
                    -Dsonar.sources=src \
                    -Dsonar.java.binaries=target
                    '''
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t food-delivery-app .'
            }
        }

    }

    post {

        success {
            echo 'Pipeline Executed Successfully'
        }

        failure {
            echo 'Pipeline Failed'
        }
    }
}
