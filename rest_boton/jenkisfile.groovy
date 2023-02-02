pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    stages {
         stage('Build') {
            steps {
                sh 'make' 
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true 
            }
        }
        stage('Test') {
            steps {
                /* `make check` returns non-zero on test failures,
                * using `true` to allow the Pipeline to continue nonetheless
                */
                sh 'make check || true' 
                junit '**/target/*.xml' 
            }
        }
         stage('Test-veracode'){
        when {
                branch 'master'
            }
            steps {
                sh 'make check'
                junit 'reports/pruebas/prueba.xml'
            }
     }
         stage('Test-publicar'){
         steps {
             sh 'make check'
             junit 'reports/pruebas/prueba.xml'
             }
         }
         stage('public-toDockerhub') {
         when {
             branch 'master'
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
}
