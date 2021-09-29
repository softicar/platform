package com.softicar.platform.common.io;

import com.softicar.platform.common.io.stream.hexadecimal.UnexpectedEndOfHexadecimalStreamException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class HexDecoderStreamTest extends Assert {

	@Test
	public void testReadOnEmptyStream() {

		byte[] input = new byte[0];
		try (HexDecoderStream stream = new HexDecoderStream(new ByteArrayInputStream(input))) {
			assertEquals(-1, stream.read());
		}
	}

	@Test(expected = UnexpectedEndOfHexadecimalStreamException.class)
	public void testReadOnInvalidStream() {

		byte[] input = "a".getBytes();
		try (HexDecoderStream stream = new HexDecoderStream(new ByteArrayInputStream(input))) {
			stream.read();
		}
	}

	public void testReadOnMinimalStream() {

		byte[] input = "aa".getBytes();
		try (HexDecoderStream stream = new HexDecoderStream(new ByteArrayInputStream(input))) {
			assertEquals(0xAA, stream.read());
			assertEquals(-1, stream.read());
		}
	}

	public void testRead() {

		byte[] input = "0012A8ffeB".getBytes();
		try (HexDecoderStream stream = new HexDecoderStream(new ByteArrayInputStream(input))) {
			assertEquals(0x00, stream.read());
			assertEquals(0x12, stream.read());
			assertEquals(0xa8, stream.read());
			assertEquals(0xff, stream.read());
			assertEquals(0xeb, stream.read());
			assertEquals(-1, stream.read());
		}
	}

	@Test
	public void testClose() throws IOException {

		@SuppressWarnings("resource")
		InputStream inputStream = Mockito.mock(InputStream.class);

		try (HexDecoderStream dummy = new HexDecoderStream(inputStream)) {
			// nothing to do
		}

		Mockito.verify(inputStream).close();
	}
}
