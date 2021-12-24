package com.hexad.library.scenario;

import com.hexad.library.entity.Book;
import com.hexad.library.payload.response.BookResponse;
import com.hexad.library.repository.BookRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewBooksTest {

    @Autowired
    BookRepository bookRepository;

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }


    @Test
    public void testViewBooksWhenThereAreNoBooks() {
        bookRepository.deleteAll();

        Response response = RestAssured.get("/book/view-books");

        assertEquals(200, response.getStatusCode());

        assertEquals(0, response.getBody().as(BookResponse.class).getBooks().size());

        assertEquals(bookRepository.findAll(), response.getBody().as(BookResponse.class).getBooks());

    }

    @Test
    @Sql(scripts = "classpath:testData/books.sql")
    public void testViewBooksWhenThereAreBooks() {
        Response response = RestAssured.get("/book/view-books");

        assertEquals(3, response.getBody().as(BookResponse.class).getBooks().size());

        assertEquals(200, response.getStatusCode());

        List<String> actualBooks = bookRepository.findAll().stream().map(Book::getName).collect(Collectors.toList());
        List<String> expectedBooks = response.getBody().as(BookResponse.class).getBooks().stream().map(Book::getName).collect(Collectors.toList());

        assertEquals(actualBooks, expectedBooks);
    }

    @After
    public void deleteRecords() {
        bookRepository.deleteAll();
    }
}
