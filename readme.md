# Task API

API REST de gestion de tâches avec Java + Spring Boot + H2.

## Technologies & Dépendances

* Java 21
* Spring Boot
* Spring Web MVC
* Spring Data JPA
* H2 Database
* MockMvc
* Maven

## Fonctionnalités

* POST /tasks : créer une tâche
* GET /tasks : lister les tâches
* GET /tasks?status=TODO : filtrer par statut
* PATCH /tasks/{id}/status : modifier le statut

### Statuts

TODO → IN_PROGRESS → DONE


Les autres transitions retournent 409 Conflict.


## Commande de lancement de projet

.\mvnw.cmd spring-boot:run


API :
http://localhost:8080/tasks


Console H2 :


http://localhost:8080/h2-console


## Tests automatisés

Trois tests sont réalisés :

1. Création valide → 201 Created
2. Titre invalide → 400 Bad Request
3. Transition interdite → 409 Conflict

Lancer les tests :

.\mvnw.cmd clean test

Résultat attendu :

Tests run: 3, Failures: 0, Errors: 0
BUILD SUCCESS


## Structure

controller/
service/
repository/
model/
exception/

