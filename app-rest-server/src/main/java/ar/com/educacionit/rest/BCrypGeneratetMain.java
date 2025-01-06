package ar.com.educacionit.rest;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Version;

public class BCrypGeneratetMain {

	public static void main(String[] args) {
		byte[] hash = BCrypt.with(Version.VERSION_2Y).hash(10, "1234".getBytes());
		System.out.println(new String(hash));
	}
}
