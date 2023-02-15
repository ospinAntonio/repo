pipeline {
  agent any
  tools {
    gradle  'Gradle 7.5.1'
    //dockerTool 'Docker 20.10.21'
  }
  stages {
    stage('Build') {
      steps {
        script{
          sh """
          ls -la
          pwd
          gradle build """
        }
      }
    }
    stage('test') {
      steps {
        script{
          withSonarQubeEnv('sonar') {    
            sh """
            cd rest_boton
            gradle sonarqube \
              -Dsonar.projectKey=rest \
              -Dsonar.host.url=http://localhost:9000 \
              -Dsonar.login=67302ead7900b8b32b8704ff237a501ea65b62a0r """
          }
        }
      }
    }
    stage('Build Docker Image') {
      steps {
        sh"""
        cd rest_boton
        docker login -u $DOCKER_USER -p $DOCKER_PASSWORD
        docker build -t testjenkinsdocker:1.0 .
        docker push $DOCKER_USER/testjenkinsdocker:1.0
        """
      }
    }
    stage('Push Docker Image') {
      steps {
        echo 'succesfull'
      }
    }
  }
}
