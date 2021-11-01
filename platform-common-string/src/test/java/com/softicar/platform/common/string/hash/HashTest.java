package com.softicar.platform.common.string.hash;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test cases for {@link Hash}.
 *
 * @author Oliver Richers
 */
public class HashTest {

	@Test
	public void testMd5() {

		String empty = "d41d8cd98f00b204e9800998ecf8427e";
		assertEquals(empty.toLowerCase(), Hash.MD5.getHashStringLC(""));
		assertEquals(empty.toUpperCase(), Hash.MD5.getHashStringUC(""));

		String hello = "5d41402abc4b2a76b9719d911017c592";
		assertEquals(hello.toLowerCase(), Hash.MD5.getHashStringLC("hello"));
		assertEquals(hello.toUpperCase(), Hash.MD5.getHashStringUC("hello"));
	}

	@Test
	public void tesSha1() {

		String empty = "da39a3ee5e6b4b0d3255bfef95601890afd80709";
		assertEquals(empty.toLowerCase(), Hash.SHA1.getHashStringLC(""));
		assertEquals(empty.toUpperCase(), Hash.SHA1.getHashStringUC(""));

		String hello = "aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d";
		assertEquals(hello.toLowerCase(), Hash.SHA1.getHashStringLC("hello"));
		assertEquals(hello.toUpperCase(), Hash.SHA1.getHashStringUC("hello"));
	}
}
