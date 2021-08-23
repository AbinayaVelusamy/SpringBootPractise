package com.springboot.sample.samplespringboot.restcontrollers;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity @Component
@Table(name="bookdatabase")
public class LibraryBean {
    @Column(name="isbn")
    private String isbn;
    @Column(name="id")
    @Id
    private String id;
    @Column(name="author")
    private String author;
    @Column(name="aisle")
    private int aisle;
    @Column(name="bookname")
    private String bookname;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAisle() {
        return aisle;
    }

    public void setAisle(int aisle) {
        this.aisle = aisle;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
}
