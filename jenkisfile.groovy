pipeline {
    agent any
    tools {
        gradle "gradle-7.5.1"
    }
    stages {
        stage('Build') {
                steps {
                    sh 'gradle task'
                    sh 'gradle init'
                    sh 'gradle wrapper'
                    sh './gradle build'
                }
            }
        }
            stage('Test'){
                steps {
                   echo 'test succesfull'
                }
        }
             stage('Build docker image'){
                steps {
                   echo 'succesfull'
                }
         }
             stage('public-toDockerhub') {
                 steps {
             withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: jenkins_registry_cred_id, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                 sh "docker login -e ${docker_email} -u ${env.USERNAME} -p ${env.PASSWORD} ${docker_registry_url}"
                 sh 'docker push semillero:latest'
                 }
                 }
             }
         stage('push docker imag') {
                 steps {
                    echo 'succesfull'
             }
         }
}
