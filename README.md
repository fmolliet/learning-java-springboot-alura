# Aprendendo Spring Boot Curso Alura 

Conteudo que aprendi no curso do Alura de como fazer uma API Rest utilizando Spring boot.

Aulas:
- https://cursos.alura.com.br/course/spring-boot-api-rest
- https://cursos.alura.com.br/course/spring-boot-seguranca-cache-monitoramento
- https://cursos.alura.com.br/course/spring-boot-profiles-testes-deploy

### Pré-requisitos

- Java 11

- Maven

### Build

```shell
$ > mvn clean package
```

### Build e Run em Container

```shell
$ > docker build –t alura/forum
```

-----

```shell
$ > docker run -p 8080:8080 alura/forum
```
ou  

```shell
$ > docker run -p 8080:8080 -e FORUM_DATABASE_URL='jdbc:h2:mem:alura-forum' -e FORUM_DATABASE_USERNAME='sa' -e FORUM_DATABASE_PASSWORD='' -e FORUM_JWT_SECRET='123456' -e SPRING_PROFILES_ACTIVE='prod' -e PORT='8080' alura/forum
```

### Deploy Heroku 

```shell
$ > heroku container:login
```

```shell
$ > heroku create <NOME DO PROJETO> 
```

```shell
$ > heroku create <NOME DO PROJETO> 
```

```shell
$ > heroku git:remote -a <NOME DO PROJETO>   
```

```shell
$ > heroku container:push web   
```

```shell
$ > heroku container:release web 
```