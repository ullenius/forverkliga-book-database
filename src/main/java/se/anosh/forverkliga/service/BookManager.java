package se.anosh.forverkliga.service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import se.anosh.forverkliga.domain.Book;

@Service
public class BookManager implements BookService {

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
				"Forgot the window load event" 
		};
		errorMessages = Collections.unmodifiableList(Arrays.asList(messages));
	}

	private Map<Long,Book> database;
	private static final List<String> errorMessages;

	public BookManager() {
		database = new ConcurrentHashMap<>();

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
	public void addBook(Book book) {
		database.put(book.getId(), book);
	}

	@Override
	public Collection<Book> findAllBooks() {

		return Collections.unmodifiableCollection(database.values());
	}

	@Override
	public void updateBook(long id, Book updated) {

		System.out.println("Bookservice... updating id:" + id);
		database.replace(id, updated);
		System.out.println("Database after updating... " + database);
	}

	@Override
	public void removeBook(long id) {
		database.remove(id);
	}


}
