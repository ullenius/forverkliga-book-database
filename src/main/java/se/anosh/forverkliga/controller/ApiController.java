package se.anosh.forverkliga.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import se.anosh.forverkliga.domain.Book;
import se.anosh.forverkliga.service.BookService;

@RestController
public class ApiController {

	private BookService service;

	@Autowired
	public ApiController(BookService service) {
		this.service = service;
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
			
			System.out.println("running update...");
			Book updated = new Book();
			updated.setId(Long.parseLong(id));
			updated.setAuthor(author);
			updated.setTitle(title);
			return updateBook(updated);
		}
		else if (op.contentEquals("delete")) {
			return removeBook(Long.parseLong(id));
		}

		BookWrapper error = new BookWrapper();
		error.setStatus("error");
		error.setMessage("FAIL");
		return new ResponseEntity<BookWrapper>(error,HttpStatus.I_AM_A_TEAPOT);
	}
	
	private ResponseEntity<BookWrapper> removeBook(long id) {
		service.removeBook(id);
		BookWrapper wrapper = new BookWrapper();
		wrapper.status = "success";
		return new ResponseEntity<BookWrapper>(wrapper,HttpStatus.OK);
	}
	
	private ResponseEntity<BookWrapper> viewAllBooks() {
		BookWrapper wrapper = new BookWrapper();
		wrapper.data = service.findAllBooks();
		wrapper.status = "success";

		return new ResponseEntity<BookWrapper>(wrapper,HttpStatus.OK);
	}
	
	private ResponseEntity<BookWrapper> updateBook(Book updated) {
		System.out.println("updateBook received: " + updated);
		service.updateBook(updated.getId(), updated);
		BookWrapper wrapper = new BookWrapper();
		wrapper.status = "success";

		return new ResponseEntity<BookWrapper>(wrapper,HttpStatus.OK);
	}
	

	private static class BookWrapper {

		public BookWrapper() {
		}
		@JsonInclude(Include.NON_NULL)
		Collection<Book> data;
		String status;
		@JsonInclude(Include.NON_NULL)
		String message;
		
		public Collection<Book> getData() {
			return data;
		}
		public void setData(Collection<Book> data) {
			this.data = data;
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

	}

}
