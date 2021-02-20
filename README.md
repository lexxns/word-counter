# synalogik-test project

This project uses Quarkus https://quarkus.io/ as a framework
and utilises redis https://redis.io/ as a data-store

## Local Standup
Create a redis container & run App:
```shell script
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name redis_quarkus_test -p 6379:6379 redis:5.0.6
./mvnw clean compile quarkus:dev
```

## Upload text
POST text to http://localhost:8080/files to add the text file to the data-store e.g.
```shell script
curl -X POST --data 'Hello World' http://localhost:8080/files
``` 
This will return an id for that text file.

## Read details of text document
set a GET request to http://localhost:8080/files/{fileId} e.g.
```
curl http://localhost:8080/files/c5d0805a-9535-45db-bda8-0ae09a8b8815
``` 
This will return details of word length etc formatted in the form:
```
Word count = 9
Average word length = 4.556
Number of words of length 1 is 1
Number of words of length 2 is 1
Number of words of length 3 is 1
Number of words of length 4 is 2
Number of words of length 5 is 2
Number of words of length 7 is 1
Number of words of length 10 is 1
The most frequently occurring word length is 2, for word lengths of [4, 5]
```
