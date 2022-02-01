pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'main', url: 'https://github.com/rubenrm-gft/demo_springboots.git'
                // Run Gradle Wrapper
                sh "./gradlew clean test assemble"
            }

            post {
                // If Gradle was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit 'build/test-results/test/*.xml'
                    archiveArtifacts 'build/libs/*.jar'
                    jacoco execPattern: 'build/jacoco/*.exec'
                }
            }
        }
    }
}