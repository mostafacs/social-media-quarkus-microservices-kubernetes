# Social Media Platform

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
helm install hz-hazelcast --set service.type=LoadBalancer hazelcast/hazelcast

# Now the hazelcast cluster is ready get the external ip address to be used on the client connector
kubectl get service hz-hazelcast
```
[Service Per Pod and More Details](https://docs.hazelcast.com/tutorials/kubernetes-external-client)
