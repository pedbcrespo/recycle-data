# 1️⃣ Etapa de build
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copia o POM e as dependências para cache de build
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e compila o JAR
COPY src ./src
RUN mvn clean package -DskipTests

# 2️⃣ Etapa final (imagem leve)
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia o JAR gerado da etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Copia o script wait-for-it
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Variáveis de ambiente (podem vir do docker-compose)
ENV MYSQL_HOST=mysql
ENV MYSQL_PORT=3306
ENV MONGO_HOST=mongodb
ENV MONGO_PORT=27017

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# 3️⃣ EntryPoint: espera os bancos antes de iniciar a app
ENTRYPOINT ["/wait-for-it.sh", "mysql", "3306", "--", "/wait-for-it.sh", "mongodb", "27017", "--", "java", "-jar", "app.jar"]
