package com.softicar.platform.common.string.binary;

import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.common.testing.Asserts;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.Test;

public class BinaryOrTextDiscriminatorTest extends Asserts {

	private static final String NON_ASCII_TEXT = "Der Bär denkt, daß es schön würde.";

	@Test
	public void testWithEmptyArray() {

		assertText(new byte[0]);
	}

	@Test
	public void testWithSingleByte() {

		// printable control characters
		assertText(toArray('\b'));
		assertText(toArray('\f'));
		assertText(toArray('\n'));
		assertText(toArray('\r'));
		assertText(toArray('\t'));

		// non-ASCII characters
		assertText(toLatin1("ä"));
		assertText(toLatin1("Ä"));
		assertText(toLatin1("ö"));
		assertText(toLatin1("Ö"));
		assertText(toLatin1("ü"));
		assertText(toLatin1("Ü"));
		assertText(toLatin1("ß"));

		// non-printable control characters
		assertBinary(toArray(0));
		assertBinary(toArray(1));
		assertBinary(toArray(2));
		assertBinary(toArray(31));
		assertBinary(toArray(127));
	}

	@Test
	public void testWithLongerTexts() {

		assertText(toUtf8(NON_ASCII_TEXT));
		assertText(toLatin1(NON_ASCII_TEXT));
		assertText(concat(toLatin1(NON_ASCII_TEXT), toArray('\t')));
		assertText(concat(toLatin1(NON_ASCII_TEXT), toArray('\n')));
		assertText(concat(toLatin1(NON_ASCII_TEXT), toArray('\r', '\n')));

		assertBinary(concat(toLatin1(NON_ASCII_TEXT), toArray(0)));
		assertBinary(concat(toLatin1(NON_ASCII_TEXT), toArray(127)));
	}

	private byte[] toArray(int...bytes) {

		var buffer = new ByteArrayOutputStream();
		for (int b: bytes) {
			buffer.write(b);
		}
		return buffer.toByteArray();
	}

	private byte[] toUtf8(String text) {

		return text.getBytes(Charsets.UTF8);
	}

	private byte[] toLatin1(String text) {

		return text.getBytes(Charsets.ISO_8859_1);
	}

	private byte[] concat(byte[]...arrays) {

		var buffer = new ByteArrayOutputStream();
		for (byte[] array: arrays) {
			StreamUtils.copy(new ByteArrayInputStream(array), buffer);
		}
		return buffer.toByteArray();
	}

	private void assertText(byte[] bytes) {

		assertTrue("expected text but was binary", new BinaryOrTextDiscriminator(bytes).isText());
		assertFalse("expected text but was binary", new BinaryOrTextDiscriminator(bytes).isBinary());
	}

	private void assertBinary(byte[] bytes) {

		assertFalse("expected binary but was text", new BinaryOrTextDiscriminator(bytes).isText());
		assertTrue("expected binary but was text", new BinaryOrTextDiscriminator(bytes).isBinary());
	}
}
