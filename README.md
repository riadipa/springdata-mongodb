# springdata-mongodb
It is a spring-boot application with Mongo DB as backend. 
Data in Mongo DB collections are exposed as REST api endpoints
The appplication also processes the data across multiple collections using map-reduce techniques and expose the process collection through a REST endpoint.

### support for docker:
The application can be deployed in two separate containers - one running the spring-boot app and the other running  the mongodb.
The two containers have been configured to run under the same docker network. Refer to the docker-compose.yml for implementation details.

#### Technology stack used:
1. java 1.8
2. mongo db 4.0.1
3. spring-boot 2.0.3
