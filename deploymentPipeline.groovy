def utils

pipeline {
  
  agent { label 'deployment' }

  environment {
        MAVEN_HOME = tool('maven-3.6.1')
        JENKINS_URL = "jenkins-staging.leanplum.com"

    }

  options {
    disableConcurrentBuilds()
    ansiColor('xterm')
  }

  stages {
    
  stage("Build ghprb plugin from leanplum fork repository...") {
      steps {
        script {
            sh 'mvn package' 
        }
      }
    }

    stage ("Install the plugin to Jenkins...") {
      steps {
        script {
          dir("target") {
            echo "Installing plugin ...."
            withCredentials([usernameColonPassword(credentialsId: 'lpadmin', variable: 'USERPASS')]) { 
            def CRUMB = sh (script: """curl -s 'https://$USERPASS@${env.JENKINS_URL}/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)'""",returnStdout: true)

           // sh """curl -X POST -H "$CRUMB" --user $USERPASS -i -F file=@ghprb.hpi http://${env.JENKINS_URL}/pluginManager/uploadPlugin"""
          }
        }
       }
      }
    }
  }
} 



