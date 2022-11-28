package org.example.web.dto;

import javax.validation.constraints.*;

public class Book {

    private Integer id;
    @NotEmpty(message = "every book should have an author! (this field can't be empty)")
    @Size(max = 25, message = "max length of author name - 25")
    private String author;

    @NotEmpty(message = "every book should have a title! (this field can't be empty)")
    @Size(max = 25, message = "max length of book title - 25")
    private String title;
    @Digits(integer = 4, fraction = 0)
    private Integer size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", size=" + size +
                '}';
    }
}
