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
            ./gradlew sonarqube \
             -Dsonar.projectKey=sonar \
             -Dsonar.host.url=http://localhost:9000 \
             -Dsonar.login=7ac184fe787fd1fb40a080a6fbd80e36a07bb3e5 """
          }
        }
      }
    }
    //stage('Build Docker Image') {
      //steps {
        //sh"""
        //cd micro_imagen
        //docker login -u $DOCKER_USER -p $DOCKER_PASSWORD
        //docker build -t testjenkinsdocker:1.0 .
        //docker push $DOCKER_USER/testjenkinsdocker:1.0
        //"""
      //}
    //}
    stage('Push Docker Image') {
      steps {
        echo 'succesfull'
      }
    }
  }
}
