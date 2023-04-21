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
    subgraph Meant to be run on single machine
      B--->E(BankAppCollections)
      B--->F(BankAppJDBC)
      B--->G(BankAppJPA)
      B--->H(BankAppSpringJPA)
    end
    subgraph Meant serve client requests on a server
      C--->I(BankAppSpringBoot)
    end
```
