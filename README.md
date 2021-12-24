# library-management

### Steps to Run project

1. mvn clean install
2. Run LibraryApplication.main method
3. Visit http://localhost:8082/swagger-ui.html to see rest apis exposed
4. Visit http://localhost:8082/h2-console to see the h2 database


#### Apis Description
1. /book/add-book/ - To add book in the database
2. /book/view-books/ - To view books
3. /user/borrow-book/ - To borrow book, consumes json and produces json. Refer to UserResponse class for request payload and UserResponse class for response payload
4. /user/return-book/ - To return book, consumes json and produces json. Refer to UserResponse class for request payload and UserResponse class for response payload

#### Architecture thoughts
* controller package - Classes where controller classes are defined to expose rest apis
* entity package -  Package where entity class are defined. User to Books have many to many relationship
* exception package - User defined exception classes are defined
* payload.request - Package where payload requests are defined
* payload.response - Package where payload response are defined
* repository - JPA repository are defined for fetching data from database
* service - Service layer interfaces are defined and it's implementation. It connects controller and repository layer. Here core request processing code is written
* resources/data.sql - SQL script to load initial data on statup of application
* test/scenario - Integration tests for various scenarios
* test/resources/testData/ -  SQL scripts for integration tests

    