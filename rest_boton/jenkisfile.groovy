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
           when {
                branch 'master'
            }
            steps {
                sh 'make check'
                junit 'reports/**/*.xml'
            }
        }
        stage('Deploy') {
            steps {
                sh 'make publish'
            }
        }
    }
}
