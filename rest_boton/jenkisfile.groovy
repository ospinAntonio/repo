pipeline { 
    agent any 
    options {
        skipStagesAfterUnstable()
    }
    stages {
    
    stage("build") {
        when {
            expression {
                BRANCH_NAME = 'alerta_dev_infra'
            }
        }
        steps {
            echo 'buidling the webhook'
        }
    }

    stage("test") {
        when {
            expression {
                env.BRANCH_NAME = 'alerta*'
            }
        }
          steps {
              echo 'testing the webhook'
        }
    }

    stage("deploy") {
        when {
            expression {
                env.BRANCH_NAME = 'alerta_dev_infra'
            }
        }
          steps {
              echo 'deploying the webhook'
        }
    }

  }
