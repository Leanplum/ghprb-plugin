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
    
   /*stage("Build ghprb plugin from leanplum fork repository...") {
      steps {
        script {
            sh 'mvn package' 
        }
      }
    }*/

    stage ("Install the plugin to Jenkins...") {
      steps {
        script {
          dir("target") {
            echo "Installing plugin ...." 
            script {
            sh """curl -s -k -i 'https://petar.peshev@leanplum.com:11eacc27f54406da0b933adc6c20cee7e4@${env.JENKINS_URL}/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)'"""
            /*withCredentials([usernameColonPassword(credentialsId: 'ppeshevToken', variable: 'USERPASS')]) { 
              def CRUMB = sh (script: """curl -s 'https://petar.peshev@leanplum.com:11eacc27f54406da0b933adc6c20cee7e4@${env.JENKINS_URL}/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)'""",returnStdout: true)
              echo "$CRUMB"*/
      
//            sh """curl -X POST -H "$CRUMB" --user $USERPASS -i -F file=@ghprb.hpi http://${env.JENKINS_URL}/pluginManager/uploadPlugin"""
          }
        }
       }
      }
    }
  }
} 



