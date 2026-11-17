# PayCore

[![CI](https://github.com/brunoaguiar-dev/paycore/actions/workflows/ci.yml/badge.svg)](https://github.com/brunoaguiar-dev/paycore/actions/workflows/ci.yml)

Plataforma de autorização e antifraude de cartões de crédito, construída como
microserviços. Projeto de estudo, feito fase a fase.

## Status

Fase 1 em andamento. O **card-service** emite, consulta, bloqueia e desbloqueia
cartões. O **account-service** abre e consulta a conta de crédito de cada
cartão, com o limite total e o disponível.

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

Com Docker:

```bash
docker compose up -d --build
```

| Serviço | Porta | Swagger |
|---|---|---|
| card-service | 8080 | http://localhost:8080/swagger-ui.html |
| account-service | 8081 | http://localhost:8081/swagger-ui.html |

Para desenvolvimento, com Java 25, suba só o Postgres e rode o serviço pelo
Maven:

```bash
docker compose up -d postgres
./mvnw -pl services/<serviço> spring-boot:run
```

## API do card-service

| Método | Endereço | Resposta |
|---|---|---|
| `POST` | `/cards` | 201 com o cartão criado, ou 400 |
| `GET` | `/cards/{id}` | 200 ou 404 |
| `POST` | `/cards/{id}/block` | 204, 404 ou 409 |
| `POST` | `/cards/{id}/unblock` | 204, 404 ou 409 |

Os números são gerados com um BIN fictício e o dígito verificador de Luhn, e
saem sempre mascarados nas respostas. Os erros seguem o formato `ProblemDetail`
(RFC 9457).

## API do account-service

| Método | Endereço | Resposta |
|---|---|---|
| `POST` | `/accounts` | 201 com a conta criada, 400, ou 409 se o cartão já tiver conta |
| `GET` | `/accounts/{id}` | 200 ou 404 |

Os valores usam `BigDecimal`, com 2 casas e arredondamento bancário (HALF_EVEN).
Cada cartão tem uma única conta de crédito.

## Testes

Os testes de integração usam Testcontainers, então o Docker precisa estar
ligado.

```bash
./mvnw verify
```
