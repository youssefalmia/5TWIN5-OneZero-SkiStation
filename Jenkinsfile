pipeline {
    agent any
    environment {
        registry= "fouratbendhafer11/5twin5-onezero-skistation"
        registryCredential = 'dockerhub'
        dockerImage = ''

        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "http://192.168.1.15:8081"
        NEXUS_REPOSITORY = "nexus-repo-skistation"
        NEXUS_CREDENTIAL_ID = "nexus-user-credential"
    }
    stages{
        stage('Checkout GIT'){
            steps{
                echo 'Pulling...';
                git branch: 'FouratBenDhafer-5TWIN5-G7',
                url: 'https://github.com/FouratBenDhafer99/5TWIN5-OneZero-SkiStation.git';
            }
        }
        stage('MVN package') {
            steps {
                sh 'mvn -DskipTests clean package'
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage('MVN SonarQube') {
            steps {
                script {
                    sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install'
                    sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=adminn'
                }
            }
        }
        stage('Nexus Deployment') {
            steps {
                script {
                    def repositoryUrl = ''
                    if (isSnapshot()) {
                        repositoryUrl = "${NEXUS_URL}/repository/maven-snapshots/"
                    } else {
                        repositoryUrl = "${NEXUS_URL}/repository/maven-releases/"
                    }
                    try {
                        sh "mvn deploy -DskipTests -DaltDeploymentRepository=deploymentRepo::default::${repositoryUrl}"
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error("Maven deploy failed: ${e.message}")
                    }
                }
            }
        }
        stage('Building Docker image') {
            steps {
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Deploy docker image') {
            steps {
                script {
                    docker.withRegistry( '', registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Docker compose') {
            steps {
                sh "docker-compose up -d"
            }
        }
        stage('Cleaning up') {
            steps {
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
    }
}
def isSnapshot() {
    return sh(script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true).trim().endsWith('-SNAPSHOT')
}