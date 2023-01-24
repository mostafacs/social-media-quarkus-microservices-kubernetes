```shell

kubectl exec -it <pod_name> bash


minikube tunnel
kubectl get pods
kubectl describe pods
kubectl get service hz-hazelcast
kubectl get service hz-hazelcast


kubectl exec --stdin --tty hz-hazelcast-0 -- /bin/bash
host.minikube.internal
```




-- Remove hazelcast
```shell
helm uninstall hz-hazelcast
```