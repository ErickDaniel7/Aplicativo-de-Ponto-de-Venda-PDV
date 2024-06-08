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
