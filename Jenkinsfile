pipeline {
    agent any

    stages {
        stage('Test'){
            steps {
                sh './gradlew clean test'
            }
            post{
                always{
                    junit 'build/test-results/test/*.xml'
                    jacoco execPattern 'build/jacoco/*.exec'
                }
            }
        }
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'main', url: 'https://github.com/rubenrm-gft/demo_springboots.git'
                // Run Gradle Wrapper
                sh "./gradlew assemble"
            }

            post {
                // If Gradle was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    archiveArtifacts 'build/libs/*.jar'
                }
            }
        }
        stage('Deploy'){
            echo 'Deploying...'
        }
    }
}