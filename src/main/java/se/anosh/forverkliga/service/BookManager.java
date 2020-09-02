package se.anosh.forverkliga.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import se.anosh.forverkliga.domain.Book;

public class BookManager implements BookService {

	private Map<Long,Book> database;

	public BookManager() {
		database = new ConcurrentHashMap<>();
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
		database.put(id, updated);
	}

	@Override
	public void removeBook(long id) {
		database.remove(id);
	}


}
