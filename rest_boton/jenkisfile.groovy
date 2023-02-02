pipeline { 
    agent any 
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
          when {
                branch 'master'
            }
            steps {
                sh 'java -version'
                sh 'npm run build:dev'
            }
        }
        stage('Test-sonar'){
        when {
                branch 'master'
            }
            steps {
                sh 'make check'
                junit 'reports/**/*.xml'
            }
       }
        stage('Test-veracode'){
        when {
                branch 'master'
            }
            steps {
                sh 'make check'
                junit 'reports/**/*.xml'
            }
     }
         stage('Test-publicar'){
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
    
