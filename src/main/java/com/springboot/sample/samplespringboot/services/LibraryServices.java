package com.springboot.sample.samplespringboot.services;

import com.springboot.sample.samplespringboot.restcontrollers.LibraryBean;
import com.springboot.sample.samplespringboot.repositories.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LibraryServices {
    @Autowired
    LibraryRepository repository;

    public String createId(String isbn, int aisle) {
        return isbn + aisle;
    }

    public List<LibraryBean> finalAllBooks() {
        return repository.findAll();
    }

    public boolean isExists(String id) {
        try {
            List<LibraryBean> books = finalAllBooks();
            for (LibraryBean book : books) {
                if (book.getId().equals(id))
                    return true;
            }
            return false;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public void saveBooks(LibraryBean library) {
        repository.save(library);
    }


    public List<LibraryBean> getBooksByAuthor(String authorname) {
        return repository.getBooksByAuthor(authorname);
    }

    public Boolean deleteBook(LibraryBean library) {
        List<LibraryBean> books = finalAllBooks();
        for (LibraryBean book : books) {
            if (book.getId().equals(library.getId())) {
                repository.delete(book);
                return true;
            }
        }
        return false;
    }

    public LibraryBean updateBook(String id, LibraryBean library) {
        try {
            LibraryBean book = repository.findById(id).get();
            book.setAisle(library.getAisle());
            book.setAuthor(library.getAuthor());
            book.setBookname(library.getBookname());
            repository.save(book);
            return book;
        } catch (NoSuchElementException ex) {
            return null;
        }
    }
}
