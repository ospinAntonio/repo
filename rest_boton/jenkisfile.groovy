pipeline { 
    agent any 
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
          when {
                branch '*'
            }
            steps {
                sh 'java -version'
                sh 'npm run build:dev'
            }
        }
        stage('Test-sonar'){
        when {
                branch '*'
            }
            steps {
                sh 'make check'
                junit 'reports/**/*.xml'
            }
       }
        stage('Test-veracode'){
        when {
                branch '*'
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
         stage('public-toDockerhub') {
         when {
             branch 'dev'
             }
             steps {
         withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: jenkins_registry_cred_id, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
             sh "docker login -e ${docker_email} -u ${env.USERNAME} -p ${env.PASSWORD} ${docker_registry_url}"
             }
             }
         }
     stage('Deploy-qa') {
         when {
             branch 'master'
             }
             steps {
             sh 'echo publish'
             sh 'kubeclt apply -f ingress.yaml'
         }
     }
}
