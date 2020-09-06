package se.anosh.forverkliga;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.anosh.forverkliga.apikey.ApiKey;
import se.anosh.forverkliga.apikey.ApiKeyGenerator;

class TestGeneratedApiKey {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testKeyLength() {
		
		String key = ApiKeyGenerator.generateKey();
		assertNotNull(key);
		assertEquals(key.length(),ApiKey.length());
	}
	
	@Test
	void testUniqueKeys() {
		
		String one, two;
		one = ApiKeyGenerator.generateKey();
		two = ApiKeyGenerator.generateKey();
		
		assertNotSame(one, two);
		assertNotEquals(one,two);
	}

}
