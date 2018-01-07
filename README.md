### Cloud instructions
Public ip : http://169.51.16.54

* bx login -a https://api.eu-de.bluemix.net  
* bx cs cluster-config rso-fririders
* SET KUBECONFIG=...
* kubectl get nodes
* kubectl proxy 
* kubectl autoscale deployment accommodations-deployment --min=1 --max=10 --cpu-percent=80
* kubectl set image deployments/accommodations-deployment accommodations=janerz6/accomodations:0.2

## Kubectl controls:
* All logs of specific deployment: kubectl logs deployment/accommodations-deployment
* List pods: kubectl get pods 
* Get all logs of specific pod: kubectl logs accommodations-deployment-<deployment_hash>

Kubernetes IP (should change): http://169.51.16.54:32641/v1/accommodations/

## Endpoints
* Swagger `GET /swagger-ui.html`
* Health: `GET /health`
* Environment: `GET /env`
* Metrics (all): `GET /metrics`
* Custom metrics: `GET /custommetrics`
* Restart app: `POST /test/restart`
* Simulate heavy traffic: `GET /test/fibonnacci/{n}`
* ...

### Accommodations

### Configuration test 

#### Change notification message
IDEA:
When user updates accommodation the accommodation owner is notified that it has been changed.
* Localhost: consul kv put config/accommodations/app/message/notify/user/accommodation/changed "Msg from fri-riders"
* Postman: POST http://<host>:8500/v1/kv/config/accommodations,dev/app/message/notify/user/accommodation/changed 
@Body "Your accommodation was changed"

### Logging test
* Display all requests to `GET /v1/accommodations`
    * DSL query (all accommodations requests):
        {
          "bool": {
            "must": {
              "query_string": {
                "query": "(contextMap.uri:\"v1/accommodations\")"
              }
            }
          }
        }
        or
        {
            "query": {
                "match" : {
                    "contextMap.uri" : "v1/accommodations"
                }
            }
        }
    * All updates of accommodation with id 1: 
        contextMap.uri :"v1/accommodations/1"





