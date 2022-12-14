package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean removeBookByRegex(String queryRegex) throws SQLException {
        return bookRepo.removeItemByRegex(queryRegex);
    }
//
//    public boolean removeBookByAuthor(String authorName) {
//        return bookRepo.removeItemByAuthor(authorName);
//    }
//
//    public boolean removeBookByTitle(String title) {
//        return bookRepo.removeItemByTitle(title);
//    }
//
//    public boolean removeBookBySize(int size) {
//        return bookRepo.removeItemBySize(size);
//    }
}
