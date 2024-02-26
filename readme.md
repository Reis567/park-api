# PARK API 

Bem-vindo ao projeto PARK API, uma API de estacionamento desenvolvida com Spring Boot 3 e Java 17. Este README fornecerá informações essenciais para começar a trabalhar no projeto.

## Configuração do Ambiente

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina antes de começar:

- Java 17: [Download e Instalação do Java 17](https://www.oracle.com/java/technologies/javase-downloads.html)
- Maven: [Download e Instalação do Maven](https://maven.apache.org/download.cgi)
- IDE (Eclipse, IntelliJ, VS Code, etc.): Escolha uma IDE de sua preferência.
- MySQL

## Configuração do Projeto

1. Clone o repositório PARK API:

    ```bash
    git clone https://github.com/Reis567/park-api.git
    ```

2. Abra o projeto em sua IDE escolhida.

3. Certifique-se de que a versão do Java está configurada para 17.

4. Execute o Maven para baixar as dependências do projeto.

## URLs Disponíveis

### Vagas

- **Criar Nova Vaga**
  - URL: `http://localhost:8080/api/v1/vagas`
  - Método: POST
  - Descrição: Endpoint para criar uma nova vaga de estacionamento.
  - Restrições: Apenas perfis com a role 'ADMIN' têm permissão para acessar este recurso.

- **Buscar Vaga por Código**
  - URL: `http://localhost:8080/api/v1/vagas/{codigo}`
  - Método: GET
  - Descrição: Endpoint para recuperar informações de uma vaga específica por código.
  - Restrições: Apenas perfis com a role 'ADMIN' têm permissão para acessar este recurso.

### Clientes

- **Criar Novo Cliente**
  - URL: `http://localhost:8080/api/v1/clientes`
  - Método: POST
  - Descrição: Endpoint para criar um novo cliente vinculado a um usuário cadastrado.

- **Buscar Cliente por ID**
  - URL: `http://localhost:8080/api/v1/clientes/{clienteId}`
  - Método: GET
  - Descrição: Endpoint para recuperar informações de um cliente específico por ID.

- **Listar Todos os Clientes**
  - URL: `http://localhost:8080/api/v1/clientes`
  - Método: GET
  - Descrição: Endpoint para recuperar informações de todos os clientes cadastrados.

- **Detalhes do Cliente Logado**
  - URL: `http://localhost:8080/api/v1/clientes/detalhes`
  - Método: GET
  - Descrição: Endpoint para obter detalhes do cliente atualmente autenticado.

### Estacionamentos

- **Check-in no Estacionamento**
  - URL: `http://localhost:8080/api/v1/estacionamentos/check-in`
  - Método: POST
  - Descrição: Endpoint para realizar o check-in de um cliente no estacionamento.

- **Buscar Check-in por Recibo**
  - URL: `http://localhost:8080/api/v1/estacionamentos/check-in/{recibo}`
  - Método: GET
  - Descrição: Endpoint para retornar os detalhes do check-in associado ao recibo fornecido.

- **Check-out no Estacionamento**
  - URL: `http://localhost:8080/api/v1/estacionamentos/check-out/{recibo}`
  - Método: PUT
  - Descrição: Endpoint para realizar o check-out de um cliente no estacionamento.

- **Listar Usos de Estacionamento por CPF do Cliente**
  - URL: `http://localhost:8080/api/v1/estacionamentos/cpf/{clienteCPF}`
  - Método: GET
  - Descrição: Endpoint para retornar uma lista paginada de usos de estacionamento associada ao CPF do cliente fornecido.

- **Listar Todos os Estacionamentos do Cliente Logado**
  - URL: `http://localhost:8080/api/v1/estacionamentos`
  - Método: GET
  - Descrição: Endpoint para retornar uma lista paginada de usos de estacionamento associada ao CPF do cliente logado.

### Usuários

- **Criar Novo Usuário**
  - URL: `http://localhost:8080/api/v1/usuarios/registro`
  - Método: POST
  - Descrição: Recurso para criar um novo usuário.

- **Recuperar Usuário por ID**
  - URL: `http://localhost:8080/api/v1/usuarios/{id}`
  - Método: GET
  - Descrição: Recurso para recuperar um usuário pelo ID.

- **Recuperar Todos os Usuários**
  - URL: `http://localhost:8080/api/v1/usuarios`
  - Método: GET
  - Descrição: Recurso para recuperar todos os usuários.

- **Atualizar Senha do Usuário**
  - URL: `http://localhost:8080/api/v1/usuarios/{id}`
  - Método: PATCH
  - Descrição: Recurso para atualizar a senha do usuário.

## Documentação Adicional

Para obter mais informações sobre os endpoints e suas funcionalidades, consulte a documentação da API disponível em `http://localhost:8080/swagger-ui/index.html`.
