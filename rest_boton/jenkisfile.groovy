pipeline { 
    agent any 
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
<<<<<<< HEAD
          when {
                branch '*'
            }
=======
          
>>>>>>> 5eb1ace953fb9e113fc43b385566ec02abf89260
            steps {
                sh 'java -version'
                sh 'java -jar ./build/libs/rest-1.0.jar'
            }
        }
        stage('Test-sonar'){
        when {
<<<<<<< HEAD
                branch '*'
=======
                branch 'master'
>>>>>>> 5eb1ace953fb9e113fc43b385566ec02abf89260
            }
            steps {
                sh 'make check'
                junit 'reports/**/*.xml'
            }
       }
<<<<<<< HEAD
        stage('Test-veracode'){
        when {
                branch '*'
            }
            steps {
                sh 'make check'
                junit 'reports/**/*.xml'
            }
     }
=======
       
>>>>>>> 5eb1ace953fb9e113fc43b385566ec02abf89260
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
<<<<<<< HEAD
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
             branch 'qa'
             }
             steps {
             sh 'echo publish'
             sh 'kubeclt apply -f ingress.yaml'
         }
     }
}
=======
}
    
>>>>>>> 5eb1ace953fb9e113fc43b385566ec02abf89260
