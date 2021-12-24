package com.hexad.library.repository;

import com.hexad.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    @Query(value = "select count(user_id)>1 from books_users where user_id=:id", nativeQuery = true)
    boolean findNumberOfBooksByUserId(Long id);
}
