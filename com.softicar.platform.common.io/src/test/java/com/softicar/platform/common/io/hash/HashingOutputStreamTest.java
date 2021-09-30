package com.softicar.platform.common.io.hash;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.softicar.platform.common.io.stream.test.TestOutputStream;
import com.softicar.platform.common.string.hash.Hash;
import java.io.IOException;
import java.util.function.Consumer;
import org.junit.Before;
import org.junit.Test;

public class HashingOutputStreamTest {

	private static final byte[] SOME_BYTES = new byte[] { 33, -15, 127, 1 };
	private static final byte[] EXPECTED_BYTES = new byte[] { 42, -128, 33, -15, 127, 1, -15, 127 };
	private TestOutputStream underlyingStream;
	private HashingOutputStream stream;
	private Consumer<HashingOutputStream> callback;

	@Before
	public void before() throws IOException {

		underlyingStream = new TestOutputStream();
		callback = mock(Consumer.class);
		stream = new HashingOutputStream(() -> underlyingStream, Hash.SHA1);
		stream.setOnCloseCallback(callback);

		stream.write(42);
		stream.write(-128);
		stream.write(SOME_BYTES);
		stream.write(SOME_BYTES, 1, 2);
	}

	@Test
	public void callsWriteOnUnderlyingStreamCorrectly() {

		assertArrayEquals(EXPECTED_BYTES, underlyingStream.toByteArray());
	}

	@Test
	public void sumsTotalLengthCorrectly() {

		assertEquals(EXPECTED_BYTES.length, stream.getTotalLength());
	}

	@Test
	public void computesCorrectHash() throws IOException {

		byte[] expectedDigest = Hash.SHA1.createDigest().digest(EXPECTED_BYTES);
		stream.close();
		assertArrayEquals(expectedDigest, stream.getFinalHash());
	}

	@Test(expected = IllegalStateException.class)
	public void throwsIfRetrievingHashOnOpenStream() {

		stream.getFinalHash();
	}

	@Test
	public void testClose() throws IOException {

		stream.close();

		verify(callback).accept(stream);
		underlyingStream.assertWasClosedOnce();
	}

	@Test
	public void testCloseMultipleTimes() throws IOException {

		stream.close();
		stream.close();
		stream.close();

		verify(callback, times(1)).accept(stream);
		underlyingStream.assertWasClosedOnce();
	}

	@Test
	public void testCloseWithoutCallback() throws IOException {

		stream = new HashingOutputStream(() -> underlyingStream, Hash.SHA1);
		stream.close();
	}
}
