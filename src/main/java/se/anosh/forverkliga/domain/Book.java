package se.anosh.forverkliga.domain;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class Book {
	
	private static AtomicLong idCounter = new AtomicLong(1);
	
	private static long uniqueId() {
		return idCounter.incrementAndGet();
	}
	
	private long id;
	private String title;
	private String author;
	private LocalDateTime updated;
	
	public Book() {
		this.updated = LocalDateTime.now();
		this.id = uniqueId();
	}
	
	public Book(String title, String author) {
		this.title = title;
		this.author = author;
		this.updated = LocalDateTime.now();
		this.id = uniqueId();
	}
	
	public Book(Long id, String title, String author) {
		this.title = title;
		this.author = author;
		this.updated = LocalDateTime.now();
		this.id = id;
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
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", updated=" + updated + ", getId()="
				+ getId() + ", getTitle()=" + getTitle() + ", getAuthor()=" + getAuthor() + ", getUpdated()="
				+ getUpdated() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
