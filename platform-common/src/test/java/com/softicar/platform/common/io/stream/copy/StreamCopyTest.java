package com.softicar.platform.common.io.stream.copy;

import com.softicar.platform.common.string.unicode.Utf8Convering;
import com.softicar.platform.common.testing.Asserts;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.Test;

public class StreamCopyTest extends Asserts {

	private static final String TEST_DATA = "Hello, world!";

	@Test
	public void test() {

		var inputStream = new TestInputStream(TEST_DATA);
		var outputStream = new TestOutputStream();

		new StreamCopy(inputStream, outputStream).copyAndClose();

		outputStream.assertContent(TEST_DATA);
		inputStream.assertReadCalls(2);
	}

	@Test
	public void testWithCustomBufferSize() {

		var bufferSize = 3;
		var inputStream = new TestInputStream(TEST_DATA);
		var outputStream = new TestOutputStream();

		new StreamCopy(inputStream, outputStream).setBufferSize(bufferSize).copyAndClose();

		outputStream.assertContent(TEST_DATA);
		inputStream.assertReadCalls(6);
	}

	@Test
	public void testWithThrowOnRead() {

		var outputStream = new TestOutputStream();

		assertException(StreamCopyInputException.class, () -> new StreamCopy(new ThrowingInputputStream(), outputStream).copyAndClose());

		outputStream.assertContent("");
	}

	@Test
	public void testWithThrowOnWrite() {

		var inputStream = new TestInputStream(TEST_DATA);

		assertException(StreamCopyOutputException.class, () -> new StreamCopy(inputStream, new ThrowingOutputputStream()).copyAndClose());

		inputStream.assertReadCalls(1);
	}

	// ------------------------------ utility classes ------------------------------ //

	private static class TestInputStream extends ByteArrayInputStream {

		private int readCalls;

		public TestInputStream(String content) {

			super(Utf8Convering.toUtf8(content));
			this.readCalls = 0;
		}

		@Override
		public int read(byte[] buffer) throws IOException {

			readCalls++;
			return super.read(buffer);
		}

		public void assertReadCalls(int expectedCalls) {

			assertEquals("read calls", expectedCalls, readCalls);
		}
	}

	private static class TestOutputStream extends ByteArrayOutputStream {

		public void assertContent(String expectedContent) {

			assertEquals(expectedContent, Utf8Convering.fromUtf8(toByteArray()));
		}
	}

	private static class ThrowingInputputStream extends InputStream {

		@Override
		public int read() throws IOException {

			throw new IOException();
		}
	}

	private static class ThrowingOutputputStream extends OutputStream {

		@Override
		public void write(int buffer) throws IOException {

			throw new IOException();
		}
	}
}
