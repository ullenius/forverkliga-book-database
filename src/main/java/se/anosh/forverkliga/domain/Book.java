package se.anosh.forverkliga.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Book {
	
	public Book() {
	}
	
	public Book(long id, String title, String author) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.updated = LocalDateTime.now();
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public LocalDateTime getUpdated() {
		return updated;
	}
	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}
	private long id;
	private String title;
	private String author;
	private LocalDateTime updated;
}
