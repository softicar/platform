package com.softicar.platform.common.io.stream;

import com.softicar.platform.common.io.stream.test.TestOutputStream;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

public class SwitchingOutputStreamTest extends Assert {

	private static final int LIMIT = 3;
	private final TestOutputStream initalStream;
	private final TestOutputStream alternativeStream;
	private final SwitchingOutputStream outputStream;

	public SwitchingOutputStreamTest() {

		this.initalStream = new TestOutputStream();
		this.alternativeStream = new TestOutputStream();
		this.outputStream = new SwitchingOutputStream(LIMIT, initalStream, () -> alternativeStream);
	}

	@Test
	public void testRightBelowLimit() throws IOException {

		outputStream.write(1);
		outputStream.write(2);
		outputStream.write(3);
		outputStream.close();

		assertEquals(3, initalStream.size());
		assertEquals(0, alternativeStream.size());
		assertArrayEquals(new byte[] { 1, 2, 3 }, initalStream.toByteArray());
		initalStream.assertWasClosedOnce();
	}

	@Test
	public void testOneAboveLimit() throws IOException {

		outputStream.write(1);
		outputStream.write(2);
		outputStream.write(3);
		outputStream.write(4);
		outputStream.close();

		assertEquals(3, initalStream.size());
		assertEquals(1, alternativeStream.size());
		assertArrayEquals(new byte[] { 1, 2, 3 }, initalStream.toByteArray());
		assertArrayEquals(new byte[] { 4 }, alternativeStream.toByteArray());
		alternativeStream.assertWasClosedOnce();
	}

	@Test
	public void testBurstAboveLimit() throws IOException {

		outputStream.write(new byte[] { 1, 2, 3 });
		outputStream.write(new byte[] { 4, 5, 6 });
		outputStream.close();

		assertEquals(3, initalStream.size());
		assertEquals(3, alternativeStream.size());
		assertArrayEquals(new byte[] { 1, 2, 3 }, initalStream.toByteArray());
		assertArrayEquals(new byte[] { 4, 5, 6 }, alternativeStream.toByteArray());
		alternativeStream.assertWasClosedOnce();
	}
}
