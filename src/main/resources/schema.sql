Drop table if exists books;

Create table books(
    id INT AUTO_INCREMENT PRIMARY KEY,
    author VARCHAR(250) NOT NULL ,
    title VARCHAR(250) not null ,
    size INT DEFAULT NULL
);