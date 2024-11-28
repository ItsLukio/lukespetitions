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
                // Use the correct path for the Dockerfile
                sh 'docker build -t myapp -f Dockerfile ./lukespetitions'
                
                // Remove any existing container
                sh 'docker rm -f "myappcontainer" || true'
                
                // Run the new container
                sh 'docker run --name "myappcontainer" -p 9090:8080 --detach myapp:latest'
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
