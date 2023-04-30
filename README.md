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
  ```
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
- **Postman**:
  <details><summary><strong>GET</strong></summary><br>
      <details><summary><i>Show Balance</i></summary><code>localhost:9999/bankapp-dev/showBalance/1</code></details>
      <details><summary><i>Get Last Transactions</i></summary><code>localhost:9999/bankapp/showLastTransactions/1</code></details>
  </details>
  <details><summary><strong>POST</strong></summary><br>
      <details><summary><i>Deposit Money</i></summary><code>localhost:9999/bankapp/depositMoney/</code></details>
      <details><summary><i>Withdraw Money</i></summary><code>localhost:9999/bankapp/withdrawMoney/</code></details>
      <details><summary><i>Fund Transfe</i>r</summary><code>localhost:9999/bankapp/fundTransfer</code></details>
  </details>
