pipeline { 
    agent any 
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') { 
            when {
                branch ''
            }
            steps {
                sh 'java -version'
            }
        }
        stage('Test'){
            steps {
                sh 'make check'
            }
        }
        stage('Deploy') {
            steps {
                sh 'make publish'
            }
        }
    }
}
