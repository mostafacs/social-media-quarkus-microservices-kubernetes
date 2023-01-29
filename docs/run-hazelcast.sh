helm repo add hazelcast https://hazelcast-charts.s3.amazonaws.com/
helm repo update
helm install hz-hazelcast -f hazelcast-helm.yaml hazelcast/hazelcast