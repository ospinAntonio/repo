pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            
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
                junit 'reports/prueba.xml'
            }
        }
            stage('Test-veracode'){
        when {
                 branch 'master'
            }
            steps {
                sh 'make check'
                junit 'reports/prueba.xml'
            }
        }
        stage('Test-publicar'){
            steps {
                sh 'make check'
                junit 'reports/prueba.xml'
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
                branch 'master'
            }
            steps {
                sh 'echo publish'
                sh 'kubeclt apply -f ingress.yaml'
            }
        }
    }
}
