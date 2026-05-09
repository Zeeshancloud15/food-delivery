pipeline {

    agent any

    environment {
        SONAR_TOKEN = credentials('sonar-token')
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
                    -Dsonar.login=sqa_7adf9bdeb926e9ce61827c6ae28824100d761c55
                    """
                }
            }
        }
    }
}
