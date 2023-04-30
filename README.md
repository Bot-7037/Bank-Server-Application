# Spring Boot
### Banking Application using Spring Boot

## How to run
### Requirements
- JDK 11
- MySQL
- Apache Maven
### Steps
- Create a database using workbench/command-line (The application will try to connect to this database)
- Set up the properties file in `/Config.properties` by entering respective credentials for example
  ```properties
  username=username
  password=password
  mysqlJDBCDriver=com.mysql.cj.jdbc.Driver
  jdbcurl=jdbc:mysql://localhost:3306/BankApp
  ```
- Clone the branch into local system using
  ```
  git clone --brach BankAppSpringJPA https://github.com/Bot-7037/Bank-Server-Application.git
  ```
- Install the dependencies using `mvn install`
- Run the `/src/main/java/com/cg/bankapp/main/Main.java`
