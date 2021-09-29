package com.softicar.platform.common.io.stream.limit;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An {@link OutputStream} that limits the number of written bytes.
 *
 * @author Oliver Richers
 */
public class LimitedOutputStream extends OutputStream {

	private final OutputStream output;
	private final long limit;
	private long written;

	public LimitedOutputStream(OutputStream output, long limit) {

		this.output = output;
		this.limit = limit;
		this.written = 0;
	}

	@Override
	public void write(int singleByte) throws IOException {

		if (isInLimit(1)) {
			output.write(singleByte);
			written += 1;
		} else {
			throw new LimitReachedException();
		}
	}

	@Override
	public void write(byte[] bytes) throws IOException {

		if (isInLimit(bytes.length)) {
			output.write(bytes);
			written += bytes.length;
		} else {
			throw new LimitReachedException();
		}
	}

	@Override
	public void write(byte[] bytes, int offset, int length) throws IOException {

		if (isInLimit(length)) {
			output.write(bytes, offset, length);
			written += length;
		} else {
			throw new LimitReachedException();
		}
	}

	@Override
	public void flush() throws IOException {

		output.flush();
	}

	@Override
	public void close() throws IOException {

		output.close();
	}

	private boolean isInLimit(int length) {

		return written + length <= limit;
	}

	public static class LimitReachedException extends RuntimeException {

		public LimitReachedException() {

			super("Limit of output stream reached.");
		}
	}
}
