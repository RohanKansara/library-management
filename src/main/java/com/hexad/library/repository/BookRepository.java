package com.hexad.library.repository;

import com.hexad.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByNameAndIsbn(String name, String isbn);

    @Query(value = "select count(*)=1 from books_users where user_id=:userId and book_id=:bookId", nativeQuery = true)
    boolean bookUserExists(Long userId, Long bookId);
}
