pipeline {
    agent any

    stages {
        stage('Get Project') {
            steps {
                git branch: 'master', url: 'https://github.com/ItsLukio/lukespetitions.git'
            }
        }

        stage('Set Permissions') {
            steps {
                sh 'chmod +x ./demo/mvnw'
            }
        }

        stage('Build') {
            steps {
                sh './demo/mvnw -f ./demo/pom.xml clean package'
            }
        }

        stage('Deploy') {
            steps {
                // Use the correct path for the Dockerfile and build context
                sh 'docker build -t lukespetitions -f Dockerfile ./demo'
                
                // Remove any existing container named lukespetitions
                sh 'docker rm -f "lukespetitions" || true'
                
                // Run the new container with the name lukespetitions
                sh 'docker run --name "lukespetitions" -p 9090:8080 --detach lukespetitions:latest'
            }
        }
    }

    post {
        success {
            // Archive the WAR file for reference
            archiveArtifacts artifacts: 'demo/target/*.war', allowEmptyArchive: true
        }
    }
}
