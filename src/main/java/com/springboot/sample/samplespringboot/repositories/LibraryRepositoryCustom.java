package com.springboot.sample.samplespringboot.repositories;
import com.springboot.sample.samplespringboot.restcontrollers.LibraryBean;
import java.util.List;

public interface LibraryRepositoryCustom {
    List<LibraryBean> getBooksByAuthor(String authorname);
}
