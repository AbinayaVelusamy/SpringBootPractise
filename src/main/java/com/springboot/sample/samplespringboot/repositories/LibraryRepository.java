package com.springboot.sample.samplespringboot.repositories;

import com.springboot.sample.samplespringboot.restcontrollers.LibraryBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryBean,String>,LibraryRepositoryCustom {

}
