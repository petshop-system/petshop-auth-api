#makefile
APPLICATION_NAME := petshop-auth-api
PORT := 5004

docker-compose-up: docker-compose-down
	clear
	docker-compose rm -f -v postgres
	docker-compose up

docker-compose-down:
	docker-compose down -v

docker-compose-dev-up: # docker-compose-down
	docker-compose -f docker-compose-dev.yaml down -v
	docker-compose -f docker-compose-dev.yaml rm -f -v postgres
	docker-compose -f docker-compose-dev.yaml up

docker-build:
	docker build -t $(APPLICATION_NAME):latest .

docker-build-run:	docker-build docker-run

docker-run:
	docker run -p $(PORT):$(PORT) -t $(APPLICATION_NAME):latest

docker-clean-all:
	#To clear containers:
	docker rm -f $(docker ps -a -q) \
	#To clear images:
	docker rmi -f $(docker images -a -q) \
	#To clear volumes:
	docker volume rm $(docker volume ls -q) \
	#To clear networks:
	docker network rm $(docker network ls | tail -n+2 | awk '{if($2 !~ /bridge|none|host/){ print $1 }}')