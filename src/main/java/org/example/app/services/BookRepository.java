package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    {
        Book book2=new Book();
        book2.setId(String.valueOf(1));
        book2.setAuthor("Author1");
        book2.setTitle("Title1");
        book2.setSize(100);
        repo.add(book2);
        Book book1=new Book();
        book1.setId(String.valueOf(2));
        book1.setAuthor("Author1");
        book1.setTitle("Title2");
        book1.setSize(110);
        repo.add(book1);
        Book book3=new Book();
        book3.setId(String.valueOf(3));
        book3.setAuthor("Author2");
        book3.setTitle("Title3");
        book3.setSize(150);
        repo.add(book3);
        Book book4=new Book();
        book4.setId(String.valueOf(4));
        book4.setAuthor("Author3");
        book4.setTitle("Title4");
        book4.setSize(160);
        repo.add(book4);
    }

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(String.valueOf(book.hashCode()));
        if (!(Objects.equals(book.getAuthor(), ""))
                ||!(Objects.equals(book.getTitle(), ""))
                ||!(book.getSize()==null)){
        logger.info("store new book: " + book);
        repo.add(book);}
    }

    @Override
    public boolean removeItemById(String bookIdToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public boolean removeItemByRegex(String queryRegex) {
        List<Book> booksToDelete = new ArrayList<>();
        for (Book book : retreiveAll()) {
            if (book.getAuthor().equals(queryRegex)||
                    book.getTitle().equals(queryRegex)||
                    book.getSize().toString().equals(queryRegex)) {
                booksToDelete.add(book);
            }
        }
        if (!booksToDelete.isEmpty()){
        logger.info("remove book completed: " + booksToDelete);
        return repo.removeAll(booksToDelete);}
        else
            return false;
    }

    @Override
    public boolean removeItemByAuthor(String author) {
        List<Book> booksToDelete = new ArrayList<>();
        for (Book book : retreiveAll()) {
            if (book.getAuthor().equals(author)) {
                booksToDelete.add(book);
            }
        }
        if (!booksToDelete.isEmpty()){
            logger.info("remove book completed: " + booksToDelete);
            return repo.removeAll(booksToDelete);}
        else
            return false;
    }

    @Override
    public boolean removeItemByTitle(String title) {
        List<Book> booksToDelete = new ArrayList<>();
        for (Book book : retreiveAll()) {
            if (book.getTitle().equals(title)) {
                booksToDelete.add(book);
            }
        }
        if (!booksToDelete.isEmpty()){
            logger.info("remove book completed: " + booksToDelete);
            return repo.removeAll(booksToDelete);}
        else
            return false;
    }

    @Override
    public boolean removeItemBySize(int size) {
        List<Book> booksToDelete = new ArrayList<>();
        for (Book book : retreiveAll()) {
            if (book.getSize()==size) {
                booksToDelete.add(book);
            }
        }
        if (!booksToDelete.isEmpty()){
            logger.info("remove book completed: " + booksToDelete);
            return repo.removeAll(booksToDelete);}
        else
            return false;
    }
}
