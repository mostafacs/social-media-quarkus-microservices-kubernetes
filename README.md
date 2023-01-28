# Social Media Platform


## Run App Locally
Start Docker Desktop:
```shell
docker compose up
```

#### using minikube docker environment

```shell
# Start minikube cluster
minikube start

# User minikube docker environment
eval $(minikube docker-env)

# Build project jars and docker images
mvn clean install -Dquarkus.container-image.build=true

```

#### or using local registry
```shell
docker run -d -p 5000:5000 --name registry registry:2.7
minikube start --cpus 4 --memory 4096 --insecure-registry localhost:5000
cd social-media-quarkus-kubernetes/services/feed
docker build -f src/main/docker/Dockerfile.jvm -t social/feed:1.0 .
docker tag  social/feed:1.0 localhost:5000/social/feed:1.0
docker push localhost:5000/social/feed
kubectl apply -f target/kubernetes/kubernetes.yml
```

## Feed Service
```shell
cd social-media-quarkus-kubernetes/services/feed
kubectl apply -f target/kubernetes/kubernetes.yml
minikube service feed
```
### Login curl

```shell
   curl --insecure -X POST http://localhost:8080/realms/social/protocol/openid-connect/token \
    --user backend-service:ZEGieh1cMBxxtIbEHwWJ3U7PODSMUoYI \
    -H 'content-type: application/x-www-form-urlencoded' \
    -d 'username=mostafa3@gmail.com&password=123&grant_type=password'
```
### Start Hazelcast cluster

docker run --rm -p 8180:8080 hazelcast/management-center:5.1.4

```shell
# install hazelcast images in minikube docker
minikube ssh docker pull hazelcast/hazelcast:5.2.0
minikube ssh docker pull hazelcast/management-center:5.2.0

minikube image load feed-service

# start  hazelcast cluster
helm repo add hazelcast https://hazelcast-charts.s3.amazonaws.com/
helm repo update
helm install hz-hazelcast -f hazelcast-helm.yaml hazelcast/hazelcast
helm install hz-hazelcast --set service.type=LoadBalancer hazelcast/hazelcast

# Now the hazelcast cluster is ready get the external ip address to be used on the client connector
kubectl get service hz-hazelcast
```
[Service Per Pod and More Details](https://docs.hazelcast.com/tutorials/kubernetes-external-client)

https://github.com/kubernetes-sigs/kustomize


## Clean up
```shell
kubectl delete -f target/kubernetes/kubernetes.yml

```