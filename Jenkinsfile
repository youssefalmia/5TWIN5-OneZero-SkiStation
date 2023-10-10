pipeline {
    agent any
    environment{
        registry= "$docker_repository"
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

        stage('Building our image') {
            steps {
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }

        stage('Deploy our image') {
            steps {
                script {
                    docker.withRegistry( '', registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }
    }
}