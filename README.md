# Todo API

Backend REST para gerenciamento de tarefas com Spring Boot, PostgreSQL e Spring Data JPA.

## Requisitos

- Java 17 ou superior
- Docker Desktop, para executar o PostgreSQL

## Executar

1. Inicie o banco:

```powershell
docker compose up -d
```

2. Inicie a aplicação:

```powershell
.\mvnw.cmd spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

A conexão padrão usa o banco `mydatabase`, usuário `myuser` e senha `secret`. Essas configurações podem ser substituídas pelas variáveis `DB_URL`, `DB_USERNAME` e `DB_PASSWORD`.

## Endpoints

- `POST /api/tarefas` cria uma tarefa.
- `GET /api/tarefas` lista as tarefas.
- `GET /api/tarefas/{id}` busca uma tarefa.
- `PUT /api/tarefas/{id}` atualiza uma tarefa.
- `DELETE /api/tarefas/{id}` remove uma tarefa.

Exemplo de criação:

```json
{
  "nome": "Estudar Spring Boot",
  "descricao": "Implementar o CRUD de tarefas",
  "status": "PENDENTE",
  "observacoes": "Revisar validações"
}
```

Os status aceitos são `PENDENTE`, `EM_ANDAMENTO` e `CONCLUIDA`.

## Testes

Os testes usam H2 em memória e não exigem PostgreSQL:

```powershell
.\mvnw.cmd -B test
```

O script PostgreSQL para criação da tabela está em [`script_bd.sql`](script_bd.sql).
