# PayCore

Plataforma de autorização e antifraude de cartões de crédito, construída como
microserviços. Projeto de estudo, feito fase a fase.

## Status

Fase 1 em andamento. O primeiro serviço, o **card-service**, está pronto: emite,
consulta, bloqueia e desbloqueia cartões.

## Arquitetura

Cada serviço segue a **arquitetura hexagonal** (portas e adaptadores), com a
regra de dependência da **Clean Architecture**: as regras de negócio ficam no
domínio, em Java puro, e o banco e a API entram por adaptadores que dependem
dele.

```
adapter.in.web  →  application  →  domain  ←  adapter.out
(API HTTP)         (casos de uso)    (regras)   (banco, gerador de números)
```

Cada serviço tem o seu próprio banco no mesmo Postgres, e um serviço não
consegue ler o banco de outro.

## Práticas

- **TDD** nas regras de domínio e nos casos de uso.
- **DDD:** domínio modelado a partir de um Event Storming do fluxo de
  autorização, com entidades, value objects e repositórios como portas.
- **Testes em camadas:** unidade sem Spring para o domínio e os casos de uso,
  `@WebMvcTest` para a API e Testcontainers para a persistência.

## Tecnologias

**Em uso:** Java 25, Spring Boot 4, Spring Data JPA, PostgreSQL 18, Flyway,
Docker Compose, springdoc (Swagger), JUnit, AssertJ, Mockito e Testcontainers.

**Planejado:** gRPC, Redis, Kafka, RabbitMQ, Quarkus, Kubernetes e Terraform.

## Como rodar

Precisa de Java 25 e Docker.

```bash
docker compose up -d
./mvnw -pl services/card-service spring-boot:run
```

Com o serviço rodando, a documentação da API fica em
http://localhost:8080/swagger-ui.html, onde dá para testar cada endpoint pelo
navegador.

## API do card-service

| Método | Endereço | Resposta |
|---|---|---|
| `POST` | `/cards` | 201, com o cartão criado |
| `GET` | `/cards/{id}` | 200 ou 404 |
| `POST` | `/cards/{id}/block` | 204, 404 ou 409 |
| `POST` | `/cards/{id}/unblock` | 204, 404 ou 409 |

Os números são gerados com um BIN fictício e o dígito verificador de Luhn, e
saem sempre mascarados nas respostas. Os erros seguem o formato `ProblemDetail`
(RFC 9457).

## Testes

Os testes de integração usam Testcontainers, então o Docker precisa estar
ligado.

```bash
./mvnw verify
```
