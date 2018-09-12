#HAZELCAST SPIKE

###Objectives
* Configure local hazelcast cluster and ensure nodes are registered/deregistered
* Configure kubernetes hazelcast cluster and ensure nodes are registered/deregistered
* Raise an event & ensure all nodes within cluster receives event

###How To:

#####Run local cluster
By default app is configured to run default cluster

Start 1st instance of spring-boot-app: 

```java -jar target/*.jar```

Any subsequent instances:

```java -Dserver.port=xxx -jar target/*.jar```

#####Run kubernetes cluster
---TODO

#####Test event propagation
A simple endpoint is exposed that will raise event. If successful it will propagate accross all nodes within the cluster and print a log message eg:

```Message received = AppEvent(uuid=f05693a7-594e-418e-85e5-a0150c2363d5)```

To call endpoint:

```
curl -X GET http://localhost:8081/test
```

