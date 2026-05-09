pipeline {

    agent any

    stages {

        stage('Clone') {
            steps {
                echo 'GitHub Connected'
            }
        }

        stage('Maven Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
