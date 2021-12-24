package com.hexad.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "isbn", length = 50, nullable = false, unique = true)
    private String isbn;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "serialName", length = 50, nullable = false)
    private String serialName;

    @Column(name = "description", length = 250, nullable = false)
    private String description;

    @Column(name = "copies")
    private int copies;

    private String author;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "books_users", joinColumns = {@JoinColumn(name = "book_id")}, inverseJoinColumns = {
            @JoinColumn(name = "user_id")})
    private Set<User> users = new HashSet<>();

    public Book(String isbn, String name, String serialName, String description) {
        this.isbn = isbn;
        this.name = name;
        this.serialName = serialName;
        this.description = description;
    }

}
