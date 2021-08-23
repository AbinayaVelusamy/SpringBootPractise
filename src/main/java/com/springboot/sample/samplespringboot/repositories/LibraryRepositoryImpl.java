package com.springboot.sample.samplespringboot.repositories;

import com.springboot.sample.samplespringboot.restcontrollers.LibraryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class LibraryRepositoryImpl implements LibraryRepositoryCustom{
    @Autowired
    LibraryRepository repository;

    @Override
    public List<LibraryBean> getBooksByAuthor(String authorname) {
     List<LibraryBean> allBooks=repository.findAll();
     List<LibraryBean> selectedBooks =new ArrayList<LibraryBean>();
     for(LibraryBean book: allBooks)
     {
         if(book.getAuthor().equals(authorname))
             selectedBooks.add(book);
     }
     return selectedBooks;
    }
}
