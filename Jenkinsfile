def mvnCmd = "mvn -s configuration/nexus_settings.xml"
pipeline {
   
    agent {
        label 'maven'
    }

    stages {

        stage('Build App') {
            steps {
                echo "Build App."
                sh "${mvnCmd} install -DskipTests=true"
            }
        }

        stage('Test') {
            steps {
                echo "Test App."
                sh "${mvnCmd} test"
                step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
            }
        }

        stage('Code Analysis') {
            steps {
                echo "Code Analysis App."
                script {
                sh "${mvnCmd} sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -DskipTests=true"
                }
            }
        }
    } // End of Stages
} // End of pipeline