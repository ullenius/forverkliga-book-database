package se.anosh.forverkliga.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.anosh.forverkliga.dao.BookDao;
import se.anosh.forverkliga.domain.Book;

@Service
public class BookManager implements BookService {

	private BookDao dao;

	@Autowired
	public BookManager(BookDao dao) {
		this.dao = dao;
	}

	@Override
	public void createDatabase(String key) {
		dao.createDatabase(key);
	}

	@Override
	public void addBook(String apiKey, Book book) {
		dao.add(apiKey, book);
	}

	@Override
	public Collection<Book> findAllBooks(String apiKey) {
		return dao.findAll(apiKey);
	}

	@Override
	public void updateBook(String apiKey, long id, Book updated) {
		dao.update(apiKey, id, updated);
	}

	@Override
	public void removeBook(String apiKey, long id) {
		dao.remove(apiKey, id);
	}

	@Override
	public boolean validApiKey(String key) {
		return dao.validKey(key);
	}

}
