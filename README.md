# 📦 Sistema de Gestão de Estoque Web & REST API

> Aplicação moderna de controle de estoque migrada de Java Swing para **Spring Boot 3**, com banco de dados **PostgreSQL no Supabase** e deploy automatizado em nuvem via **Docker no Render**.

[![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-6DB33F?logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-4169E1?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Supabase](https://img.shields.io/badge/Supabase-Platform-3ECF8E?logo=supabase&logoColor=white)](https://supabase.com/)
[![Docker](https://img.shields.io/badge/Docker-Multi--stage-2496ED?logo=docker&logoColor=white)](https://www.docker.com/)
[![Render](https://img.shields.io/badge/Render-Deploy-46E3B7?logo=render&logoColor=white)](https://render.com/)
[![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Active-brightgreen.svg)]()

---

## 📋 Índice

- [Visão Geral](#-visão-geral)
- [Arquitetura](#-arquitetura-da-solução)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração do Banco de Dados](#️-configuração-do-banco-de-dados-no-supabase)
- [Variáveis de Ambiente](#-variáveis-de-ambiente)
- [Deploy no Render](#️-deploy-no-render-via-docker)
- [Execução Local](#-execução-local)
- [Interface Web](#️-interface-web-embutida)
- [API REST](#-documentação-da-api-rest)
- [Estrutura do Projeto](#-estrutura-de-pastas-do-projeto)
- [Testes](#-testes)
- [Troubleshooting](#-troubleshooting)
- [Contribuição](#-contribuição)
- [Licença](#-licença)
- [Autor](#-autor)

---

## 🎯 Visão Geral

Este projeto é um **Sistema de Gestão de Estoque** completo, desenvolvido com arquitetura moderna e pronta para produção. Originalmente uma aplicação desktop em Java Swing, foi completamente reescrita para **Spring Boot 3** com:

- ✅ **API REST** completa para integração com qualquer frontend
- ✅ **Dashboard Web responsivo** embutido (HTML5 + Tailwind CSS)
- ✅ **Banco de dados na nuvem** (PostgreSQL no Supabase)
- ✅ **Deploy automatizado** via Docker no Render (plano gratuito)
- ✅ **Validações robustas** (Bean Validation + regras de negócio)
- ✅ **Pool de conexões otimizado** para ambiente serverless (HikariCP + PgBouncer)

---

## 🚀 Arquitetura da Solução

| Camada | Tecnologia | Descrição |
|--------|------------|-----------|
| **Linguagem** | Java 17 LTS | Versão de longo suporte com performance otimizada |
| **Framework** | Spring Boot 3.2+ | Auto-configuração, starter web, data JPA, validation |
| **Banco de Dados** | PostgreSQL 15 (Supabase) | Gerenciado, com connection pooler nativo |
| **ORM** | Spring Data JPA / Hibernate | Repositórios tipados, queries derivadas |
| **Pool de Conexões** | HikariCP | Configurado para Transaction Pooler (porta 6543) |
| **Frontend** | HTML5 + Tailwind CSS + Fetch API | Servido estaticamente pelo Spring Boot |
| **Build** | Maven 3.9+ | Multi-module ready, dependency management |
| **Container** | Docker Multi-stage | Build Maven → Runtime Alpine JRE (~80MB) |
| **Cloud** | Render (Free Tier) | Deploy automático via Git, SSL grátis |
| **CI/CD** | Render Auto-Deploy | Push na `main` = novo deploy |

---

## 📦 Pré-requisitos

### Para desenvolvimento local:
- ☕ **Java 17+** (JDK) — [Download](https://adoptium.net/temurin/releases/?version=17)
- 📦 **Maven 3.9+** — [Download](https://maven.apache.org/download.cgi) ou use `./mvnw`
- 🐳 **Docker 24+** (opcional, para container local) — [Download](https://www.docker.com/get-started)
- 🗄️ **Conta no Supabase** (gratuita) — [Criar](https://supabase.com/)
- ☁️ **Conta no Render** (gratuita) — [Criar](https://render.com/)

### Para apenas rodar o container:
- 🐳 **Docker 24+**

---

## 🗄️ Configuração do Banco de Dados no Supabase

### 1.1 Criar as Tabelas
1. Acesse o dashboard do seu projeto no [Supabase](https://supabase.com)
2. No menu lateral, clique em **SQL Editor**
3. Abra o arquivo [`database/schema.sql`](database/schema.sql), copie o conteúdo, cole no editor e clique em **Run**
4. O script criará:
   - Tabelas: `usuarios`, `produtos`, `movimentacoes`
   - Índices otimizados para consultas frequentes
   - Dados iniciais de teste (usuário admin, produtos exemplo)

### 1.2 Obter a String de Conexão com Connection Pooler (Porta 6543) ⚡
> **Essencial para o plano gratuito do Supabase** — evita esgotamento de conexões.

1. No Supabase, vá em **Project Settings → Database**
2. Na seção **Connection string**, selecione:
   - Aba: **URI**
   - Modo: **Transaction** (porta **6543**)
3. A URL terá o formato:
   ```text
   jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:6543/postgres?sslmode=require&prepareThreshold=0
   ```
4. **Parâmetro crítico:** `&prepareThreshold=0` desativa o cache de prepared statements do driver PostgreSQL, **obrigatório** para funcionar com PgBouncer/Supavisor na porta 6543.
5. O usuário do pooler segue o padrão: `postgres.[ref-do-projeto]`
   - Encontre seu `ref-do-projeto` na URL do painel: `https://supabase.com/dashboard/project/<ref-do-projeto>`

---

## ⚙️ Variáveis de Ambiente

| Variável | Obrigatória | Descrição | Exemplo |
|----------|-------------|-----------|---------|
| `SPRING_DATASOURCE_URL` | ✅ Sim | JDBC URL do Supabase Pooler | `jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:6543/postgres?sslmode=require&prepareThreshold=0` |
| `SPRING_DATASOURCE_USERNAME` | ✅ Sim | Usuário do pooler | `postgres.abcdefghijklmnop` |
| `SPRING_DATASOURCE_PASSWORD` | ✅ Sim | Senha do banco (Settings → Database) | `sua_senha_forte_aqui` |
| `SPRING_PROFILES_ACTIVE` | Não | Perfil ativo (`prod`, `dev`) | `prod` |
| `SERVER_PORT` | Não | Porta HTTP (Render injeta `PORT`) | `8080` |

> 💡 **Dica:** No Render, configure essas variáveis na aba **Environment** do Web Service. **Nunca** commite credenciais no código!

---

## ☁️ Deploy no Render (via Docker) — Gratuito 🎉

O Render não tem runtime nativo Java no plano gratuito, mas **suporta Docker** com deploy automático via Git.

### Passo a Passo:

1. **Suba o código** para um repositório no **GitHub** ou **GitLab**
2. Acesse o [Render Dashboard](https://dashboard.render.com) → **New + → Web Service**
3. Conecte o repositório do projeto
4. Configure:
   | Campo | Valor |
   |-------|-------|
   | **Name** | `gerenciador-estoque` (ou seu nome) |
   | **Environment** | `Docker` |
   | **Region** | Mais próxima (ex: `Ohio (US East)`, `Oregon (US West)`) |
   | **Branch** | `main` (ou `master`) |
   | **Instance Type** | `Free` |
5. **Variáveis de Ambiente** (aba **Environment**):
   - Adicione as 3 variáveis obrigatórias da tabela acima
6. Clique em **Create Web Service**
7. O Render executará o `Dockerfile` multi-stage e disponibilizará em: `https://<seu-nome>.onrender.com`

### ⚠️ Erro Comum — **LEIA ISSO**
> Se não configurar as variáveis no Render, o Spring Boot usará os valores de exemplo do `application.properties` e falhará com:
> ```
> PSQLException: FATAL: (ENOTFOUND) tenant/user postgres.sua_referencia_de_projeto not found
> ```
> **Solução:** Configure **todas as 3 variáveis** no painel do Render antes do primeiro deploy.

### `render.yaml` (Opcional — Blueprint as Code)
O projeto inclui `render.yaml` para deploy via IaC:
```bash
# Instale o Render CLI e faça:
render deploy --confirm
```

---

## 💻 Execução Local

### Opção A: Docker (Recomendado — ambiente idêntico ao prod)
```bash
# Build da imagem
docker build -t gerenciador-estoque .

# Execução com variáveis de ambiente
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:6543/postgres?sslmode=require&prepareThreshold=0" \
  -e SPRING_DATASOURCE_USERNAME="postgres.seu_ref" \
  -e SPRING_DATASOURCE_PASSWORD="sua_senha" \
  gerenciador-estoque
```
Acesse: `http://localhost:8080`

### Opção B: Maven (Desenvolvimento rápido)
```bash
# Com wrapper (não precisa instalar Maven)
./mvnw spring-boot:run

# Ou com Maven instalado
mvn spring-boot:run
```
> Configure as variáveis de ambiente no seu IDE ou no `application-local.properties` (gitignored).

### Opção C: Perfil de Desenvolvimento (H2 em memória)
```bash
# Crie application-dev.properties com H2 e rode:
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

---

## 🖥️ Interface Web Embutida

Todas as páginas são **Server-Side Rendered** (Thymeleaf-style via templates estáticos) e servidas diretamente pelo Spring Boot.

| Rota | Página | Funcionalidades |
|------|--------|-----------------|
| `/` | **Dashboard** | KPIs tempo real, valor total em estoque, alertas de reposição, últimas 10 movimentações |
| `/produtos` | **Catálogo** | CRUD completo, busca em tempo real, validação de preço/quantidade, paginação |
| `/movimentacoes` | **Operações** | Entrada (reposição) + Saída (venda/baixa) com validação de saldo negativo |
| `/relatorios` | **Relatórios** | Resumo executivo, valor financeiro, itens críticos, **impressão/PDF** |

**Tecnologias do Frontend:**
- **Tailwind CSS** via CDN (zero build step)
- **FontAwesome 6** para ícones
- **Fetch API** nativa (sem dependências JS)
- **Responsivo** (mobile-first)
- **Acessível** (semântico, contraste, foco visível)

---

## 📡 Documentação da API REST

Base URL: `https://<seu-app>.onrender.com/api` ou `http://localhost:8080/api`

> ✅ **CORS habilitado** — consumível por qualquer origem (React, Vue, Mobile, Postman)

### 5.1 Produtos (`/api/produtos`)

| Método | Endpoint | Descrição | Status Codes |
|--------|----------|-----------|--------------|
| `GET` | `/api/produtos` | Lista todos (filtro: `?busca=nome`) | 200 |
| `GET` | `/api/produtos/{id}` | Busca por ID | 200, 404 |
| `POST` | `/api/produtos` | Cadastra novo | 201, 400 |
| `PUT` | `/api/produtos/{id}` | Atualiza campos | 200, 400, 404 |
| `DELETE` | `/api/produtos/{id}` | Remove produto | 204, 404 |

**Payload (POST/PUT):**
```json
{
  "nome": "Monitor UltraWide 29 Pol",
  "descricao": "Monitor IPS 75Hz com portas HDMI e DisplayPort",
  "preco": 1150.00,
  "quantidadeEstoque": 10
}
```

**Validações:**
- `nome`: obrigatório, 3-100 chars
- `preco`: obrigatório, ≥ 0.01
- `quantidadeEstoque`: obrigatório, ≥ 0
- `descricao`: opcional, máx 500 chars

---

### 5.2 Movimentações (`/api/movimentacoes`)

| Método | Endpoint | Descrição | Status Codes |
|--------|----------|-----------|--------------|
| `GET` | `/api/movimentacoes` | Histórico completo (ordenado por data desc) | 200 |
| `POST` | `/api/movimentacoes/entrada` | Registra entrada → incrementa estoque | 201, 400, 404 |
| `POST` | `/api/movimentacoes/saida` | Registra saída → decrementa estoque (bloqueia se insuficiente) | 201, 400, 404 |
| `GET` | `/api/movimentacoes/relatorio` | Métricas consolidadas | 200 |

**Payload (Entrada/Saída):**
```json
{
  "produtoId": 1,
  "quantidade": 5,
  "motivo": "Venda balcão - Pedido #1024"
}
```

**Validações:**
- `produtoId`: deve existir
- `quantidade`: ≥ 1
- `motivo`: obrigatório, 3-200 chars
- **Saída:** retorna `400 Bad Request` se `quantidade > saldo_atual`

---

### 5.3 Exemplos cURL

```bash
# Listar produtos
curl https://gerenciador-estoque.onrender.com/api/produtos

# Buscar produto
curl https://gerenciador-estoque.onrender.com/api/produtos/1

# Criar produto
curl -X POST https://gerenciador-estoque.onrender.com/api/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome":"Teclado Mecânico","preco":350,"quantidadeEstoque":15}'

# Registrar entrada
curl -X POST https://gerenciador-estoque.onrender.com/api/movimentacoes/entrada \
  -H "Content-Type: application/json" \
  -d '{"produtoId":1,"quantidade":10,"motivo":"Recebimento NF #123"}'

# Registrar saída
curl -X POST https://gerenciador-estoque.onrender.com/api/movimentacoes/saida \
  -H "Content-Type: application/json" \
  -d '{"produtoId":1,"quantidade":2,"motivo":"Venda online"}'

# Relatório consolidado
curl https://gerenciador-estoque.onrender.com/api/movimentacoes/relatorio
```

---

## 📂 Estrutura de Pastas do Projeto

```text
Projeto_Gerenciador_Estoque_Java/
├── Dockerfile                         # Multi-stage: build (Maven) → runtime (Alpine JRE)
├── .dockerignore                      # Exclui target/, .git, IDE files
├── render.yaml                        # Blueprint Render (IaC)
├── pom.xml                            # Maven: deps, plugins, Java 17
├── database/
│   └── schema.sql                     # DDL + índices + seeds (Supabase PostgreSQL)
├── src/
│   ├── main/
│   │   ├── java/com/estoque/
│   │   │   ├── EstoqueApplication.java          # @SpringBootApplication
│   │   │   ├── config/
│   │   │   │   └── CorsConfig.java              # CORS global
│   │   │   ├── controller/
│   │   │   │   ├── ProdutoController.java       # REST + Web
│   │   │   │   ├── MovimentacaoController.java  # REST + Web
│   │   │   │   ├── RelatorioController.java     # REST + Web
│   │   │   │   └── WebController.java           # Rotas das páginas HTML
│   │   │   ├── dto/
│   │   │   │   ├── ProdutoDTO.java              # Validações (Jakarta Bean Validation)
│   │   │   │   └── MovimentacaoDTO.java
│   │   │   ├── model/
│   │   │   │   ├── Produto.java                 # @Entity, @Table
│   │   │   │   ├── Movimentacao.java            # @Entity, @Enumerated(Tipo)
│   │   │   │   └── Usuario.java                 # @Entity (futuro auth)
│   │   │   ├── repository/
│   │   │   │   ├── ProdutoRepository.java       # JpaRepository + queries custom
│   │   │   │   └── MovimentacaoRepository.java
│   │   │   └── service/
│   │   │       ├── ProdutoService.java          # Regras de negócio produto
│   │   │       └── MovimentacaoService.java     # Estoque: entrada/saida + validação saldo
│   │   └── resources/
│   │       ├── application.properties           # Config prod (Supabase Pooler)
│   │       └── templates/                       # Páginas HTML + Tailwind CDN
│   │           ├── index.html                   # Dashboard
│   │           ├── produtos.html                # CRUD Produtos
│   │           ├── movimentacoes.html           # Entrada/Saída
│   │           └── relatorios.html              # Relatório executivo + print
│   └── Tela_principal.java                      # Legado Swing (preservado)
└── README.md
```

---

## 🧪 Testes

### Rodar testes unitários
```bash
./mvnw test
```

### Cobertura de código (JaCoCo)
```bash
./mvnw verify jacoco:report
# Abra: target/site/jacoco/index.html
```

### Testes de integração (Testcontainers — opcional)
```bash
# Requer Docker rodando
./mvnw verify -Dspring.profiles.active=test
```

---

## 🔧 Troubleshooting

| Problema | Causa Provável | Solução |
|----------|----------------|---------|
| `ENOTFOUND tenant/user postgres.sua_referencia...` | Variáveis não configuradas no Render | Configure `SPRING_DATASOURCE_*` no painel do Render |
| `Connection refused` local | Supabase Pooler IP não liberado | Supabase Pooler não precisa de IP allowlist; verifique URL/porta 6543 |
| `prepareThreshold` erro | Falta `&prepareThreshold=0` na URL | Adicione o parâmetro na `SPRING_DATASOURCE_URL` |
| `OutOfMemory` no Render Free | Build Maven usa muita RAM | O `Dockerfile` já usa `-Xmx512m` no stage de build |
| Deploy travado em "Pulling image" | Docker Hub rate limit | Render usa cache próprio; aguarde ou faça push manual |
| CORS bloqueado no frontend | Origem não permitida | `CorsConfig.java` já libera `*`: verifique se redeployou |
| Porta 8080 ocupada local | Outro processo | `lsof -ti:8080 \| xargs kill -9` ou mude `SERVER_PORT` |

### Logs no Render
- Acesse o serviço → aba **Logs** → veja stdout/stderr em tempo real
- `Spring Boot` banner + `HikariCP` pool stats + SQL (se `spring.jpa.show-sql=true`)

---

## 🤝 Contribuição

Contribuições são bem-vindas! Siga o fluxo:

1. **Fork** o repositório
2. Crie uma branch: `git checkout -b feature/minha-feature`
3. Commit convencional: `git commit -m "feat: adiciona filtro por categoria"`
4. Push: `git push origin feature/minha-feature`
5. Abra um **Pull Request**

### Padrões do Projeto
- **Commits:** [Conventional Commits](https://www.conventionalcommits.org/) (`feat:`, `fix:`, `docs:`, `refactor:`, `test:`)
- **Code Style:** Google Java Format (configurado no `pom.xml`)
- **Branches:** `feature/*`, `fix/*`, `docs/*`, `refactor/*`
- **Testes:** Novas features = testes unitários + integração

---

## 📄 Licença

Este projeto está licenciado sob a **Licença MIT** — veja o arquivo [LICENSE](LICENSE) para detalhes.

```
MIT License

Copyright (c) 2025 Joaz Rodrigues

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

## 👨‍💻 Autor

**Joaz Rodrigues**  
🔗 [GitHub](https://github.com/joazrodrigues) • [LinkedIn](https://linkedin.com/in/joazrodrigues)

> Projeto migrado de **Java Swing Desktop** → **Spring Boot Web & Cloud Native**  
> Demonstrando: arquitetura limpa, cloud-native patterns, DevOps com Docker/Render, e boas práticas Java moderno.

---

⭐ **Se este projeto te ajudou, deixe uma estrela no GitHub!**  
📧 **Dúvidas ou sugestões? Abra uma [Issue](https://github.com/joazrodrigues/Projeto_Gerenciador_Estoque_Java/issues).**