# FundsTransfer

How to start the FundsTransfer application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/fund-transfer-api-0.0.1.jar server config.yml`
1. The project uses an in memory h2  database and liquibase for database migration
   to run the migrations run the command after building the application `java -jar target/fund-transfer-api-0.0.1.jar server config.yml`
1. Some resources have been created to make sure that you can test the functionality of the application, you can create a bank account using the resource 
http://localhost:8080/swagger#!/fund/createUserAccount 
you can test fund transfer between two users using the resource http://localhost:8080/swagger#!/fund/transferFunds
1. To clear and reset the in memory database run the command `java -jar target/fund-transfer-api-0.0.1.jar db drop-all config.yml --confirm-delete-everything`


