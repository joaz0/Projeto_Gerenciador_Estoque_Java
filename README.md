<div align="center">

<!-- Banner estilo terminal -->
<img src="https://capsule-render.vercel.app/api?type=waving&color=0:0d1117,50:161b22,100:00ff41&height=180&section=header&text=Gerenciador%20de%20Estoque&fontSize=38&fontColor=00ff41&fontAlignY=38&desc=Spring%20Boot%203%20%7C%20REST%20API%20%7C%20PostgreSQL%20%7C%20Docker&descAlignY=62&descColor=c9d1d9" width="100%" />

[![Typing SVG](https://readme-typing-svg.demolab.com?font=Fira+Code&weight=600&size=18&pause=1000&color=00FF41&center=true&vCenter=true&width=680&lines=Migrado+de+Java+Swing+para+Spring+Boot+3+%26+Cloud+Native;API+REST+completa+%2B+Dashboard+Web+embutido;PostgreSQL+(Supabase)+%2B+Deploy+Docker+no+Render)](https://git.io/typing-svg)

<br/>

<!-- Stack & Status Badges (Tema Terminal) -->
[![Java](https://img.shields.io/badge/Java-17-0D1117?style=flat-square&logo=openjdk&logoColor=00FF41)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-0D1117?style=flat-square&logo=spring-boot&logoColor=00FF41)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-0D1117?style=flat-square&logo=postgresql&logoColor=00FF41)](https://www.postgresql.org/)
[![Supabase](https://img.shields.io/badge/Supabase-Pooler-0D1117?style=flat-square&logo=supabase&logoColor=00FF41)](https://supabase.com/)
[![Docker](https://img.shields.io/badge/Docker-Multi--stage-0D1117?style=flat-square&logo=docker&logoColor=00FF41)](https://www.docker.com/)
[![Render](https://img.shields.io/badge/Render-Deploy-0D1117?style=flat-square&logo=render&logoColor=00FF41)](https://render.com/)
[![Maven](https://img.shields.io/badge/Maven-3.9-0D1117?style=flat-square&logo=apache-maven&logoColor=00FF41)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-0D1117?style=flat-square&color=00FF41)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Active-0D1117?style=flat-square&color=00FF41)]()

</div>

---

### `> cat ./indice.md`

- [Visão Geral](#-visão-geral)
- [Arquitetura da Solução](#-arquitetura-da-solução)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração do Banco de Dados (Supabase)](#️-configuração-do-banco-de-dados-no-supabase)
- [Variáveis de Ambiente](#-variáveis-de-ambiente)
- [Deploy no Render via Docker](#️-deploy-no-render-via-docker--gratuito-)
- [Execução Local](#-execução-local)
- [Interface Web Embutida](#️-interface-web-embutida)
- [Documentação da API REST](#-documentação-da-api-rest)
- [Estrutura de Pastas](#-estrutura-de-pastas-do-projeto)
- [Testes](#-testes)
- [Troubleshooting](#-troubleshooting)
- [Contribuição](#-contribuição)
- [Licença](#-licença)
- [Autor](#-autor)

---

### `> cat ./visao_geral.md`

#### 🎯 Visão Geral

Este projeto é um **Sistema de Gestão de Estoque** completo, desenvolvido com arquitetura moderna e pronta para produção. Originalmente uma aplicação desktop em Java Swing, foi completamente reescrita para **Spring Boot 3** com:

```text
[✔] API REST completa para integração com qualquer frontend
[✔] Dashboard Web responsivo embutido (HTML5 + Tailwind CSS)
[✔] Banco de dados na nuvem (PostgreSQL no Supabase)
[✔] Deploy automatizado via Docker no Render (plano gratuito)
[✔] Validações robustas (Bean Validation + regras de negócio)
[✔] Pool de conexões otimizado para ambiente serverless (HikariCP + PgBouncer)
```

---

### `> cat ./arquitetura.table`

#### 🚀 Arquitetura da Solução

| Camada | Tecnologia | Descrição |
| :--- | :--- | :--- |
| **Linguagem** | `Java 17 LTS` | Versão de longo suporte com performance otimizada |
| **Framework** | `Spring Boot 3.2+` | Auto-configuração, starter web, data JPA, validation |
| **Banco de Dados** | `PostgreSQL 15` | Gerenciado no Supabase, com connection pooler nativo |
| **ORM** | `Spring Data JPA` | Repositórios tipados, Hibernate e queries derivadas |
| **Pool de Conexões** | `HikariCP` | Configurado para Transaction Pooler (porta 6543) |
| **Frontend** | `HTML5 + Tailwind` | Servido estaticamente pelo Spring Boot + Fetch API |
| **Build** | `Maven 3.9+` | Multi-module ready, dependency management |
| **Container** | `Docker Multi-stage` | Build Maven → Runtime Alpine JRE (~80MB) |
| **Cloud & CI/CD** | `Render (Free Tier)` | Deploy automático via Git (`main`), SSL grátis |

---

### `> ./check_prereqs.sh`

#### 📦 Pré-requisitos

<table>
  <tr>
    <td valign="top" width="50%">
      <h4>💻 Desenvolvimento Local</h4>
      <ul>
        <li>☕ <strong>Java 17+</strong> (JDK) — <a href="https://adoptium.net/temurin/releases/?version=17">Download</a></li>
        <li>📦 <strong>Maven 3.9+</strong> — <a href="https://maven.apache.org/download.cgi">Download</a> ou <code>./mvnw</code></li>
        <li>🐳 <strong>Docker 24+</strong> (opcional) — <a href="https://www.docker.com/get-started">Download</a></li>
        <li>🗄️ <strong>Conta Supabase</strong> (gratuita) — <a href="https://supabase.com/">Criar</a></li>
        <li>☁️ <strong>Conta Render</strong> (gratuita) — <a href="https://render.com/">Criar</a></li>
      </ul>
    </td>
    <td valign="top" width="50%">
      <h4>🐳 Apenas Container</h4>
      <ul>
        <li>🐳 <strong>Docker 24+</strong> instalado e em execução</li>
        <li>🔑 Credenciais do banco PostgreSQL (Supabase)</li>
      </ul>
    </td>
  </tr>
</table>

---

### `> psql --setup-supabase`

#### 🗄️ Configuração do Banco de Dados no Supabase

##### 1.1 Criar as Tabelas
1. Acesse o dashboard do seu projeto no [Supabase](https://supabase.com)
2. No menu lateral, clique em **SQL Editor**
3. Abra o arquivo [`database/schema.sql`](database/schema.sql), copie o conteúdo, cole no editor e clique em **Run**
4. O script criará:
   - Tabelas: `usuarios`, `produtos`, `movimentacoes`
   - Índices otimizados para consultas frequentes
   - Dados iniciais de teste (usuário admin, produtos exemplo)

##### 1.2 Obter a String de Conexão com Connection Pooler (Porta 6543) ⚡
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

### `> env | grep SPRING_`

#### ⚙️ Variáveis de Ambiente

| Variável | Obrigatória | Descrição | Exemplo |
| :--- | :---: | :--- | :--- |
| `SPRING_DATASOURCE_URL` | ✅ Sim | JDBC URL do Supabase Pooler | `jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:6543/postgres?sslmode=require&prepareThreshold=0` |
| `SPRING_DATASOURCE_USERNAME` | ✅ Sim | Usuário do pooler | `postgres.abcdefghijklmnop` |
| `SPRING_DATASOURCE_PASSWORD` | ✅ Sim | Senha do banco (Settings → Database) | `sua_senha_forte_aqui` |
| `SPRING_PROFILES_ACTIVE` | Não | Perfil ativo (`prod`, `dev`) | `prod` |
| `SERVER_PORT` | Não | Porta HTTP (Render injeta `PORT`) | `8080` |

> 💡 **Dica:** No Render, configure essas variáveis na aba **Environment** do Web Service. **Nunca** commite credenciais no código!

---

### `> ./deploy_render.sh`

#### ☁️ Deploy no Render (via Docker) — Gratuito 🎉

O Render não tem runtime nativo Java no plano gratuito, mas **suporta Docker** com deploy automático via Git.

1. **Suba o código** para um repositório no **GitHub** ou **GitLab**
2. Acesse o [Render Dashboard](https://dashboard.render.com) → **New + → Web Service**
3. Conecte o repositório do projeto
4. Configure os parâmetros:

| Campo | Valor |
| :--- | :--- |
| **Name** | `gerenciador-estoque` (ou seu nome) |
| **Environment** | `Docker` |
| **Region** | Mais próxima (ex: `Ohio (US East)`, `Oregon (US West)`) |
| **Branch** | `main` (ou `master`) |
| **Instance Type** | `Free` |

5. **Variáveis de Ambiente** (aba **Environment**): adicione as 3 variáveis obrigatórias da tabela anterior
6. Clique em **Create Web Service**
7. O Render executará o `Dockerfile` multi-stage e disponibilizará em: `https://<seu-nome>.onrender.com`

> ⚠️ **Erro Comum — LEIA ISSO:** Se não configurar as variáveis no Render, o Spring Boot usará os valores de exemplo do `application.properties` e falhará com `PSQLException: FATAL: (ENOTFOUND) tenant/user postgres.sua_referencia_de_projeto not found`. Configure **todas as 3 variáveis** antes do primeiro deploy.

**`render.yaml` (Opcional — Blueprint as Code):**
```bash
render deploy --confirm
```

---

### `> ./run_local.sh`

#### 💻 Execução Local

##### Opção A: Docker (Recomendado — ambiente idêntico ao prod)
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

##### Opção B: Maven (Desenvolvimento rápido)
```bash
# Com wrapper (não precisa instalar Maven)
./mvnw spring-boot:run

# Ou com Maven instalado
mvn spring-boot:run
```
> Configure as variáveis de ambiente no seu IDE ou no `application-local.properties` (gitignored).

##### Opção C: Perfil de Desenvolvimento (H2 em memória)
```bash
# Crie application-dev.properties com H2 e rode:
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

---

### `> ls ./web_routes`

#### 🖥️ Interface Web Embutida

Todas as páginas são **Server-Side Rendered** (templates estáticos servidos diretamente pelo Spring Boot) com **Tailwind CSS**, **FontAwesome 6**, **Fetch API** nativa e design responsivo/acessível.

| Rota | Página | Funcionalidades |
| :--- | :--- | :--- |
| `/` | **Dashboard** | KPIs em tempo real, valor total em estoque, alertas de reposição, últimas 10 movimentações |
| `/produtos` | **Catálogo** | CRUD completo, busca em tempo real, validação de preço/quantidade, paginação |
| `/movimentacoes` | **Operações** | Entrada (reposição) + Saída (venda/baixa) com validação de saldo negativo |
| `/relatorios` | **Relatórios** | Resumo executivo, valor financeiro, itens críticos, **impressão/PDF** |

---

### `> curl -i /api/docs`

#### 📡 Documentação da API REST

Base URL: `https://<seu-app>.onrender.com/api` ou `http://localhost:8080/api`  
✅ **CORS habilitado** — consumível por qualquer origem (React, Vue, Mobile, Postman).

##### 5.1 Produtos (`/api/produtos`)

| Método | Endpoint | Descrição | Status Codes |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/produtos` | Lista todos (filtro: `?busca=nome`) | `200` |
| `GET` | `/api/produtos/{id}` | Busca por ID | `200`, `404` |
| `POST` | `/api/produtos` | Cadastra novo | `201`, `400` |
| `PUT` | `/api/produtos/{id}` | Atualiza campos | `200`, `400`, `404` |
| `DELETE` | `/api/produtos/{id}` | Remove produto | `204`, `404` |

```json
// Payload (POST / PUT)
// Validações: nome (3-100 chars), preco (>= 0.01), quantidadeEstoque (>= 0), descricao (<= 500 chars)
{
  "nome": "Monitor UltraWide 29 Pol",
  "descricao": "Monitor IPS 75Hz com portas HDMI e DisplayPort",
  "preco": 1150.00,
  "quantidadeEstoque": 10
}
```

##### 5.2 Movimentações (`/api/movimentacoes`)

| Método | Endpoint | Descrição | Status Codes |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/movimentacoes` | Histórico completo (ordenado por data desc) | `200` |
| `POST` | `/api/movimentacoes/entrada` | Registra entrada → incrementa estoque | `201`, `400`, `404` |
| `POST` | `/api/movimentacoes/saida` | Registra saída → decrementa estoque (bloqueia se insuficiente) | `201`, `400`, `404` |
| `GET` | `/api/movimentacoes/relatorio` | Métricas consolidadas | `200` |

```json
// Payload (Entrada / Saída)
// Validações: produtoId existente, quantidade >= 1, motivo (3-200 chars)
// Saída retorna 400 Bad Request se quantidade > saldo_atual
{
  "produtoId": 1,
  "quantidade": 5,
  "motivo": "Venda balcão - Pedido #1024"
}
```

##### 5.3 Exemplos cURL

```bash
# Listar e buscar produtos
curl https://gerenciador-estoque.onrender.com/api/produtos
curl https://gerenciador-estoque.onrender.com/api/produtos/1

# Criar produto
curl -X POST https://gerenciador-estoque.onrender.com/api/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome":"Teclado Mecânico","preco":350,"quantidadeEstoque":15}'

# Registrar entrada e saída
curl -X POST https://gerenciador-estoque.onrender.com/api/movimentacoes/entrada \
  -H "Content-Type: application/json" \
  -d '{"produtoId":1,"quantidade":10,"motivo":"Recebimento NF #123"}'

curl -X POST https://gerenciador-estoque.onrender.com/api/movimentacoes/saida \
  -H "Content-Type: application/json" \
  -d '{"produtoId":1,"quantidade":2,"motivo":"Venda online"}'

# Relatório consolidado
curl https://gerenciador-estoque.onrender.com/api/movimentacoes/relatorio
```

---

### `> tree -L 4`

#### 📂 Estrutura de Pastas do Projeto

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

### `> ./mvnw test`

#### 🧪 Testes

```bash
# Testes unitários
./mvnw test

# Cobertura de código (JaCoCo → target/site/jacoco/index.html)
./mvnw verify jacoco:report

# Testes de integração (Testcontainers — requer Docker rodando)
./mvnw verify -Dspring.profiles.active=test
```

---

### `> tail -f /var/log/troubleshooting.log`

#### 🔧 Troubleshooting

| Problema | Causa Provável | Solução |
| :--- | :--- | :--- |
| `ENOTFOUND tenant/user postgres.sua_referencia...` | Variáveis não configuradas no Render | Configure `SPRING_DATASOURCE_*` no painel do Render |
| `Connection refused` local | URL ou porta incorreta | Verifique URL/porta `6543` do Supabase Pooler |
| `prepareThreshold` erro | Falta `&prepareThreshold=0` na URL | Adicione o parâmetro na `SPRING_DATASOURCE_URL` |
| `OutOfMemory` no Render Free | Build Maven usa muita RAM | O `Dockerfile` já usa `-Xmx512m` no stage de build |
| Deploy travado em "Pulling image" | Docker Hub rate limit | Render usa cache próprio; aguarde ou faça push manual |
| CORS bloqueado no frontend | Origem não permitida | `CorsConfig.java` já libera `*`; verifique se redeployou |
| Porta 8080 ocupada local | Outro processo usando a porta | `lsof -ti:8080 \| xargs kill -9` ou mude `SERVER_PORT` |

> 📋 **Logs no Render:** Acesse o serviço → aba **Logs** para acompanhar stdout/stderr, banner do Spring Boot e estatísticas do pool HikariCP em tempo real.

---

### `> git checkout -b feature/sua-melhoria`

#### 🤝 Contribuição & Padrões

1. **Fork** o repositório e crie uma branch (`git checkout -b feature/minha-feature`)
2. Siga **Conventional Commits** (`feat:`, `fix:`, `docs:`, `refactor:`, `test:`) e **Google Java Format** (`pom.xml`)
3. Adicione testes unitários/integração para novas features e abra um **Pull Request**

---

### `> cat ./LICENSE`

#### 📄 Licença

Distribuído sob a **Licença MIT** — veja [`LICENSE`](LICENSE) para mais detalhes.  
`Copyright (c) 2025 Joaz Rodrigues`

---

### `> whoami`

<div align="center">

**Joaz Rodrigues**  
*Migrado de Java Swing Desktop → Spring Boot Web & Cloud Native*

[![GitHub](https://img.shields.io/badge/GitHub-joaz0-0D1117?style=for-the-badge&logo=github&logoColor=00FF41)](https://github.com/joaz0)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Joaz%20Rodrigues-0D1117?style=for-the-badge&logo=linkedin&logoColor=00FF41)](https://linkedin.com/in/joaz-rodrigues)
[![Issues](https://img.shields.io/badge/Reportar%20Bug-Issues-0D1117?style=for-the-badge&logo=github&logoColor=00FF41)](https://github.com/joaz0/Projeto_Gerenciador_Estoque_Java/issues)

<br/>

⭐ *Se este projeto te ajudou, deixe uma estrela no repositório!*

<img src="https://capsule-render.vercel.app/api?type=waving&color=0:0d1117,50:161b22,100:00ff41&height=95&section=footer" width="100%" />

</div>