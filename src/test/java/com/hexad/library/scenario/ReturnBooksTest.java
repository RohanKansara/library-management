package com.hexad.library.scenario;

import com.hexad.library.entity.Book;
import com.hexad.library.entity.BookRequest;
import com.hexad.library.payload.request.UserBookRequest;
import com.hexad.library.payload.response.UserResponse;
import com.hexad.library.repository.BookRepository;
import com.hexad.library.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:testData/return-books.sql")
public class ReturnBooksTest {

    @LocalServerPort
    int port;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testReturnOneBookToTheLibrary() {
        RequestSpecification request = RestAssured.given();

        request.header("Content-Type", "application/json");

        List<BookRequest> bookRequestList = Collections.singletonList(createBookRequest("Finance", "ABC"));

        UserBookRequest userBookRequest = createUserBookRequest("Rohan", bookRequestList);

        String bookName = userBookRequest.getBookRequestList().get(0).getName();

        String isbn = userBookRequest.getBookRequestList().get(0).getIsbn();

        Book book = bookRepository.findByNameAndIsbn(bookName, isbn);

        request.body(userBookRequest);

        Response response = request.post("/user/return-book");

        assertEquals(200, response.getStatusCode());

        UserResponse userResponse = response.getBody().as(UserResponse.class);

        String expectedStatus = "Book removed from user list and updated its stock in book";

        String actualStatus = userResponse.getStatus();

        assertEquals(expectedStatus, actualStatus);

        int copiesBefore = book.getCopies();

        int copiesAfter = bookRepository.findByNameAndIsbn(bookName, isbn).getCopies();

        assertEquals(copiesBefore + 1, copiesAfter);

        assertTrue(userResponse.getUser().getBooks().size() > 0);
    }


    @Test
    public void testReturnAllBooksToTheLibrary() {
        RequestSpecification request = RestAssured.given();

        request.header("Content-Type", "application/json");

        List<BookRequest> bookRequestList = Arrays.asList(createBookRequest("Finance", "ABC"), createBookRequest("Comic", "XYZ"));

        UserBookRequest userBookRequest = createUserBookRequest("Roy", bookRequestList);

        request.body(userBookRequest);

        Response response = request.post("/user/return-book");

        assertEquals(200, response.getStatusCode());

        UserResponse userResponse = response.getBody().as(UserResponse.class);

        String expectedStatus = "Book removed from user list and updated its stock in book";

        String actualStatus = userResponse.getStatus();

        assertEquals(expectedStatus, actualStatus);

        assertEquals(0, userResponse.getUser().getBooks().size());
    }

    @After
    public void deleteRecords() {
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    private UserBookRequest createUserBookRequest(String userName, List<BookRequest> bookRequestList) {
        UserBookRequest userBookRequest = new UserBookRequest();
        userBookRequest.setName(userName);
        userBookRequest.setBookRequestList(bookRequestList);
        return userBookRequest;
    }

    private BookRequest createBookRequest(String bookName, String isbn) {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setIsbn(isbn);
        bookRequest.setName(bookName);
        bookRequest.setCopies(1);
        return bookRequest;
    }
}
