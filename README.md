### Aplicativo Ponto de Venda (PDV)

##### Nome: Erick Daniel Teixeira Vier - RA: 235908-1

##

#### Construindo o Projeto

1. Navegue até o diretório do projeto em seu terminal.
2. Execute `mvn clean install` para construir o projeto.

Isso criará um arquivo JAR no diretório `target`, que contém o aplicativo compilado.

#### Executando a Aplicação

1. Certifique-se de ter um banco de dados em execução (PostgreSQL).
2. Antes de executar é necessário que exista um banco e um schema padrão criados. O nome do banco padrão é `postgres` e o schema padrão é `pdv`
3. Execute `mvn spring-boot:run` para iniciar a aplicação usando o servidor embutido do Spring Boot.

O aplicativo deve estar normalmente acessível em `http://localhost:8080` (porta padrão do Spring Boot).

### Documentação Open API
`http://localhost:8080/swagger-ui/index.html#/`

### Listagem de vendas Web
`http://localhost:8080/vendas`


