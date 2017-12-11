### Cloud instructions
Public ip : http://169.51.16.54

* bx login -a https://api.eu-de.bluemix.net  
* bx cs cluster-config rso-fririders
* SET KUBECONFIG=...
* kubectl get nodes
* kubectl proxy 
* kubectl autoscale deployment accommodations-deployment --min=1 --max=10 --cpu-percent=80

Ukazi kubectl:
* Vsi logi določenega deploymenta: kubectl logs deployment/accommodations-deployment
* Pridobi seznam podov: kubectl get pods 
* Vsi logi določenega poda: kubectl logs accommodations-deployment-<deployment_hash>

