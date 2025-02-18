package com.skillbox.cryptobot;

import org.junit.jupiter.api.Test;

class CryptoBotApplicationTests {

	@Test
	void contextLoads() {
		String t = "/subscribe 9999999.12";
		System.out.println(t.replaceAll("(/subscribe|\\s)", ""));
	}
}
