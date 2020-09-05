package se.anosh.forverkliga.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import se.anosh.forverkliga.domain.Book;
import se.anosh.forverkliga.service.BookService;

@RestController
public class ApiController {

	private static final List<String> errorMessages;
	
	static {
		String[] messages = { 
				"Mispelled a variable",
				"Disrupted by solar flares",
				"Storing passwords in plaintext and hashing using MD5",
				"Flux capacitors overloaded",
				"Warp core ejected",
				"Something went wrong, please try again",
				"Internet is offline",
				"Forgot a semicolon",
				"Forgot the window load event" ,
				"Unresolved merge conflict",
				"Server is unhappy"
		};
		errorMessages = Collections.unmodifiableList(Arrays.asList(messages));
		messages = null;
	}
	
	private BookService service;
	private final Logger logger;

	@Autowired
	public ApiController(BookService service) {
		this.service = service;
		logger = LoggerFactory.getLogger("ApiController.class");
	}

	@GetMapping(path="/", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookWrapper> view(
			@RequestParam String op,
			@RequestParam(required=false) String id,
			@RequestParam(required=false) String author,
			@RequestParam(required=false) String title) {
		
		

		if (op.contentEquals("select")) {
			return viewAllBooks();
		} 
		else if (op.contentEquals("update")) {
			
			logger.debug("Running update");
			Book updated = new Book(Long.parseLong(id), author, title);
			return updateBook(updated);
		}
		else if (op.contentEquals("delete")) {
			return removeBook(Long.parseLong(id));
		}
		else if (op.contentEquals("insert")) {
			
			Book book = new Book(title, author);
			logger.info("Adding book: {}", book);
			service.addBook(book);
			return addBook(book.getId());
		}

		else {
			return HttpFail();
		}
	}
	
	private ResponseEntity<BookWrapper> randomFail() {
		BookWrapper wrapper = new BookWrapper();
		wrapper.setStatus("error");
		wrapper.setMessage(randomErrorMessage());
		return HttpOK(wrapper);
	}
	
	private String randomErrorMessage() {
		List<String> randomized = new ArrayList<>(errorMessages);
		Collections.shuffle(randomized);
		return randomized.get(0);
	}
	
	private ResponseEntity<BookWrapper> addBook(Long id) {
		BookWrapper wrapper = new BookWrapper(id);
		return HttpOK(wrapper);
	}
	
	private ResponseEntity<BookWrapper> removeBook(long id) {
		service.removeBook(id);
		BookWrapper wrapper = new BookWrapper();
		return HttpOK(wrapper);
	}
	
	private ResponseEntity<BookWrapper> viewAllBooks() {
		BookWrapper wrapper = new BookWrapper(service.findAllBooks());
		return HttpOK(wrapper);
	}
	
	private ResponseEntity<BookWrapper> updateBook(Book updated) {
		service.updateBook(updated.getId(), updated);
		BookWrapper wrapper = new BookWrapper();
		return HttpOK(wrapper);
	}
	
	private ResponseEntity<BookWrapper> HttpOK(BookWrapper wrapper) {
		return new ResponseEntity<BookWrapper>(wrapper,HttpStatus.OK);
	}
	
	private ResponseEntity<BookWrapper> HttpFail() {
		BookWrapper error = new BookWrapper();
		error.setStatus("error");
		error.setMessage("FAIL");
		return new ResponseEntity<BookWrapper>(error,HttpStatus.I_AM_A_TEAPOT);
	}

	private static class BookWrapper {

		public BookWrapper(Long id) {
			this.id = id;
		}
		
		public BookWrapper() {
			status = "success";
		}
		
		public BookWrapper(Collection<Book> data) {
			this.data = data;
			status = "success";
		}
		
		@JsonInclude(Include.NON_NULL)
		private Collection<Book> data;
		private String status;
		@JsonInclude(Include.NON_NULL)
		private String message;
		@JsonInclude(Include.NON_NULL)
		private Long id;
		
		public Collection<Book> getData() {
			return data;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		public Long getId() {
			return id;
		}
	}

}
