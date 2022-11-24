
# Student-teacher CRUD
This a REST API for managing student-teacher relationship. It allows to perform: CRUD operations, add Students to Teachers 
and vice versa, filter, sort, paginate. 

## Technologies used for this project
- Java 17
- Maven
- Spring Boot 2.7.5
- Spring Data JPA (Hibernate)
- MySQL 8
- Lombok
- H2 Database (test scope In-Memory database)
- JUnit 5
- Mockito
- AssertJ

## How to run
### Requirements and configuration
- MySQL 8 database
- Postman
-  In Postman set  *Content-Type* header to *application/json-patch+json*

In MySQL create the database with the name "student_teacher", default name and password are set to "root" and "password" if you would like to change it, go to:
*src/main/resources/application.properties* and change these two fields: *spring.datasource.username* and *spring.datasource.password*.

## Usage example

Request:
**Method: *POST* url: *http://localhost:8080/api/v1/school/students***
Request Body:
```json
{
	"firstName": "John",
	"lastName": "Doe",
	"age": 21,
	"email": "johndoe@gmail.com",
	"fieldOfStudy": "Computer Science"
}
```
Response Header: Status 201 Created

Response Body:
```json
{
	"id": 1,
	"firstName": "John",
	"lastName": "Doe",
	"age": 21,
	"email": "johndoe@gmail.com",
	"fieldOfStudy": "Computer Science"
}
```
___
Request:
**Method: *POST* url: *http://localhost:8080/api/v1/school/teachers***

Request Body:
```json
{
	"firstName": "Jane",
	"lastName": "Nowak",
	"age": 41,
	"email": "janenowak@gmail.com",
	"subject": "Data Structures and Algorithms"
}
```
Response Header: Status 201 Created

Response Body:
```json
{
	"id": 1,
	"firstName": "Jane",
	"lastName": "Nowak",
	"age": 41,
	"email": "janenowak@gmail.com",
	"subject": "Data Structures and Algorithms"
}
``` 
___
Request:
**Method: *GET* url: *http://localhost:8080/api/v1/school/students?page=0&size=2&sort=age***

Response Body:
```json
{
  "content": [
    {
      "id": 3,
      "firstName": "Mariola",
      "lastName": "Nowakowa",
      "age": 19,
      "email": "mariola@gmail.com",
      "fieldOfStudy": "Physics"
    },
    {
      "id": 1,
      "firstName": "Jan",
      "lastName": "Kowalski",
      "age": 21,
      "email": "jankowalski@gmail.com",
      "fieldOfStudy": "Math"
    }
  ],
  "pageable": {
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 2,
    "paged": true,
    "unpaged": false
  },
  "last": false,
  "totalElements": 6,
  "totalPages": 3,
  "size": 2,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "first": true,
  "numberOfElements": 2,
  "empty": false
}
```
___
Request:
**Method: *PATCH* url: *http://localhost:8080/api/v1/school/teachers/1/student?student-id=1&op=add***

Response Header: Status 200 OK

Response Body:
```json
"Student list for teacher with id: 3 updated successfully,operation: add"
```
___
Request:
**Method: *PATCH* url: *http://localhost:8080/api/v1/school/teachers/1/student?student-id=1&op=remove***

Response Header: Status 200 OK

Response Body:
```json
"Student list for teacher with id: 3 updated successfully,operation: remove"
```
___
Request:
**Method: *PATCH* url: *http://localhost:8080/api/v1/school/teachers/1***

Request Body:
```json
[
	{
		"op": "replace",
		"path": "/email",
		"value": "nowakjane@gmail.com"
	}
]
``` 
Response Header: Status 200 OK

Response Body:
```json
"Person with id 1 updated successfully"
```
___
Request:
**Method: *GET* url: *http://localhost:8080/api/v1/school/students?first-name=John&last-name=Doe***

Response Header: Status 200 OK

Response Body:
```json
{
	"firstName": "John",
	"lastName": "Doe",
	"persons": [
		{
			"id": 1,
			"firstName": "John",
			"lastName": "Doe",
			"age": 21,
			"email": "johndoe@gmail.com",
			"fieldOfStudy": "Computer Science"
		}
	]
}
```
