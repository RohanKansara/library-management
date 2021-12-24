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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:testData/borrow-books.sql")
public class BorrowBooksTest {

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
    public void testBorrowBooksFromTheLibraryWhenMoreThanOneCopyExits() {
        RequestSpecification request = RestAssured.given();

        request.header("Content-Type", "application/json");

        UserBookRequest userBookRequest = createUserBookRequest("Rohan", "Finance", "ABC");

        String bookName = userBookRequest.getBookRequestList().get(0).getName();

        String isbn = userBookRequest.getBookRequestList().get(0).getIsbn();

        Book book = bookRepository.findByNameAndIsbn(bookName, isbn);

        request.body(userBookRequest);

        Response response = request.post("/user/borrow-book");

        assertEquals(200, response.getStatusCode());

        UserResponse userResponse = response.getBody().as(UserResponse.class);

        String expectedStatus = "Books Added to the Borrow List!";

        String actualStatus = userResponse.getStatus();

        assertEquals(expectedStatus, actualStatus);

        int copiesBefore = book.getCopies();

        int copiesAfter = bookRepository.findByNameAndIsbn(bookName, isbn).getCopies();

        assertEquals(copiesBefore - 1, copiesAfter);

        assertTrue(copiesAfter > 0);

    }

    @Test
    public void testBorrowBooksFromTheLibraryWhenOnlyOneCopyExits() {
        RequestSpecification request = RestAssured.given();

        request.header("Content-Type", "application/json");

        UserBookRequest userBookRequest = createUserBookRequest("Roy", "Comic", "XYZ");

        String bookName = userBookRequest.getBookRequestList().get(0).getName();

        String isbn = userBookRequest.getBookRequestList().get(0).getIsbn();

        Book book = bookRepository.findByNameAndIsbn(bookName, isbn);

        request.body(userBookRequest);

        Response response = request.post("/user/borrow-book");

        assertEquals(200, response.getStatusCode());

        UserResponse userResponse = response.getBody().as(UserResponse.class);

        String expectedStatus = "Books Added to the Borrow List!";

        String actualStatus = userResponse.getStatus();

        assertEquals(expectedStatus, actualStatus);

        int copiesBefore = book.getCopies();

        int copiesAfter = bookRepository.findByNameAndIsbn(bookName, isbn).getCopies();

        assertEquals(copiesBefore - 1, copiesAfter);

        assertEquals(0, copiesAfter);
    }

    private UserBookRequest createUserBookRequest(String userName, String bookName, String isbn) {
        UserBookRequest userBookRequest = new UserBookRequest();
        userBookRequest.setName(userName);
        BookRequest bookRequest = new BookRequest();
        bookRequest.setIsbn(isbn);
        bookRequest.setName(bookName);
        bookRequest.setCopies(1);
        userBookRequest.setBookRequestList(Collections.singletonList(bookRequest));
        return userBookRequest;
    }

    @After
    public void deleteRecords() {
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }
}
