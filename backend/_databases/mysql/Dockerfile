FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=admin

COPY ./scripts/init-db.sql /docker-entrypoint-initdb.d/

EXPOSE 3306