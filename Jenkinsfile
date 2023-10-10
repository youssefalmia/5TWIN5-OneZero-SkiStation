pipeline {
    agent any
    environment{
        registry= ${docker_repository}
        registryCredential = 'dockerhub'
        dockerImage = ''
    }
    stages{
        stage('Checkout GIT'){
            steps{
                echo 'Pulling...';
                git branch: 'main',
                url: 'https://github.com/RayenBourguiba/Ski-Station-DevOps.git';
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