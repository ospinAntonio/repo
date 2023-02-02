pipeline { 
    agent any 
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
          
            steps {
                sh 'java -version'
                sh 'run build:dev'
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
}
    
