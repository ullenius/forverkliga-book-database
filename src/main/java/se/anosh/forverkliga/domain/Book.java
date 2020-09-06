package se.anosh.forverkliga.domain;

import java.time.LocalDateTime;
import java.util.Objects;
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

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", updated=" + updated + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return id == other.id;
	}
	
}
