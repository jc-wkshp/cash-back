def mvnCmd = "mvn -s configuration/nexus_settings.xml"
pipeline {
   
    agent {
        label 'maven'
    }

    stages {

        stage('Build App') {
            steps {
                sh "${mvnCmd} install -DskipTests=true"
            }
        }

        stage('Run Tests') {
            steps {
                parallel (
                    unitTest: {
                        sh "${mvnCmd} test"
                        step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
                    },
                    codeAnalysis: {
                        sh "${mvnCmd} sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -DskipTests=true"
                    }
                )
            }
        }

        stage('Archive App') {
            steps {
                sh "${mvnCmd} deploy -DskipTests=true"
            }
        }

        stage('Build Image') {
            steps {
                sh "cp ./target/cash-back-0.1.0.jar ./target/app.jar"
                script {
                    openshift.withCluster() {
                        openshift.withProject(env.DEV_PROJECT) {
                            openshift.selector("bc", "cash-back").startBuild("--from-file=./target/app.jar", "--wait=true")
                        }
                    }
                }
            }
        }

        stage('Deploy DEV') {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.withProject(env.DEV_PROJECT) {
                            openshift.selector("dc", "cash-back").rollout().latest();
                        }
                    }
                }
            }
        }

    } // End of Stages
} // End of pipeline