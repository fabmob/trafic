# Prototype de capture des données

## Description

La solution pour la captation des flux cita.lu et telraam se compose de :

- Un batch d'alimentation : batch générique Open Source en java à l’aide du Framework Spring permettant de récolter les données des caméras, spring.io
- Une base de donnée de type «Time Series », timescaledb de PostgreSQL www.timescale.com
- Un outil de visualisation des données, Graphana, grafana.com/grafana
![Schema](./assets/CaptureSchema.png)

## Stack technique
- Configuration Run = exécuter les éléments
- Configuration Dev = développement

Minimal Configuration :
- Docker : Run + Dev
- Java 11.0.7 : Run + Dev
- Maven : Dev
- EDI IntelliJ ou Eclipse : Dev

## Le batch de capture

Le batch est développer à l'aide de Spring Batch. Un batch est composé de trois couches qui s’exécute périodiquement :
- Reader : lecture périodique des urls pour lire les données 
- Processor : conversion / formatage des données
- Writer : insertion dans la DB

[Documentation officiel](https://spring.io/projects/spring-batch)
####  Eléments clés
- trafic\batch\src\main\resources\application.properties : database configuration
- trafic\batch\src\main\java\com\datex\batch\BatchApplication.java : main program
- trafic\batch\src\main\java\com\datex\batch\BatchConfiguration.java : configuration des batchs
####  Build / Execute
Le jar est généré à l'aide de maven  `./mvnw clean package`  et s’exécute à l'aide de la commande `java -jar target/gs-batch-processing-0.1.0.jar`

## Mise en oeuvre de la DB
Tous les sources sont dans trafic\batch\sql

A exécuter dans le répertoire  \batch\src :
```
################################
	Create db
	execute each command in .../batch/sql
################################
docker run -d --name traficdb -p 5432:5432 -e POSTGRES_PASSWORD=password timescale/timescaledb:latest-pg12
docker cp createEmptyDB.sql traficdb:createEmptyDB.sql
docker cp telraam.csv traficdb:telraam.csv
docker cp trafic_time.csv traficdb:trafic_time.csv
docker exec -it traficdb psql -U postgres -f createEmptyDB.sql

################################
	optional : load data
################################
docker exec -it traficdb psql -U postgres -d trafic -c "\COPY telraam FROM telraam.csv CSV"
docker exec -it traficdb psql -U postgres -d trafic -c "\COPY trafic_time FROM trafic_time.csv CSV"
```

## Graphana

#### Installer Graphana dans docker 
`docker run -d -p 3000:3000 grafana/grafana`  

#### installer le worldmap panel

Dans le dashboard Docker, ouvrir un client linux : bouton CLI 
Exécuter :
`grafana-cli plugins install grafana-worldmap-panel`

#### Connection à graphana
`localhost:3000`
- Première connection : user = admin/ password = admin  
- Définir un nouveau mot de passe exemple : new password  graphana

#### Importer les dashboards

Les tableaux de bord sont exportés au format Grafana JSON et contiennent tout ce dont vous avez besoin (mise en page, variables, styles, sources de données, requêtes, etc.).

Les dashboards sont dans le répertoire  trafic\batch\graphana

Pour importer un tableau de bord, cliquez sur l'icône + dans le menu latéral, puis cliquez sur Importer.

#### Exporter les dashboards

La fonction d'exportation est accessible dans la fenêtre de partage que vous ouvrez en cliquant sur le bouton de partage dans le menu du tableau de bord.

