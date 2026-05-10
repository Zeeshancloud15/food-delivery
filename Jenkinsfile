pipeline {

    agent any

    environment {
        SONAR_TOKEN = credentials('sonar')
    }

    stages {

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
        sh 'docker login -u zeeshancloud15  -p Uddin@1234#'
        sh 'docker tag foodapp:v1 zeeshancloud15/foodapp:v1'
        sh 'docker push zeeshancloud15/foodapp:v1'
    }
}
    }
}
