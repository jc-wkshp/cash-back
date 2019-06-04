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

        stage('Unit Test And Code Analysis') {
            parallel {
                unitTest: {
                    echo "Test App"
                    sh "${mvnCmd} test"
                    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
                },
                codeAnalysis: {
                    echo "Running Code Analysis"
                    sh "${mvnCmd} sonar:sonar -Dsonar.host.url=http://sonarqube:9000 -DskipTests=true"
                }
            }
        }

        stage('Build Image') {
            steps {
                echo "Build Image."
                sh "cp target/cash-back-0.1.0.jar target/app.jar"
                script {
                    openshift.withCluster() {
                        openshift.withProject(env.DEV_PROJECT) {
                            openshift.selector("bc", "cash-back").startBuild("--from-file=target/app.jar", "--wait=true")
                        }
                    }
                }
            }
        }

        stage('Deploy DEV') {
            steps {
                echo "Deploy App To DEV."
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