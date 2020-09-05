package se.anosh.forverkliga.apikey;

import java.security.SecureRandom;
import java.util.Base64;

public final class ApiKeyGenerator {
	
	public static String generateKey() {
		SecureRandom rnd = new SecureRandom();
		byte[] token = new byte[4];
		rnd.nextBytes(token);
		
		Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
		return encoder.encodeToString(token);
	}

}
