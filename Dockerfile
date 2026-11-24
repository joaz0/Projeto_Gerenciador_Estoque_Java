# ========================================================
# Estágio 1: Build da Aplicação com Maven
# ========================================================
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copia pom.xml e faz cache das dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e gera o pacote .jar sem executar testes
COPY src ./src
RUN mvn clean package -DskipTests

# ========================================================
# Estágio 2: Imagem Final de Execução (Leve e Segura)
# ========================================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Cria usuário não-root para segurança
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copia o jar compilado do estágio de build com permissões para o appuser
COPY --from=build --chown=appuser:appgroup /app/target/gerenciador-estoque-*.jar app.jar

# Variáveis de ambiente padrão
ENV PORT=8080
EXPOSE 8080

# Execução otimizada da JVM para containers
ENTRYPOINT ["sh", "-c", "java -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
