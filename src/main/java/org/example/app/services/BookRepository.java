package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.app.exceptions.WrongRegexException;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);

    private ApplicationContext context;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }


    @Override
    public List<Book> retreiveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemplate.update("INSERT INTO books(author,title,size) VALUES(:author, :title, :size)", parameterSource);
        logger.info("store new book: " + book);

    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", bookIdToRemove);
        jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
        logger.info("remove book completed");
        return true;
    }

    @Override
    public boolean removeItemByRegex(String queryRegex) throws SQLException {
        List<Book> booksToDelete = new ArrayList<>();
        for (Book book : retreiveAll()) {
            if (book.getAuthor().equals(queryRegex) || book.getTitle().equals(queryRegex) || book.getSize().toString().equals(queryRegex)) {
                booksToDelete.add(book);
            }
        }
        if (!booksToDelete.isEmpty()) {
            String sql = "delete from books where id = ?";

            Connection connection = DriverManager.getConnection("jdbc:h2:mem:book_store", "sa", "");


            for (Book book : booksToDelete) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, book.getId());
                preparedStatement.executeUpdate();
            }

            logger.info("remove book completed: " + booksToDelete);
            return true;
        } else
           return false;
    }

}


//    @Override
//    public boolean removeItemByAuthor(String author) {
//        List<Book> booksToDelete = new ArrayList<>();
//        for (Book book : retreiveAll()) {
//            if (book.getAuthor().equals(author)) {
//                booksToDelete.add(book);
//            }
//        }
//        if (!booksToDelete.isEmpty()){
//            logger.info("remove book completed: " + booksToDelete);
//            return repo.removeAll(booksToDelete);}
//        else
//            return false;
//    }
//
//    @Override
//    public boolean removeItemByTitle(String title) {
//        List<Book> booksToDelete = new ArrayList<>();
//        for (Book book : retreiveAll()) {
//            if (book.getTitle().equals(title)) {
//                booksToDelete.add(book);
//            }
//        }
//        if (!booksToDelete.isEmpty()){
//            logger.info("remove book completed: " + booksToDelete);
//            return repo.removeAll(booksToDelete);}
//        else
//            return false;
//    }
//
//    @Override
//    public boolean removeItemBySize(int size) {
//        List<Book> booksToDelete = new ArrayList<>();
//        for (Book book : retreiveAll()) {
//            if (book.getSize()==size) {
//                booksToDelete.add(book);
//            }
//        }
//        if (!booksToDelete.isEmpty()){
//            logger.info("remove book completed: " + booksToDelete);
//            return repo.removeAll(booksToDelete);}
//        else
//            return false;
//    }