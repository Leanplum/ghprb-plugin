CURRENT_CLUSTER="staging-1"
CLUSTER_FOLDER="/LEANPLUM/Kubernetes/deployment-manager/environments/${CURRENT_CLUSTER}"

DOCKER_REPO="us.gcr.io"
PROJECT="leanplum-staging"
IMAGE_BASE="${DOCKER_REPO}/${PROJECT}"
JENKINS_VERSION="2.150.2"
NS = "--namespace=jenkins-ppeshev-test"

K8S_CLUSTER = [
        staging: "cluster-staging-1",
        prod: "cluster-prod-1"
]

K8S_ZONE = [
        staging: "us-central1",
        prod: "us-central1"
]

IMAGE = [
        master: "${IMAGE_BASE}/jenkins:${JENKINS_VERSION}",
        qa: "${IMAGE_BASE}/jenkins-qa-slave:latest",
        deployment: "${IMAGE_BASE}/jenkins-deployment-slave:latest",
        agent: "${IMAGE_BASE}/jenkins-k8s-slave:latest"
]



