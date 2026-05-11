pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-21-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:/usr/local/bin:/usr/bin:/bin"
        SCANNER_HOME = '/opt/sonar-scanner'
    }

    stages {

        stage('Check Java') {
            steps {
                sh '''
                echo $JAVA_HOME
                java -version
                javac -version
                mvn -version
                '''
            }
        }

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

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
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
                    $SCANNER_HOME/bin/sonar-scanner \
                    -Dsonar.projectKey=food-delivery \
                    -Dsonar.projectName=food-delivery \
                    -Dsonar.sources=src \
                    -Dsonar.java.binaries=target/classes
                    '''
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t food-delivery:latest .'
            }
        }

        stage('Docker Run') {
            steps {
                sh '''
                docker rm -f food-delivery || true
                docker run -d --name food-delivery -p 5000:5000 food-delivery:latest
                '''
            }
        }
    }
}
