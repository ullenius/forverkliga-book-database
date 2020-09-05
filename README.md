# :books: Book Database API Backend

A reverse-engineered Java :coffee: version of the Book Database API.
The [original](https://www.forverkliga.se/JavaScript/api/crud.php) was written 
by David Andersson using PHP.

This is an intentionally poorly designed API that simulates a Book Database. 
Its purpose is for client-side developers to practice working with a flaky and
badly designed API.

**This is the server code** For the client-web app 
see [book-api-js](https://github.com/ullenius/book-api-js).

## :construction_worker: Installation

This is a Maven project.

To build the project run:

~~~sh
./mvnw install target
~~~

## :pushpin: API Summary
1. The API has a high likelihood of failing. On every operation.
2. The API returns `HTTP 200` on every operation
3. The API returns a `JSON` but the MIME-type is erroneously set to `text/html`.

4. The API accepts only query-parameters.
5. Only `GET` is used.

## :information_source: Technical API Documentation

Text by David Andersson.

This API simulates a book database. There are 4 operations available:

1. Add data to database
2. View data in database
3. Modify data in database
4. Delete data

## :key: API key

In order to use the database you need an API key. Request a key by an `GET` 
request with `requestKey` in the query string. You must use that key in all 
subsequent requests.

Example: `localhost:8080/?requestKey`

## :blue_book: Add data

Add book information to the database. Query string parameters:

    op=insert
    key - an API key that identifies the request
    title - the book title
    author - the name of the author

This request will output a `JSON` object of the following form if successful:
~~~
{
	"status": "success",
	"id": an id generated for the inserted data
}
~~~

## :eyeglasses: View data

Get all book information in database. Query string parameters:

    op=select
    key - an API key that identifies the request

This request will output a `JSON` object of the following form if successful:
~~~
{
	"status": "success",
	"data": [{
		"id": a unique id that identifies a book,
		"title": book title,
		"author": author name,
		"updated": when the data was last updated
	}]
}
~~~
## :pencil: Modify data

Change the entry for a specific book. Query string parameters:

    op=update
    key - an API key that identifies the request
    id - identifies what book you want to update
    title - new title
    author - new author

This request will output a `JSON` object of the following form if successful:
~~~json
{
	"status": "success"
}
~~~
## :skull: Delete data

Delete the information for a specific book in the database. Query string 
parameters:

    op=delete
    key - an API key that identifies the request
    id - identifies what book you want to remove

This request will output a `JSON` object of the following form if successful:
~~~json
{
	"status": "success"
}
~~~
## :poop: Errors

Every operation may fail! If an error occurs, the request will output a `JSON` 
object that describes the error:
~~~
{
	"status": "error",
	"message": a descriptive message
}
~~~
