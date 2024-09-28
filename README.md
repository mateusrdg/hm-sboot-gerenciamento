# Health&Med
## Diagrama de Arquitetura
![Diagrama de Arquitetura](assets/diagrama arqb.png)

## Visão Geral

O projeto **Health&Med** consiste em dois microserviços principais: **Gerenciamento** e **Agendamento**.

### Microserviço de Gerenciamento

O microserviço de gerenciamento é responsável por:

- **Cadastro de Usuários**: Integra-se com o Amazon Cognito para autenticação e controle de acesso dos usuários.
- **Controle de Conflitos de Agendamento**: Implementa um lock pessimista para evitar que pacientes agendem consultas no mesmo horário, garantindo que não haja conflitos.

### Microserviço de Agendamento

O microserviço de agendamento tem como função:

- **Envio de Confirmação de Consulta**: Quando uma consulta é marcada, o microserviço de gerenciamento envia uma mensagem via Amazon SQS para o microserviço de agendamento, que, por sua vez, utiliza o Amazon SES para enviar um e-mail de confirmação da consulta ao médico.




## Deploy e CI/CD
![Fluxo de Deploy CI/CD](assets/deploy cicd.png)

O deploy das aplicações é realizado através do **GitHub Actions**. As Pull Requests (PRs) não direcionadas à branch **main** são validadas pelo **SonarQube**. A partir dessas validações, uma imagem é gerada no **Amazon ECR** e o deploy é realizado através de uma **Service Task** no **Amazon ECS**.


## Cobertura de testes
![img.png](img.png)