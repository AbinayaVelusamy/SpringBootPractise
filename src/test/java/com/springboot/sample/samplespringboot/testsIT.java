package com.springboot.sample.samplespringboot;

import com.springboot.sample.samplespringboot.restcontrollers.LibraryBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@SpringBootTest
public class testsIT {
   @Autowired
   LibraryBean newBook;

    @Test
    public void addBookIntTest(){
           TestRestTemplate template=new TestRestTemplate();
           HttpHeaders headers= new HttpHeaders();
           headers.setContentType(MediaType.APPLICATION_JSON);
           HttpEntity<LibraryBean> request=new HttpEntity<LibraryBean>(buildBookBean(),headers);
           ResponseEntity response=template.postForEntity("http://localhost:9091/addBook",request,String.class);
    }

    @Test
    public void  getBookBuAuthorIntTest(){
       TestRestTemplate template= new TestRestTemplate();
       ResponseEntity response= template.getForEntity("http://localhost:9091/author?authorname=Abinaya",String.class);
       System.out.println(response.getBody());
       Assertions.assertTrue(response.getStatusCode()==HttpStatus.OK);
    }

  public LibraryBean buildBookBean(){
        newBook.setBookname("Python");
        newBook.setAuthor("Abi");
        newBook.setAisle(3014);
        newBook.setIsbn("old");
        newBook.setId("old3014");
        return newBook;
  }
}
