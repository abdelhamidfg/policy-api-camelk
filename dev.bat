kamel run  --profile=openshift   --name policy-api PolicyAPI.java api-spec.xml
kamel run  --profile=openshift   --name policy-api PolicyAPI.java api-spec.xml --open-api api/openapi.json --resource file:api/openapi.json --property file:cfg/svc.properties  --resource file:map/response-mapping.adm