# backend-monitoring

## Локальная разработка в контейнерах
- Перейти в корневую директорию проекта, выполнить команду сборки:
```
mvn -B package --file pom.xml
```
- Затем запустить все контейнеры командой:
```
docker compose -f infra/docker-compose-local.yml up -d
```
- Api будет доступен по адресу http://localhost/api и http://localhost/api

## Полезные команды:
- Просмотр запущенных контейнеров:
```
docker ps
```
- Просмотр логов контейнеров (необходимо находиться в корневой директории проекта):
```
docker compose -f infra/docker-compose-local.yml logs
```
