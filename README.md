# Social Media Platform

### Start Hazelcast cluster

docker run --rm -p 8180:8080 hazelcast/management-center:5.1.4

```shell

minikube ssh docker pull hazelcast/hazelcast:5.2.0

minikube image load feed-service
## start  hazelcast cluser
helm repo add hazelcast https://hazelcast-charts.s3.amazonaws.com/
helm repo update
helm install hz-hazelcast hazelcast/hazelcast
```