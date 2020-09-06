package se.anosh.forverkliga.dao;

import java.util.*;
import java.util.concurrent.*;

import org.slf4j.*;
import org.springframework.stereotype.Repository;

import net.jodah.expiringmap.*;
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

		if (!keyMappings.containsKey(key)) {
			logger.info("Creating database for key: {}", key);
			Map<Long,Book> map = ExpiringMap.builder()
					.maxSize(123)
					.expirationPolicy(ExpirationPolicy.ACCESSED)
					.expiration(30, TimeUnit.MINUTES)
					.build();
			
			keyMappings.putIfAbsent(key, map);
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

	@Override
	public boolean validKey(String key) {
		return (keyMappings.containsKey(key));
	}
}
