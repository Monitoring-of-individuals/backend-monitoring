# backend-monitoring

## Локальная разработка в контейнерах

- Для начала необходимо создать файл infra/.env, значения для него можно взять из infra/env.example. В этом файле необходимо будет добавить значения для недостающих переменных.

```
mvn -B package --file pom.xml
```
- Затем запустить все контейнеры командой:
```
docker compose -f infra/docker-compose-local.yml up -d
```
- Api будет доступен по адресу http://localhost/api и http://localhost/auth
- В postman необходимо обращаться к хосту http://localhost

## Полезные команды:
- Просмотр запущенных контейнеров:
```
docker ps
```
- Просмотр логов контейнеров (необходимо находиться в корневой директории проекта):
```
docker compose -f infra/docker-compose-local.yml logs
```
## Запуск контейнеров с фронтендом
Есть возможность посмотреть как выглядит фронтенд. Для этого нужно сделать несколько шагов:
- В корне проекта выполнить команду инициализации сабмодуля
```
git submodule init frontend/
```
- Затем необходимо скачать последнюю версию из репозитория фронтенда:
```
git submodule update --remote --recursive
```
- Переключаемся в ветки main на ветку Develop, команду необходимо выполнять из корневой директории:
```
cd frontend/ && git checkout Develop && cd ..
```
- Запускаем контейнеры:
```
docker compose -f infra/docker-compose-local-with-front.yml up -d
```
Фронтенд будет доступен по URL: http://localhost

