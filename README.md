### Aplicativo Ponto de Venda (PDV)

##### Nome: Erick Daniel Teixeira Vier - RA: 235908-1

##

<details><summary>Construção PDV Desktop</summary>

## Requisitos
- Java 17 ou superior
- Maven 3.6.0 ou superior
- Banco de dados PostgreSQL 15.3 ou superior

## Configuração do Ambiente
1. Instale o Java 17: [Download Java 17](https://www.oracle.com/java/technologies/downloads/#java17)
2. Instale o Maven: [Download Maven](https://maven.apache.org/download.cgi)
3. Crie um banco chamado postgres e um schema chamado pdvdesktop ou modifique o arquivo persistence.xml colocando as informações de seu ambiente

### Compilação e Empacotamento
Execute o seguinte comando na raiz do projeto para compilar o código e gerar o JAR:

```bash
mvn clean package   
ou
mvn clean package -Dmaven.test.skip=true

```
## Execução

```bash
java -jar target/pdv-1.0-SNAPSHOT.jar
```

</details>

<details><summary>Construção Web</summary>

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

</details>
