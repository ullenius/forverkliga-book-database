package se.anosh.forverkliga;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.anosh.forverkliga.domain.Book;

class TestBook {

	@Test
	void testUniqueBookId() {
		
		Book book1 = new Book();
		Book book2 = new Book();
		
		assertNotEquals(book1.getId(), book2.getId());
	}

}
