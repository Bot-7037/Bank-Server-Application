# JDBC
### Banking Application using JDBC

## How to run
- Ensure JDK 11 has been set up on your system
- Download and install MYSQL workbench (or any other DBMS of your choice)
- Create a database using workbench/command-line (The application will try to connect to this database)
- Set up the properties file in `/Config.properties` by entering respective credentials for example
  ```properties
  username=username
  password=password
  mysqlJDBCDriver=com.mysql.cj.jdbc.Driver
  jdbcurl=jdbc:mysql://localhost:3306/BankApp
  ```
- Download and set-up Apache Maven on your system
- Clone the branch into local system using
  ```
  got clone --brach BankAppJDBC https://github.com/Bot-7037/Bank-Server-Application.git
  ```
- Install the dependencies using `mvn install`
- Run the `/src/main/java/com/cg/bankapp/main/Main.java`
