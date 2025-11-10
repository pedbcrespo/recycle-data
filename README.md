# Para subir o projeto local, tem duas formas:
## Subindo localmente SEM usar o Docker:
Tera que subir antes da aplicação, o servidor do mysql. Dependendo de como esta configurado o nome de usuario, senha e database, tera de alterar os valores no arquivo .env.
Esse arquivo é utilizado apenas em ambiente local.

## Subindo pelo Docker
Antes de subir os containers, é necessario desligar os servidores do mysql e do mongodb.
Depois apenas execute, dentro da pasta do projeto:
IMPORTANTE: esse comando só é executado na primeira vez, pois vai construir os containers
```bash
docker compose up --build
```

depois de rodar a primeira vez, para rodar localmente basta executar
```bash
docker compose up
```

## IMPORTANTE
O MONGO DB ESTA INSTALADO POREM DESATIVADO!
para ativa-lo, troque o 
"@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})" por 
"@SpringBootApplication"
na classe RecycleDataApplication.java