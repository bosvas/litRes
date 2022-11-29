package org.example.app.services;

import org.example.app.exceptions.WrongRegexException;

import java.sql.SQLException;
import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    boolean removeItemById(Integer bookIdToRemove);

    boolean removeItemByRegex(String queryRegex) throws SQLException;
//
//    boolean removeItemByAuthor(String authorName);
//
//    boolean removeItemByTitle(String title);
//
//    boolean removeItemBySize(int size);
}
