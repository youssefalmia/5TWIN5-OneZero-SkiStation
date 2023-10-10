pipeline {
    agent any
    environment {
        registry= "fouratbendhafer11/5twin5-onezero-skistation"
        registryCredential = 'dockerhub'
        dockerImage = ''
    }
    stages{
        stage('Checkout GIT'){
            steps{
                echo 'Pulling...';
                git branch: 'master',
                url: 'https://github.com/FouratBenDhafer99/5TWIN5-OneZero-SkiStation.git';
            }
        }
        stage('MVN package') {
            steps {
                sh 'mvn -DskipTests clean package'
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