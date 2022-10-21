FROM mysql:8.0.30
ENV MYSQL_ROOT_PASSWORD admin
ENV MYSQL_DATABASE messengerdb
COPY ./schema.sql /docker-entrypoint-initdb.d/1-schema.sql
COPY ./data.sql /docker-entrypoint-initdb.d/2-test-data.sql
EXPOSE 3306 33060
