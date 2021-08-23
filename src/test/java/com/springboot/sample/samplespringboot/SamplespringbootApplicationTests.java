package com.springboot.sample.samplespringboot;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.sample.samplespringboot.repositories.LibraryRepository;
import com.springboot.sample.samplespringboot.restcontrollers.AddResponseBean;
import com.springboot.sample.samplespringboot.restcontrollers.LibraryBean;
import com.springboot.sample.samplespringboot.restcontrollers.LibraryController;
import com.springboot.sample.samplespringboot.services.LibraryServices;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SamplespringbootApplicationTests {
	@Autowired
	LibraryController libController;
	@MockBean
	LibraryServices services;
	@MockBean
	LibraryRepository repository;
	@Autowired
	private MockMvc mockmvc;

	@Test
	public void addBookTestMockitoForCreation(){
		LibraryBean book=buildBean();
		Mockito.when(services.createId(book.getIsbn(), book.getAisle())).thenReturn(book.getId());
		Mockito.when(services.isExists(book.getId())).thenReturn(false);
		Mockito.when(repository.save(book)).thenReturn(book);
		ResponseEntity response= libController.addBooks(book);
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		AddResponseBean responseBean=new AddResponseBean();
		responseBean= (AddResponseBean) response.getBody();
		assertEquals(responseBean.getMsg(), "Book is added to the book database");
		assertEquals(responseBean.getId(), book.getId());
	}

	@Test
	public void addBookMockitoTestForExistingBook(){
		LibraryBean book=buildBean();
		Mockito.when(services.createId(book.getIsbn(), book.getAisle())).thenReturn(book.getId());
		Mockito.when(services.isExists(book.getId())).thenReturn(true);
		ResponseEntity response= libController.addBooks(book);
		assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
		AddResponseBean responseBean=new AddResponseBean();
		responseBean= (AddResponseBean) response.getBody();
		assertEquals(responseBean.getMsg(), "Book has already been added");
		assertEquals(responseBean.getId(), book.getId());
	}

	@Test
	public void deleteBookMockitoTestForDeletion(){
		LibraryBean book= buildBean();
        Mockito.when(services.deleteBook(book)).thenReturn(true);
        ResponseEntity response= (ResponseEntity) libController.deletebook(book);
        assertEquals(response.getStatusCode(),HttpStatus.OK);
	}

	@Test
	public void updateBookMockitoTest(){
		LibraryBean book=buildBean();
		LibraryBean newBook=buildNewBook();
        Mockito.when(services.updateBook(book.getId(),newBook)).thenReturn(newBook);
        ResponseEntity response= libController.updateBook(book.getId(),newBook);
        assertEquals(response.getStatusCode(),HttpStatus.CREATED);
        LibraryBean updatedBook= (LibraryBean) response.getBody();
        assertEquals(updatedBook,newBook);
	}

	@Test
	public void getBookByAuthorMockitoTest(){
		LibraryBean oldBook=buildBean();
		LibraryBean newBook=buildNewBook();
		List<LibraryBean> books=new ArrayList<LibraryBean>();
		books.add(oldBook);
		books.add(newBook);
		Mockito.when(services.getBooksByAuthor(oldBook.getAuthor())).thenReturn(books);
		ResponseEntity response=  libController.getBooksByAuthor(oldBook.getAuthor());
		assertEquals(response.getStatusCode(),HttpStatus.OK);
	}

	@Test
	public void addBookMvcTest() throws Exception {
		LibraryBean book= buildBean();
		ObjectMapper mapper=new ObjectMapper();
		String jsonStr=mapper.writeValueAsString(book);
		Mockito.when(services.createId(book.getIsbn(),book.getAisle())).thenReturn(book.getIsbn());
		Mockito.when(services.isExists(book.getId())).thenReturn(false);
		this.mockmvc.perform(post("/addBook").contentType(MediaType.APPLICATION_JSON).content(jsonStr))
				.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(book.getId()));
	}

	@Test
	public void getBooksByAuthorMvcTest() throws Exception {
		LibraryBean oldBook=buildBean();
		List<LibraryBean> books=new ArrayList<LibraryBean>();
		books.add(oldBook);
		Mockito.when(services.getBooksByAuthor(oldBook.getAuthor())).thenReturn(books);
		this.mockmvc.perform(get("/author").queryParam("authorname", oldBook.getAuthor()))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].author").value(oldBook.getAuthor()))
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(content().json("[{\"isbn\":\"new\",\"id\":\"new2466\",\"author\":\"Abi\",\"aisle\":2466,\"bookname\":\"SpringBoot\"}]"));
	}

	public LibraryBean buildBean(){
	LibraryBean book=new LibraryBean();
	book.setBookname("SpringBoot");
	book.setAuthor("Abi");
	book.setAisle(2466);
	book.setIsbn("new");
	book.setId("new2466");
	return book;
}

  public LibraryBean buildNewBook(){
	  LibraryBean newBook=new LibraryBean();
	  newBook.setBookname("Java");
	  newBook.setAuthor("Appu");
	  newBook.setAisle(2426);
	  newBook.setIsbn("old");
	  newBook.setId("old2426");
	  return newBook;
  }
}
