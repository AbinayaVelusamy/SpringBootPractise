package com.springboot.sample.samplespringboot.restcontrollers;
import com.springboot.sample.samplespringboot.repositories.LibraryRepository;
import com.springboot.sample.samplespringboot.services.LibraryServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class LibraryController {
    @Autowired
    LibraryServices services;
    @Autowired
    LibraryRepository repository;
    Logger logger= LoggerFactory.getLogger(LibraryController.class);
    AddResponseBean response=new AddResponseBean();

    @PostMapping("/addBook")
    public ResponseEntity addBooks(@RequestBody LibraryBean library) {
        String id = services.createId(library.getIsbn(), library.getAisle());//dependency
        logger.info("Created an id:"+id);
        if (services.isExists(id)) {//dependency
            logger.info("Book already exists");
            response.setMsg("Book has already been added");
            response.setId(id);
            HttpHeaders headers=new HttpHeaders();
            headers.add("id",id);
            return new ResponseEntity<AddResponseBean>(response,headers,HttpStatus.ACCEPTED);
        } else {
            logger.info("Creating new book");
            library.setId(id);
            repository.save(library);//dependency
            response.setMsg("Book is added to the book database");
            response.setId(id);
            return new ResponseEntity<AddResponseBean>(response, HttpStatus.CREATED);
        }
    }

    @PostMapping("/deleteBook")
    public ResponseEntity deletebook(@RequestBody LibraryBean library)
    {
        if(services.deleteBook(library)) {
            response.setMsg("Book has been deleted");
            response.setId(library.getId());
            return new ResponseEntity<AddResponseBean>(response,HttpStatus.OK);
        }
        else
        {
            response.setMsg("No Book is found");
            response.setId(library.getId());
            return new ResponseEntity<AddResponseBean>(response,HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity updateBook(@PathVariable String id,@RequestBody LibraryBean library){
        LibraryBean book=services.updateBook(id,library);//dependency
        if(book!=null)
            return new ResponseEntity<LibraryBean>(book,HttpStatus.CREATED);
        else {
            response.setMsg("No Book is found");
            response.setId(id);
            return new ResponseEntity<AddResponseBean>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/author")
    public ResponseEntity getBooksByAuthor(@RequestParam(value="authorname")String authorname)
    {
        List<LibraryBean> books= services.getBooksByAuthor(authorname);//dependency
        if(!books.isEmpty())
            return new ResponseEntity<List<LibraryBean>>(books,HttpStatus.OK);
        else
            return new ResponseEntity<String>("No book found by the Author name "+authorname,HttpStatus.NOT_FOUND);
    }
}
