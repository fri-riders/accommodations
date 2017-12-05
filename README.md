### Cloud instructions
Public ip : http://169.51.16.54

* bx login -a https://api.eu-de.bluemix.net  
* bx cs cluster-config rso-fririders
* SET KUBECONFIG=...
* kubectl get nodes
* kubectl proxy 

Ukazi kubectl:
* Vsi logi določenega deploymenta: kubectl logs deployment/accommodations-deployment
* Pridobi seznam podov: kubectl get pods 
* Vsi logi določenega poda: kubectl logs accommodations-deployment-<deployment_hash>

