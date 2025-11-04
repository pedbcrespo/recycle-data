# Etapa 1: construir o jar com Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml e baixa dependências antes do código-fonte (para aproveitar cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e gera o jar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: imagem final e leve para execução
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o jar da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080 (obrigatório no Cloud Run)
EXPOSE 8080

# Define a variável PORT e garante que o Spring use ela
ENV PORT=8080
ENV JAVA_OPTS=""

# Inicia a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
