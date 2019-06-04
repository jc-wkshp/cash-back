def mvnCmd = "mvn -s configuration/nexus_settings.xml"
def version = ''
def devTag = ''

pipeline {
   
    agent {
        label 'maven'
    }

    stages {

        stage('Build App') {
            steps {
                script {
                    version = getVersionFromPom("pom.xml")
                    devTag  = "${version}-" + currentBuild.number
                }
                echo "Building version ${devTag}"
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
                sh "${mvnCmd} deploy -DskipTests=true -DaltDeploymentRepository=nexus::default::http://nexus:8081/repository/maven-releases/"
            }
        }

        stage('Build Image') {
            steps {
                sh "cp ./target/cash-back-0.1.0.jar ./target/app.jar"
                script {
                    openshift.withCluster() {
                        openshift.withProject("inno-apps-dev") {
                            openshift.selector("bc", "cash-back").startBuild("--from-file=./target/app.jar", "--wait=true")
                            openshift.tag("cash-back:latest", "tasks:${devTag}")
                        }
                    }
                }
            }
        }

        stage('Deploy DEV') {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.withProject("inno-apps-dev") {
                            openshift.set("image", "dc/cash-back", "tasks=docker-registry.default.svc:5000/inno-apps-dev/cash-back:${devTag}")
                            openshift.selector("dc", "cash-back").rollout().latest();
                        }
                    }
                }
            }
        }

    } // End of Stages
} // End of pipeline

def getVersionFromPom(pom) {
  def matcher = readFile(pom) =~ '<version>(.+)</version>'
  matcher ? matcher[0][1] : null
}