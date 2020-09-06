package se.anosh.forverkliga;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import se.anosh.forverkliga.domain.Book;

class TestBook {

	private Book book1;
	private Book book2;

	@BeforeEach
	void setup() {

		book1 = new Book();
		book2 = new Book();
	}

	@Test
	void testUniqueBookId() {

		assertNotEquals(book1.getId(), book2.getId());
	}

	@Test
	void testDifferentHashCodes() {
		assertNotEquals(book1.hashCode(), book2.hashCode());
	}

	@Test
	void testIdenticalHashCodes() {

		Book book1 = new Book("The Old Man and the Sea", "Hemingway");
		long id = book1.getId();
		Book book2 = new Book(id, "A Moveable Feast", "Hemingway");

		assertEquals(book1.hashCode(), book2.hashCode());

	}

	@Test
	void testEquals() {
		Book book1 = new Book("The Old Man and the Sea", "Hemingway");
		long id = book1.getId();
		Book book2 = new Book(id, "A Moveable Feast", "Hemingway");
		assertEquals(book1, book2);
	}


	@Test
	void testNonEquals() {
		Book book1 = new Book();
		Book book2 = new Book();
		assertNotSame(book1, book2);
		assertNotEquals(book1, book2);
	}

}
