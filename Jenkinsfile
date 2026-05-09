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
                    -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }
    }
}
