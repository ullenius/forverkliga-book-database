package se.anosh.forverkliga.dao;

import java.util.Collection;

import se.anosh.forverkliga.domain.Book;

public interface BookDao {
	
	boolean validKey(String key);
	void add(String apiKey, Book book);
	Collection<Book> findAll(String apiKey);
	void update(String apiKey, long id, Book updated);
	void remove(String apiKey, long id);
	void createDatabase(String apiKey);

}
