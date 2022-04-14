package com.softicar.platform.common.io.serialization;

import com.softicar.platform.common.testing.AbstractTest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;

public class SerializerDeserializerTest extends AbstractTest {

	private static final byte[] BYTES = { -128, -127, -1, 0, 1, 127 };
	private ByteArrayOutputStream outputStream;
	private Serializer serializer;

	@Before
	public void before() {

		outputStream = new ByteArrayOutputStream();
		serializer = new Serializer(outputStream);
	}

	@Test
	public void testBoolean() {

		// write
		serializer.writeBoolean(true);
		serializer.writeBoolean(false);

		// read and check
		try (Deserializer deserializer = createDeserializer()) {
			assertTrue(deserializer.readBoolean());
			assertFalse(deserializer.readBoolean());
		}
	}

	@Test
	public void testByte() {

		// write
		for (byte b: BYTES) {
			serializer.writeByte(b);
		}

		// read and check
		try (Deserializer deserializer = createDeserializer()) {
			for (byte b: BYTES) {
				assertEquals(b, deserializer.readByte());
			}
		}
	}

	@Test
	public void testByteArray() {

		// write
		serializer.writeByteArray(BYTES);
		serializer.writeByteArray(BYTES);

		// read and check
		try (Deserializer deserializer = createDeserializer()) {
			assertArrayEquals(BYTES, deserializer.readByteArray());
			assertArrayEquals(BYTES, deserializer.readByteArray());
		}
	}

	@Test
	public void testInt() {

		for (int i = -300; i < 300; ++i) {
			testInt(i);
		}

		testInt(-2147483648);
		testInt(2147483647);
	}

	private void testInt(int valueIn) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try (Serializer serializer = new Serializer(outputStream)) {
			serializer.writeInt(valueIn);
		}

		int valueOut = 0;
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		try (Deserializer deserializer = new Deserializer(inputStream)) {
			valueOut = deserializer.readInt();
		}

		assertEquals(valueIn, valueOut);
	}

	@Test
	public void testLong() {

		for (int i = -300; i < 300; ++i) {
			testLong(i);
		}

		testLong(-6000000000L);
		testLong(-2147483648L);
		testLong(2147483647L);
		testLong(6000000000L);
	}

	private void testLong(long valueIn) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try (Serializer serializer = new Serializer(outputStream)) {
			serializer.writeLong(valueIn);
		}

		long valueOut;
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		try (Deserializer deserializer = new Deserializer(inputStream)) {
			valueOut = deserializer.readLong();
		}

		assertEquals(valueIn, valueOut);
	}

	@Test
	public void serializesFieldsCorrectly() {

		TestData dataIn = new TestData();
		dataIn.b = 33;
		dataIn.s = 4000;
		dataIn.i = 12345678;
		dataIn.l = 60000000000L;
		dataIn.a = BYTES;
		dataIn.t = "hello";
		dataIn.e = TestEnum.B;
		serializer.writeFields(dataIn);

		TestData dataOut = new TestData();
		try (Deserializer deserializer = createDeserializer()) {
			deserializer.readFields(dataOut);
		}

		assertEquals(dataIn.b, dataOut.b);
		assertEquals(dataIn.s, dataOut.s);
		assertEquals(dataIn.i, dataOut.i);
		assertEquals(dataIn.l, dataOut.l);
		assertArrayEquals(dataIn.a, dataOut.a);
		assertEquals(dataIn.t, dataOut.t);
		assertEquals(dataIn.e, dataOut.e);
	}

	private Deserializer createDeserializer() {

		return new Deserializer(new ByteArrayInputStream(outputStream.toByteArray()));
	}
}
