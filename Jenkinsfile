pipeline {
    agent any
    environment {
        registry= "youssefalmia/5twin5-g7-skistation"
        registryCredential = 'DockerHub'

        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "http://192.168.100.2:8081"
        NEXUS_REPOSITORY = "nexus-repo-skistation"
        NEXUS_CREDENTIAL_ID = "nexus-user-credential"

        dockerImage = ''
    }
    stages{
        stage('Checkout GIT'){
            steps{
                echo 'Pulling...';
                git branch: 'master',
                url: 'https://github.com/youssefalmia/5TWIN5-OneZero-SkiStation';
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
        stage('Code Coverage and SonarQube Analysis') {
            steps {
                sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install'
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar'
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
    post {
        success {
            emailext(
                    subject: "Pipeline Success: 5TWIN5-OneZero-SkiStation",
                    body: "Congratulations! The pipeline executed successfully.",
                    to: "almia.youssef2@gmail.com"
            )
        }
        failure {
            emailext(
                    subject: "Pipeline Failure: 5TWIN5-OneZero-SkiStation",
                    body: "Oops! Something went wrong during the pipeline execution. Please check the Jenkins logs for more details.",
                    to: "almia.youssef2@gmail.com"
            )
        }
    }
}

def isSnapshot() {
    return sh(script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true).trim().endsWith('-SNAPSHOT')
}
