insert into user (id, name) values (1, 'Rohan');
insert into user (id, name) values (2, 'Roy');
insert into books (id, author, copies, description, isbn, name, serial_name) values (1, 'RK', 2, 'Description', 'ABC', 'Finance', 'S-NUM-F');
insert into books (id, author, copies, description, isbn, name, serial_name) values (2, 'RK', 2, 'Description', 'XYZ', 'Comic', 'S-NUM-C');
insert into books_users (book_id, user_id) values (1, 1);
insert into books_users (book_id, user_id) values (1, 2);
insert into books_users (book_id, user_id) values (2, 1);
insert into books_users (book_id, user_id) values (2, 2);
