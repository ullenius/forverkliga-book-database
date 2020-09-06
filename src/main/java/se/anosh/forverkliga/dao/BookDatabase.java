package se.anosh.forverkliga.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import se.anosh.forverkliga.apikey.ApiKey;
import se.anosh.forverkliga.domain.Book;

@Repository
public class BookDatabase implements BookDao {
	private Map<String,Map<Long,Book>> keyMappings;
	private Logger logger;

	public BookDatabase() {
		keyMappings = new ConcurrentHashMap<>();
		logger = LoggerFactory.getLogger("BookDatabase.class");
	}

	@Override
	public void createDatabase(String key) {
		if (key == null || key.length() != ApiKey.length())
			throw new IllegalArgumentException("Illegal key length");

		Object exists = keyMappings.putIfAbsent(key, new ConcurrentHashMap<Long,Book>());
		logger.info("Creating database for key: {}", key);
		if (exists == null) {
			addMockData(keyMappings.get(key)); // TODO: remove in production
		}
	}

	@Override
	public void add(String apiKey, Book book) {
		Map<Long,Book> database = getDatabase(apiKey);
		database.put(book.getId(), book);
	}

	@Override
	public Collection<Book> findAll(String apiKey) {
		Map<Long,Book> database = getDatabase(apiKey);
		return Collections.unmodifiableCollection(database.values());
	}

	@Override
	public void update(String apiKey, long id, Book updated) {
		Map<Long,Book> database = getDatabase(apiKey);
		logger.info("Bookservice... updating id: {}", id);
		database.replace(id, updated);
		logger.info("Database after updating: {}", database);
	}

	@Override
	public void remove(String apiKey, long id) {
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
	public boolean validKey(String key) {
		return (keyMappings.containsKey(key));
	}
}
