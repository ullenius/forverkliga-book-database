package se.anosh.forverkliga;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import se.anosh.forverkliga.apikey.*;

class TestGeneratedApiKey {

	@Test
	void testKeyLength() {
		
		String key = ApiKeyGenerator.generateKey();
		assertNotNull(key);
		assertEquals(ApiKey.length(), key.length());
	}
	
	@Test
	void testUniqueKeys() {
		
		String one;
		String two;
		one = ApiKeyGenerator.generateKey();
		two = ApiKeyGenerator.generateKey();
		
		assertNotSame(one, two);
		assertNotEquals(one,two);
	}

}
