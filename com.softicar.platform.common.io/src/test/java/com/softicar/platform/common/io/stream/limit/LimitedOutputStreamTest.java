package com.softicar.platform.common.io.stream.limit;

import com.softicar.platform.common.core.utils.DevNull;
import java.io.ByteArrayOutputStream;
import org.junit.Assert;
import org.junit.Test;

public class LimitedOutputStreamTest extends Assert {

	private static final char A = 'A';
	private static final char B = 'B';
	private static final char C = 'C';
	private static final char D = 'D';
	private static final char E = 'E';
	private static final char F = 'F';
	private static final char G = 'G';
	private final ByteArrayOutputStream buffer;
	private final LimitedOutputStream ouputStream;

	public LimitedOutputStreamTest() {

		this.buffer = new ByteArrayOutputStream();
		this.ouputStream = new LimitedOutputStream(buffer, 6);

	}

	@Test
	public void testWriteByte() {

		try {
			for (int i = 0; i < 6; ++i) {
				ouputStream.write((byte) A + i);
			}
			ouputStream.write((byte) 'z');
			fail("Stream should have thrown an exception.");
		} catch (LimitedOutputStream.LimitReachedException exception) {
			DevNull.swallow(exception);
			assertArrayEquals(new byte[] { A, B, C, D, E, F }, buffer.toByteArray());
		} catch (Throwable throwable) {
			DevNull.swallow(throwable);
			fail("Stream threw wrong exception.");
		}
	}

	@Test
	public void testWriteByteArray() {

		try {
			ouputStream.write(new byte[] { A, B, C });
			ouputStream.write(new byte[] { D, E });
			ouputStream.write(new byte[] { F, G });
			fail("Stream should have thrown an exception.");
		} catch (LimitedOutputStream.LimitReachedException exception) {
			DevNull.swallow(exception);
			assertArrayEquals(new byte[] { A, B, C, D, E }, buffer.toByteArray());
		} catch (Throwable throwable) {
			DevNull.swallow(throwable);
			fail("Stream threw wrong exception.");
		}
	}

	@Test
	public void testWriteByteArraySubset() {

		try {
			byte[] data = new byte[] { A, B, C, D, E, F };
			ouputStream.write(data, 2, 4);
			ouputStream.write(data, 0, 4);
			fail("Stream should have thrown an exception.");
		} catch (LimitedOutputStream.LimitReachedException exception) {
			DevNull.swallow(exception);
			assertArrayEquals(new byte[] { C, D, E, F }, buffer.toByteArray());
		} catch (Throwable throwable) {
			DevNull.swallow(throwable);
			fail("Stream threw wrong exception.");
		}
	}
}
