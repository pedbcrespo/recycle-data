# 1️⃣ Etapa de build (Builder Stage)
# Usa o Maven para compilar e empacotar a aplicação
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copia o POM e baixa as dependências para aproveitar o cache do Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e compila o JAR
COPY src ./src
RUN mvn clean package -DskipTests

---

# 2️⃣ Etapa final (Final/Runtime Stage)
# Usa uma imagem JRE leve para o runtime
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia o JAR gerado da etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Removemos:
# - A cópia e permissão do wait-for-it.sh (não é mais necessário no Cloud Run)
# - As variáveis ENV de configuração do banco de dados local (serão fornecidas pelo Cloud Run)

# Variáveis de ambiente
# Deixamos o Cloud Run definir todas as configurações de host e credenciais.

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# 3️⃣ EntryPoint: Inicia a aplicação diretamente
# O Cloud Run garantirá a disponibilidade das conexões e variáveis de ambiente
ENTRYPOINT ["java", "-jar", "app.jar"]