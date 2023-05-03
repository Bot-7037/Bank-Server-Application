# Spring Boot
### Banking Application using Spring Boot

## How to run
### Requirements
- JDK 11
- MySQL
- Apache Maven
- Postman
- Spring Tool Suite / Eclipse
### Setup Steps
- Create a database using workbench/command-line (The application will try to connect to this database)
- Clone the branch into local system using
  ```
  git clone --brach BankAppSpringJPA https://github.com/Bot-7037/Bank-Server-Application.git
  ```
- Set up the database and application properties under `/src/main/resources/application-dev.properties` for example
  ```properties
  server.port=9999

  server.servlet.context-path=/bankapp

  spring.datasource.url=jdbc:mysql://localhost:3306/bankappspringrestjpa
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  spring.datasource.username=username
  spring.datasource.password=password

  spring.jpa.show-sql=false
  spring.jpa.hibernate.ddl-auto=create
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect

  spring.main.banner-mode=off

  spring.mvc.pathmatch.matching-strategy=ant-path-matcher
  ```
- Switch the properties phase under `/src/main/resources/application.properties`
- Install the dependencies using `mvn install`
- Run the `/src/main/java/com/cg/bankapp/Main.java` ans **Spring Boot Application** or **Java Application**
- Send the required requests to the application using Postman or SwaggerUI
### Sending Requests
- **SwaggerUI** : https://localhost:9999/bankapp/swagger-ui.html
- **Postman**: https://web.postman.com

### Requests Format:
#### GET

- `localhost:9999/bankapp/show Balance/{id}` : get balance of an account
- `localhost:9999/bankapp/getLastTransactions/{id}` : get last 10 transactions of an account
    

#### POST

- `localhost:9999/bankapp/depositMoney`
    

``` json
{
    "accountNumber" : "{accountNumber}",
    "amount" : "{amount}"
}

```

- `localhost:9999/bankapp/withdrawMoney`
    

``` json
{
    "accountNumber" : "{accountNumber}",
    "amount" : "{amount}"
}

```

- `localhost:9999/bankapp/fundTransfer`
    

``` json
{
    "senderAccount" : "{accountNumber}",
    "recieverAccount" : {"accountNumber"},
    "amount" : "{amount}"
}

```




### Running on Docker
- Create a package of your application using `mvn install`. 
- Locate the package (.jar) file inside `/target` folder
- Go to [Play With Docker](https://labs.play-with-docker.com/) 
- Create an instance
- Upload the package (.jar) file, `DOCKERFILE`, and `docker-compose.yml` onto the instance
- Build the application using
  ```
  docker compose up --build -d
  ```
  OR
  ```
  docker compose up --build
  ```
