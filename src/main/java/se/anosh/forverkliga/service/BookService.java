package se.anosh.forverkliga.service;

import java.util.Collection;

import se.anosh.forverkliga.domain.Book;

public interface BookService {
	
	void addBook(Book book);
	Collection<Book> findAllBooks();
	void updateBook(long id, Book updated);
	void removeBook(long id);

}
