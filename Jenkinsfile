pipeline {
    agent any
    environment {
        registry= "youssefalmia/5twin5-g7-skistation"
        registryCredential = 'dockerhub'
        dockerImage = ''
        sonarQubeScannerHome = tool name: 'SonarQube', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
    }
    stages{
        stage('Checkout GIT'){
            steps{
                echo 'Pulling...';
                git branch: 'YoussefALMIA-5TWIN5-G7',
                url: 'https://github.com/FouratBenDhafer99/5TWIN5-OneZero-SkiStation';
            }
        }
        stage('MVN package') {
            steps {
                sh 'mvn -DskipTests clean package'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "mvn sonar:sonar"
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
        stage('Cleaning up') {
            steps {
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
    }
}
