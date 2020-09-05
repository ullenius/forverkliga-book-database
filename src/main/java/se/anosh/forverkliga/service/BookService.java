package se.anosh.forverkliga.service;

import java.util.Collection;

import se.anosh.forverkliga.domain.Book;

public interface BookService {
	
	void addBook(String apiKey, Book book);
	Collection<Book> findAllBooks(String apiKey);
	void updateBook(String apiKey, long id, Book updated);
	void removeBook(String apiKey, long id);
	void createDatabase(String apiKey);
}
