{
    node('maven') {
        def mvnCmd = "mvn -s configuration/nexus_settings.xml"
        stages {

            stage('Build App') {
                steps {
                    git branch: 'eap-7', url: 'http://gogs:3000/gogs/openshift-tasks.git'
                    sh "${mvnCmd} install -DskipTests=true"
                }
            }

            stage('Test') {
                steps {
                    sh "${mvnCmd} test"
                    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
                }
            }

            stage('Code Analysis') {
                steps {
                    script {
                    sh "${mvnCmd} sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -DskipTests=true"
                    }
                }
            }

            stage('Archive App') {
                steps {
                    sh "${mvnCmd} deploy -DskipTests=true -P nexus3"
                }
            }

        } // End of Stages
    } // End of node
} // End