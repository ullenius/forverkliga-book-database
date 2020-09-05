package se.anosh.forverkliga.service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import se.anosh.forverkliga.domain.Book;

@Service
public class BookManager implements BookService {


	private Map<String,Map<Long,Book>> keyMappings;

	public BookManager() {
		keyMappings = new ConcurrentHashMap<>();
	}

	@Override
	public void createDatabase(String key) {
		if (key == null || key.length() != 5)
			throw new IllegalArgumentException("Illegal key length");

		keyMappings.putIfAbsent(key, new ConcurrentHashMap<Long,Book>());
		System.out.println("Creating database for key... " + key);
		addMockData(keyMappings.get(key)); // TODO: remove in production
	}

	@Override
	public void addBook(String apiKey, Book book) {
		Map<Long,Book> database = getDatabase(apiKey);
		database.put(book.getId(), book);
	}

	@Override
	public Collection<Book> findAllBooks(String apiKey) {
		Map<Long,Book> database = getDatabase(apiKey);
		return Collections.unmodifiableCollection(database.values());
	}

	@Override
	public void updateBook(String apiKey, long id, Book updated) {
		Map<Long,Book> database = getDatabase(apiKey);
		System.out.println("Bookservice... updating id:" + id);
		database.replace(id, updated);
		System.out.println("Database after updating... " + database);
	}

	@Override
	public void removeBook(String apiKey, long id) {
		Map<Long,Book> database = getDatabase(apiKey);
		database.remove(id);
	}
	
	private Map<Long,Book> getDatabase(String key) {
		Map<Long,Book> database = keyMappings.get(key);
		return database;
	}

	private void addMockData(Map<Long,Book> database) {

		Book book1 = new Book("Dracula", "Bram Stoker");
		Book book2 = new Book("Antifragile", "Nassim Taleb");
		Book book3 = new Book("1984", "George Orwell");
		Book book4 = new Book("Ett Öga Rött", "Jonas Hassen Khemiri");

		database.put(book1.getId(), book1);
		database.put(book2.getId(), book2);
		database.put(book3.getId(), book3);
		database.put(book4.getId(), book4);
	}

	@Override
	public boolean validApiKey(String key) {
		return (keyMappings.containsKey(key));
	}

}
