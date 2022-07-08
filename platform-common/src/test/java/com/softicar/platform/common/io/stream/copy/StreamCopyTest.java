package com.softicar.platform.common.io.stream.copy;

import com.softicar.platform.common.string.unicode.Utf8Convering;
import com.softicar.platform.common.testing.Asserts;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.Test;

@SuppressWarnings("resource")
public class StreamCopyTest extends Asserts {

	private static final String TEST_DATA = "Hello, world!";

	@Test
	public void test() {

		var inputStream = new TestInputStream(TEST_DATA);
		var outputStream = new TestOutputStream();

		new StreamCopy(inputStream, outputStream).copyAndClose();

		outputStream.assertContent(TEST_DATA);
		inputStream.assertReadCalls(2);
		assertAllClosed(inputStream, outputStream);
	}

	@Test
	public void testWithCustomBufferSize() {

		var bufferSize = 3;
		var inputStream = new TestInputStream(TEST_DATA);
		var outputStream = new TestOutputStream();

		new StreamCopy(inputStream, outputStream).setBufferSize(bufferSize).copyAndClose();

		outputStream.assertContent(TEST_DATA);
		inputStream.assertReadCalls(6);
		assertAllClosed(inputStream, outputStream);
	}

	@Test
	public void testWithThrowOnRead() {

		var inputStream = new TestInputStream(TEST_DATA).setThrowOnRead(true);
		var outputStream = new TestOutputStream();

		assertException(StreamCopyInputException.class, () -> new StreamCopy(inputStream, outputStream).copyAndClose());

		inputStream.assertReadCalls(1);
		outputStream.assertContent("");
		assertAllClosed(inputStream, outputStream);
	}

	@Test
	public void testWithThrowOnWrite() {

		var inputStream = new TestInputStream(TEST_DATA);
		var outputStream = new TestOutputStream().setThrowOnWrite(true);

		assertException(StreamCopyOutputException.class, () -> new StreamCopy(inputStream, outputStream).copyAndClose());

		inputStream.assertReadCalls(1);
		outputStream.assertContent("");
		assertAllClosed(inputStream, outputStream);
	}

	@Test
	public void testWithExceptionWhenClosingInput() {

		var inputStream = new TestInputStream(TEST_DATA).setThrowOnClose(true);
		var outputStream = new TestOutputStream();

		assertException(StreamCopyInputException.class, () -> new StreamCopy(inputStream, outputStream).copyAndClose());

		inputStream.assertReadCalls(2);
		outputStream.assertContent(TEST_DATA);
		assertAllClosed(inputStream, outputStream);
	}

	@Test
	public void testWithExceptionWhenClosingOutput() {

		var inputStream = new TestInputStream(TEST_DATA);
		var outputStream = new TestOutputStream().setThrowOnClose(true);

		assertException(StreamCopyOutputException.class, () -> new StreamCopy(inputStream, outputStream).copyAndClose());

		inputStream.assertReadCalls(2);
		outputStream.assertContent(TEST_DATA);
		assertAllClosed(inputStream, outputStream);
	}

	// ------------------------------ utility  ------------------------------ //

	private void assertAllClosed(TestInputStream inputStream, TestOutputStream outputStream) {

		inputStream.assertClosed();
		outputStream.assertClosed();
	}

	private static class TestInputStream extends ByteArrayInputStream {

		private boolean throwOnRead;
		private boolean throwOnClose;
		private boolean closed;
		private int readCalls;

		public TestInputStream(String content) {

			super(Utf8Convering.toUtf8(content));
		}

		@Override
		public int read(byte[] buffer) throws IOException {

			readCalls++;

			if (throwOnRead) {
				throw new IOException();
			} else {
				return super.read(buffer);
			}
		}

		@Override
		public void close() throws IOException {

			this.closed = true;

			if (throwOnClose) {
				throw new IOException();
			} else {
				super.close();
			}
		}

		public TestInputStream setThrowOnRead(boolean throwOnRead) {

			this.throwOnRead = throwOnRead;
			return this;
		}

		public TestInputStream setThrowOnClose(boolean throwOnClose) {

			this.throwOnClose = throwOnClose;
			return this;
		}

		public void assertClosed() {

			assertTrue("closed", closed);
		}

		public void assertReadCalls(int expectedCalls) {

			assertEquals("read calls", expectedCalls, readCalls);
		}
	}

	private static class TestOutputStream extends OutputStream {

		private final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		private boolean throwOnWrite;
		private boolean throwOnClose;
		private boolean closed;

		@Override
		public void write(int buffer) throws IOException {

			if (throwOnWrite) {
				throw new IOException();
			} else {
				stream.write(buffer);
			}
		}

		@Override
		public void close() throws IOException {

			this.closed = true;

			if (throwOnClose) {
				throw new IOException();
			}
		}

		public TestOutputStream setThrowOnWrite(boolean throwOnWrite) {

			this.throwOnWrite = throwOnWrite;
			return this;
		}

		public TestOutputStream setThrowOnClose(boolean throwOnClose) {

			this.throwOnClose = throwOnClose;
			return this;
		}

		public void assertClosed() {

			assertTrue("closed", closed);
		}

		public void assertContent(String expectedContent) {

			assertEquals(expectedContent, Utf8Convering.fromUtf8(stream.toByteArray()));
		}
	}
}
