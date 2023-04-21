# Bank Server Application

Application that provides REST Service endpoints to facilitate banking functions.
___

## Tools and Technologies Used:
|Language <br><br> [![](https://skillicons.dev/icons?i=java&theme=light)]()|Framework <br><br> [![](https://skillicons.dev/icons?i=spring&theme=light)]()|Database <br><br> [![](https://skillicons.dev/icons?i=mysql&theme=light)]()|Build System <br><br> [![](https://skillicons.dev/icons?i=maven&theme=light)]()|Containerization <br><br> [![](https://skillicons.dev/icons?i=docker&theme=light)]()|Hosting <br><br> [![](https://skillicons.dev/icons?i=aws&theme=light)]()|
|---|---|---|---|---|---|
___

## Project Structure
### Branches

```mermaid
  graph LR;
    A[Bank Application]-->B[Standalone Applications]
    A-->C[Server Application]
      B--->E(BankAppCollections)
      B--->F(BankAppJDBC)
      B--->G(BankAppJPA)
      B--->H(BankAppSpringJPA)
      C--->I(BankAppSpringBoot)
```

### How to run

- Switch to the respective branch and follow the detailed instructions
