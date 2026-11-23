# 📦 Sistema de Gestão de Estoque Web & REST API

> Aplicação moderna de controle de estoque migrada de Java Swing para **Spring Boot 3**, com banco de dados **PostgreSQL no Supabase** e deploy automatizado em nuvem via **Docker no Render**.

---

## 🚀 Arquitetura da Solução

- **Backend:** Java 17 + Spring Boot 3 (Spring Web, Spring Data JPA, Bean Validation, HikariCP)
- **Banco de Dados:** PostgreSQL hospedado no [Supabase](https://supabase.com) utilizando o **Transaction / Session Pooler (porta 6543)**
- **Frontend Embutido:** Dashboard responsivo (HTML5, Tailwind CSS, FontAwesome e Fetch API) servido diretamente pelo Spring Boot
- **Deploy:** Container Docker multi-stage hospedado gratuitamente no [Render](https://render.com)
- **API REST:** Endpoints prontos para consumo por qualquer client (React, Vue, Mobile ou Postman)

---

## 🗄️ 1. Configuração do Banco de Dados no Supabase

### 1.1 Criar as Tabelas
1. Acesse o dashboard do seu projeto no [Supabase](https://supabase.com).
2. No menu lateral, clique em **SQL Editor**.
3. Abra o arquivo [`database/schema.sql`](file:///C:/Users/joazr/OneDrive/Documentos/Projetos/Projeto_Gerenciador_Estoque_Java/database/schema.sql), copie o conteúdo, cole no editor do Supabase e clique em **Run**.
4. O script criará as tabelas `usuarios`, `produtos`, `movimentacoes`, índices e dados iniciais de teste.

### 1.2 Obter a String de Conexão com Connection Pooler (Porta 6543)
Para evitar o esgotamento do limite de conexões no plano gratuito do Supabase, use sempre o **Transaction Pooler**:
1. No Supabase, vá em **Project Settings > Database**.
2. Na seção **Connection string**, selecione a aba **URI** e o modo **Transaction** (porta **6543**).
3. A URL terá o formato:
   ```text
   jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:6543/postgres?sslmode=require
   ```
4. O usuário do pooler terá o formato `postgres.[ref-do-projeto]`.

---

## ☁️ 2. Deploy no Render (via Docker)

O Render não possui runtime nativo para Java no plano gratuito, mas suporta containers **Docker** com deploy automático a partir do seu repositório Git!

### Passo a Passo no Render:
1. Suba este projeto para um repositório no **GitHub** ou **GitLab**.
2. Acesse o [Render Dashboard](https://dashboard.render.com) e clique em **New + > Web Service**.
3. Conecte o repositório do projeto.
4. Preencha as configurações:
   - **Name:** `gerenciador-estoque`
   - **Environment:** `Docker`
   - **Region:** Selecione a mais próxima (ex: `Ohio` ou `Oregon`)
   - **Branch:** `main` (ou `master`)
   - **Instance Type:** `Free`
5. Na seção **Environment Variables**, adicione as seguintes variáveis:
   | Chave | Valor (Exemplo) |
   |---|---|
   | `SPRING_DATASOURCE_URL` | `jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:6543/postgres?sslmode=require` |
   | `SPRING_DATASOURCE_USERNAME` | `postgres.sua_referencia_de_projeto` |
   | `SPRING_DATASOURCE_PASSWORD` | `sua_senha_do_supabase` |
6. Clique em **Create Web Service**.
7. O Render executará o `Dockerfile` multi-stage (compilação Maven + imagem leve Alpine JRE) e disponibilizará sua aplicação em uma URL pública (ex: `https://gerenciador-estoque.onrender.com`).

---

## 💻 3. Execução Local

### Opção A: Executando via Docker
```bash
docker build -t gerenciador-estoque .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="sua_url_pooler_supabase" \
  -e SPRING_DATASOURCE_USERNAME="seu_usuario" \
  -e SPRING_DATASOURCE_PASSWORD="sua_senha" \
  gerenciador-estoque
```
Acesse no navegador: `http://localhost:8080`

### Opção B: Executando com Maven localmente
```bash
mvn spring-boot:run
```

---

## 🖥️ 4. Interface Web Embutida

Ao rodar a aplicação, as seguintes páginas estão disponíveis:
- `/` - **Dashboard:** Indicadores em tempo real, valor total do patrimônio em estoque, alertas de reposição e últimas atividades.
- `/produtos` - **Catálogo:** Cadastro de produtos (substituindo a antiga `Tela_principal.java`), edição de preços/descrições, pesquisa em tempo real e exclusão.
- `/movimentacoes` - **Operações:** Registro de Entradas (reposição) e Saídas (baixa/vendas) com validação de saldo para impedir estoque negativo.
- `/relatorios` - **Relatórios:** Relatório executivo consolidado com botão para impressão / exportação em PDF.

---

## 📡 5. Documentação da API REST

A API possui suporte nativo a CORS e pode ser consumida por qualquer cliente externo.

### 5.1 Produtos (`/api/produtos`)
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/produtos` | Lista todos os produtos (filtro opcional: `?busca=nome`) |
| `GET` | `/api/produtos/{id}` | Busca um produto específico por ID |
| `POST` | `/api/produtos` | Cadastra um novo produto |
| `PUT` | `/api/produtos/{id}` | Atualiza nome, preço, quantidade ou descrição |
| `DELETE` | `/api/produtos/{id}` | Remove o produto do catálogo |

#### Exemplo de Payload (POST / PUT):
```json
{
  "nome": "Monitor UltraWide 29 Pol",
  "descricao": "Monitor IPS 75Hz com portas HDMI e DisplayPort",
  "preco": 1150.00,
  "quantidadeEstoque": 10
}
```

---

### 5.2 Movimentações (`/api/movimentacoes`)
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/movimentacoes` | Histórico completo de entradas e saídas ordenado por data |
| `POST` | `/api/movimentacoes/entrada` | Registra entrada de mercadoria e incrementa o saldo |
| `POST` | `/api/movimentacoes/saida` | Registra baixa de estoque (retorna erro 400 se saldo insuficiente) |
| `GET` | `/api/movimentacoes/relatorio` | Resumo de métricas (total produtos, unidades, valor financeiro, itens críticos) |

#### Exemplo de Entrada / Saída:
```json
{
  "produtoId": 1,
  "quantidade": 5,
  "motivo": "Venda balcão - Pedido #1024"
}
```

---

## 📂 Estrutura de Pastas do Projeto

```text
Projeto_Gerenciador_Estoque_Java/
├── Dockerfile                         # Build multi-stage e runtime da aplicação
├── .dockerignore
├── render.yaml                        # Blueprint de configuração do Render
├── pom.xml                            # Configurações do Maven e dependências
├── database/
│   └── schema.sql                     # Script DDL e seeds para o Supabase PostgreSQL
├── src/
│   ├── main/
│   │   ├── java/com/estoque/
│   │   │   ├── EstoqueApplication.java
│   │   │   ├── config/                # Configurações globais (CORS)
│   │   │   ├── controller/            # REST Controllers e Web Controller
│   │   │   ├── dto/                   # Data Transfer Objects com validações
│   │   │   ├── model/                 # Entidades JPA (Produto, Movimentacao, Usuario)
│   │   │   ├── repository/            # Interfaces Spring Data JPA
│   │   │   └── service/               # Regras de negócio de estoque
│   │   └── resources/
│   │       ├── application.properties # Parâmetros do Supabase Pooler e Render
│   │       └── templates/             # Telas HTML/Tailwind CSS
│   │           ├── index.html         # Dashboard
│   │           ├── produtos.html      # Cadastro e Edição
│   │           ├── movimentacoes.html # Entrada e Saída
│   │           └── relatorios.html    # Relatórios
│   └── Tela_principal.java            # Versão legado Swing preservada
└── README.md
```

---
Desenvolvido por **Joaz Rodrigues** &bull; Migrado para Arquitetura Web & Cloud.
