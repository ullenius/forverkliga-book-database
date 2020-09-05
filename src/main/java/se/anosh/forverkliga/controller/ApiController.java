package se.anosh.forverkliga.controller;

import java.util.Collection;

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
		} else if (op.contentEquals("update")) {
			
			logger.debug("Running update");
			Book updated = new Book();
			updated.setId(Long.parseLong(id));
			updated.setAuthor(author);
			updated.setTitle(title);
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

		return HttpFail();
	}
	
	private ResponseEntity<BookWrapper> addBook(Long id) {
		BookWrapper wrapper = new BookWrapper();
		wrapper.setId(id);
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
		public void setId(Long id) {
			this.id = id;
		}
		
		public Long getId() {
			return id;
		}
	}

}
