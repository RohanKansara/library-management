package com.hexad.library.service.impl;

import com.hexad.library.entity.Book;
import com.hexad.library.entity.User;
import com.hexad.library.payload.request.UserBookRequest;
import com.hexad.library.payload.response.UserResponse;
import com.hexad.library.repository.BookRepository;
import com.hexad.library.repository.UserRepository;
import com.hexad.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public UserResponse borrowBook(UserBookRequest userBookRequest) {
        User user = userRepository.findByName(userBookRequest.getName());

        UserResponse userResponse = new UserResponse();

        if (user == null) {
            userResponse.setUser(null);
            userResponse.setStatus("User Doesn't Exists, Please add the user first");
            return userResponse;
        } else {
            if (checkBookRequestNumber(userBookRequest))
                return createUserResponse(user, "You can only borrow max two books at a time");
            else if (checkBookCopyRequestNumber(userBookRequest))
                return createUserResponse(user, "You can only borrow max one copy of book at a time");
            else if (checkBooksAlreadyBorrowedNumber(user)) {
                return createUserResponse(user, "You Already Have two books with you");
            } else {
                return addBooksToUser(user, userBookRequest);
            }
        }
    }

    @Override
    @Transactional
    public UserResponse returnBook(UserBookRequest userBookRequest) {
        User user = userRepository.findByName(userBookRequest.getName());

        Set<String> status = new HashSet<>();

        userBookRequest.getBookRequestList().forEach(bookRequest -> {
            Book book = bookRepository.findByNameAndIsbn(bookRequest.getName(), bookRequest.getIsbn());
            if(checkBookUserExists(user, book)) {
                user.getBooks().remove(book);
                book.setCopies(book.getCopies() + 1);
                book.getUsers().remove(user);
                userRepository.save(user);
                bookRepository.save(book);
                status.add("Book removed from user list and updated its stock in book");
            } else {
                status.add("Book is not associated with the user");
            }
        });

        return createUserResponse(user, String.join(", ", status));
    }

    @Transactional
    private UserResponse addBooksToUser(User user, UserBookRequest userBookRequest) {
        Set<String> status = new HashSet<>();
        userBookRequest.getBookRequestList().forEach(bookRequest -> {
            Book book = bookRepository.findByNameAndIsbn(bookRequest.getName(), bookRequest.getIsbn());
            if (book.getCopies() > 0 && !checkBookUserExists(user, book) && !checkBooksAlreadyBorrowedNumber(user)) {
                user.getBooks().add(book);
                book.getUsers().add(user);
                book.setCopies(book.getCopies() - 1);
                userRepository.save(user);
                bookRepository.save(book);
                status.add("Books Added to the Borrow List!");
            } else {
                status.add("Error adding some books to borrow list");
            }
        });
        return createUserResponse(user, String.join(", ", status));
    }

    private boolean checkBookUserExists(User user, Book book) {
        return bookRepository.bookUserExists(user.getId(), book.getId());
    }

    private boolean checkBooksAlreadyBorrowedNumber(User user) {
        return userRepository.findNumberOfBooksByUserId(user.getId());
    }

    private boolean checkBookRequestNumber(UserBookRequest userBookRequest) {
        return userBookRequest.getBookRequestList().size() > 2;
    }

    private boolean checkBookCopyRequestNumber(UserBookRequest userBookRequest) {
        return userBookRequest.getBookRequestList().stream().anyMatch(bookRequest -> bookRequest.getCopies() > 1);
    }

    private UserResponse createUserResponse(User user, String status) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUser(user);
        userResponse.setStatus(status);
        return userResponse;
    }
}
