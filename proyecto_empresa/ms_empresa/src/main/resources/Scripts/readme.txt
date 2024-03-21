Script para generacion de componentes exito

envio

mvn package install -Dmaven.test.skip=true
docker build -t supercontainerapps/sg-intm-be-envio .
docker tag supercontainerapps/sg-intm-be-envio supercontainerapps/sg-intm-be-envio:3.0.0
docker push supercontainerapps/sg-intm-be-envio:3.0.0

pago

mvn package install -Dmaven.test.skip=true
docker build -t supercontainerapps/sg-intm-be-pago .
docker tag supercontainerapps/sg-intm-be-pago supercontainerapps/sg-intm-be-pago:3.0.0
docker push supercontainerapps/sg-intm-be-pago:3.0.0

gateway


docker build -t supercontainerapps/sg-intm-be-gateway .
docker tag supercontainerapps/sg-intm-be-gateway supercontainerapps/sg-intm-be-gateway:1.0.2
docker push supercontainerapps/sg-intm-be-gateway:1.0.2


fachada

docker build -t supercontainerapps/sg-intm-be-fachada .
docker tag supercontainerapps/sg-intm-be-fachada supercontainerapps/sg-intm-be-fachada:2.0.0
docker push supercontainerapps/sg-intm-be-fachada:2.0.0
