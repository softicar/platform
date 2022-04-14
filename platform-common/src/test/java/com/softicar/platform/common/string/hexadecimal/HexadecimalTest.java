package com.softicar.platform.common.string.hexadecimal;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * Test cases for {@link Hexadecimal}.
 *
 * @author Robert Kühne
 * @author Oliver Richers
 */
public class HexadecimalTest extends AbstractTest {

	@Test
	public void getHexEmpty() {

		String s = Hexadecimal.getHexStringLC(new byte[] {});

		assertEquals("", s);
	}

	@Test
	public void getHexSeveralBytes() {

		String s = Hexadecimal.getHexStringLC(new byte[] { (byte) 0xFF, (byte) 0xAA, (byte) 0xFF, (byte) 0xAA });

		assertEquals("ffaaffaa", s);
	}

	@Test
	public void getHex00() {

		String s = Hexadecimal.getHexStringLC(new byte[] { 0 });

		assertEquals("00", s);
	}

	@Test
	public void getHexFF() {

		String s = Hexadecimal.getHexStringLC(new byte[] { (byte) 0xFF });

		assertEquals("ff", s);
	}

	@Test
	public void getHexA0() {

		String s = Hexadecimal.getHexStringLC(new byte[] { (byte) 0xA0 });

		assertEquals("a0", s);
	}

	@Test
	public void getHexUCEmpty() {

		String s = Hexadecimal.getHexStringUC(new byte[] {});

		assertEquals("", s);
	}

	@Test
	public void getHexUCSeveralBytes() {

		String s = Hexadecimal.getHexStringUC(new byte[] { (byte) 0xFF, (byte) 0xAA, (byte) 0xFF, (byte) 0xAA });

		assertEquals("FFAAFFAA", s);
	}

	@Test
	public void getHexUC00() {

		String s = Hexadecimal.getHexStringUC(new byte[] { 0 });

		assertEquals("00", s);
	}

	@Test
	public void getHexUCFF() {

		String s = Hexadecimal.getHexStringUC(new byte[] { (byte) 0xFF });

		assertEquals("FF", s);
	}

	@Test
	public void getHexUCA0() {

		String s = Hexadecimal.getHexStringUC(new byte[] { (byte) 0xA0 });

		assertEquals("A0", s);
	}

	@Test
	public void getBytesFromHexString() {

		String input = "0123456789ABCDEFabcdefFF00";
		byte[] expectedBytes =
				{ 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, (byte) 0xab, (byte) 0xcd, (byte) 0xef, (byte) 0xFF, 0x00 };

		byte[] bytes = Hexadecimal.getBytesFromHexString(input);

		assertArrayEquals(expectedBytes, bytes);
	}

	@Test
	public void getBytesFromHexStringConverting() {

		String input =
				"474C4F42414C2E617070726F76654E6F646556616C75657328293B0A616C6572742827676F742066696C653A2067656E65726963732D7475746F7269616C2E70646627293B0A";

		byte[] bytes = Hexadecimal.getBytesFromHexString(input);

		assertEquals(input.toUpperCase(), Hexadecimal.getHexStringUC(bytes));
		assertEquals(input.toLowerCase(), Hexadecimal.getHexStringLC(bytes));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getBytesFromHexStringThrowsOnIllegalNibble() {

		Hexadecimal.getBytesFromHexString("xx");

	}

	@Test(expected = IllegalArgumentException.class)
	public void getBytesFromHexStringThrowsOnIllegalLength() {

		Hexadecimal.getBytesFromHexString("abc");
	}
}
