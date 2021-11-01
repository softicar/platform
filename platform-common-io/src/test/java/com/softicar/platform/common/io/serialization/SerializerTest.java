package com.softicar.platform.common.io.serialization;

import static org.junit.Assert.assertArrayEquals;
import java.io.ByteArrayOutputStream;
import org.junit.Test;

public class SerializerTest {

	@Test
	public void serializesIntsCorrectly() {

		checkInt(0, 0b00000000);
		checkInt(+1, 0b00000001);
		checkInt(-1, 0b01000001);
		checkInt(+63, 0b00111111);
		checkInt(-63, 0b01111111);
		checkInt(+64, 0b10000000, 0b00000001);
		checkInt(-64, 0b11000000, 0b00000001);
		checkInt(+8191, 0b10111111, 0b01111111);
		checkInt(-8191, 0b11111111, 0b01111111);
		checkInt(+8192, 0b10000000, 0b10000000, 0b00000001);
		checkInt(-8192, 0b11000000, 0b10000000, 0b00000001);
		checkInt(+2147483647, 0b10111111, 0b11111111, 0b11111111, 0b11111111, 0b00001111);
		checkInt(-2147483647, 0b11111111, 0b11111111, 0b11111111, 0b11111111, 0b00001111);
		checkInt(-2147483648, 0b11000000, 0b10000000, 0b10000000, 0b10000000, 0b00010000);
	}

	@Test
	public void serializesLongsCorrectly() {

		checkLong(0, 0b00000000);
		checkLong(+1, 0b00000001);
		checkLong(-1, 0b01000001);
		checkLong(+63, 0b00111111);
		checkLong(-63, 0b01111111);
		checkLong(+64, 0b10000000, 0b00000001);
		checkLong(-64, 0b11000000, 0b00000001);
		checkLong(+8191, 0b10111111, 0b01111111);
		checkLong(-8191, 0b11111111, 0b01111111);
		checkLong(+8192, 0b10000000, 0b10000000, 0b00000001);
		checkLong(-8192, 0b11000000, 0b10000000, 0b00000001);
		checkLong(+2147483647, 0b10111111, 0b11111111, 0b11111111, 0b11111111, 0b00001111);
		checkLong(-2147483647, 0b11111111, 0b11111111, 0b11111111, 0b11111111, 0b00001111);
		checkLong(+2147483648L, 0b10000000, 0b10000000, 0b10000000, 0b10000000, 0b00010000);
		checkLong(-2147483648L, 0b11000000, 0b10000000, 0b10000000, 0b10000000, 0b00010000);
		checkLong(+6000000000L, 0b10000000, 0b11110000, 0b10000101, 0b11011010, 0b00101100);
		checkLong(-6000000000L, 0b11000000, 0b11110000, 0b10000101, 0b11011010, 0b00101100);
	}

	private void checkInt(int value, int...expected) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try (Serializer serializer = new Serializer(outputStream)) {
			serializer.writeInt(value);
		}

		assertArrayEquals(convertToBytes(expected), outputStream.toByteArray());
	}

	private void checkLong(long value, int...expected) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try (Serializer serializer = new Serializer(outputStream)) {
			serializer.writeLong(value);
		}

		assertArrayEquals(convertToBytes(expected), outputStream.toByteArray());
	}

	private byte[] convertToBytes(int[] integers) {

		byte[] bytes = new byte[integers.length];
		for (int i = 0; i < integers.length; ++i) {
			bytes[i] = (byte) integers[i];
		}
		return bytes;
	}
}
