pipeline {
    agent any

    stages {
        // Stage to get the project
        stage('Get') {
            steps {
                git branch: 'master', url: 'https://github.com/ItsLukio/lukespetitions.git'
            }
        }

        // Stage to debug 
        stage('Debug') {
            steps {
                sh 'pwd'  // Display current directory
                sh 'ls -la'  // List files and directories in current workspace
            }
        }

        // Stage to set execution permissions for the Maven wrapper
        stage('Set Permissions') {
            steps {
                sh 'chmod +x mvnw'
            }
        }

        // Stage to clean and compile
        stage('Build') {
            steps {
                sh './mvnw clean'  // Clean the project
                sh './mvnw dependency:copy-dependencies'  // Copy dependencies
                sh './mvnw compile'  // Compile the project
            }
        }

        // Stage to run tests
        stage('Test') {
            steps {
                sh './mvnw test'  // Run tests
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'  // Archive results
                }
            }
        }

        // package the project as a WAR file
        stage('Package') {
            steps {
                sh './mvnw package'  // Package the project using Maven wrapper
            }
        }

        // Deploy the app using Docker
        stage('Deploy') {
            steps {
                // Ensure WAR is built
                sh './mvnw clean package'
                
                // Verify WAR file exists
                sh 'ls -l target/*.war'
                
                // Build Docker image
                sh 'docker build -t myapp .'
                
                // Remove existing container
                sh 'docker rm -f "myappcontainer" || true'
                
                // Run new container
                sh 'docker run --name "myappcontainer" -p 9090:8080 --detach myapp:latest'
            }
        }
    }

    // Post-build actions
    post {
        success {
            // Archive the WAR file after a successful build
            archiveArtifacts artifacts: 'target/*.war', allowEmptyArchive: true
        }
    }
}
