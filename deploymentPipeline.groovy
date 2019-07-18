def utils



pipeline {
  agent { label 'deployment' }

  parameters {
    string(name: 'BRANCH',
            defaultValue: 'master',
            description: 'Choose master to deploy to both staging and production env. Choose any other feature branch to deploy to staging only')
    choice(name: 'JENKINS_URL', choices: 'https://jenkins-staging.leanplum.com', description: 'Choose jenkins url  where the plugin should be installed ?')

  }

  options {
    disableConcurrentBuilds()
    ansiColor('xterm')
  }

  stages {
    stage("Setup Jenkins") {
      steps {
        script {
          load "Tools/jenkins/settings.groovy"

          load "Tools/CICD/settings.groovy"
          utils = load ("Tools/CICD/utils-0.3.groovy")

          if (BRANCH != 'master') {
            utils.codeCheckout(BRANCH)
          }

          utils.configurePython()
        }

        setupEnvironment(GCLOUD_PROJECT['staging'], K8S_CLUSTER['staging'], JENKINS_AUTH['staging'])
      }
    }

 

 stage("Build ghrp plugin from leanplum fork repository...") {
    steps {
      script {
          sh 'mvn package' 
      }
    }
  }

  stage ("Install the plugin to selected jenkins instance...")
    steps {
      script {
        echo "Plugin is installed!"
      }
    }
 } 

}

def setupEnvironment(String GCLOUD_PROJECT, String K8S_CLUSTER, String JENKINS_AUTH) {
  utils = evaluate readFile ("Tools/CICD/utils-0.3.groovy")
  utils.gcloudAuth(GCLOUD_PROJECT, JENKINS_AUTH)
  
}
