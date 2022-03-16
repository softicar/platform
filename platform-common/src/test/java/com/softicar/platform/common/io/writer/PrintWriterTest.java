package com.softicar.platform.common.io.writer;

import static org.junit.Assert.assertEquals;
import java.io.PrintWriter;
import java.io.Writer;
import org.junit.Test;

public class PrintWriterTest {

	protected static class MyWriter extends Writer {

		int closeCalls = 0;
		int flushCalls = 0;

		@Override
		public void close() {

			++closeCalls;
		}

		@Override
		public void flush() {

			++flushCalls;
		}

		@Override
		public void write(char[] cbuf, int off, int len) {

			// ignored
		}
	}

	@Test
	public void callsCloseOnUnderlyingWriter() {

		try (MyWriter writer = new MyWriter(); PrintWriter printWriter = new PrintWriter(writer)) {
			printWriter.close();

			assertEquals(1, writer.closeCalls);
		}
	}

	@Test
	public void callsFlushOnUnderlyingWriter() {

		try (MyWriter writer = new MyWriter(); PrintWriter printWriter = new PrintWriter(writer)) {
			printWriter.flush();

			assertEquals(1, writer.flushCalls);
		}
	}

	@Test
	public void allowsMultipleCloseCalls() {

		try (PrintWriter printWriter = new PrintWriter(new MyWriter())) {

			printWriter.close();
			printWriter.close();
			printWriter.close();
		}
	}

	@Test
	public void blocksSuperfluousCloseCalls() {

		try (MyWriter writer = new MyWriter(); PrintWriter printWriter = new PrintWriter(writer)) {

			printWriter.close();
			assertEquals(1, writer.closeCalls);

			printWriter.close();
			printWriter.close();
			assertEquals(1, writer.closeCalls);
		}
	}
}
