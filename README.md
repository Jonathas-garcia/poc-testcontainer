# poc-testcontainer
Projeto desenvolvido para testar biblioteca [TestContainers](https://www.testcontainers.org/)

## Requisitos

Ter o docker instalado na máquina

## Testes unitário e integração

Para executar testes com Testcontainers, execute:

```
./mvnw test
```

Será executado localmente um container do mongo para executar uma chamada HTTP ao serviço(verificar classe de teste).


## Executando projeto

Para iniciar os projetos através do docker-compose:

```
docker-compose up -d
```

Para parar os projetos:

```
docker-compose down
```
