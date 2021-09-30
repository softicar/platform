package com.softicar.platform.common.io.stream;

import com.softicar.platform.common.io.StreamUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A delegating {@link OutputStream} that switches to a fall-back if necessary.
 * <p>
 * Initially, this {@link OutputStream} delegates all output to another
 * {@link OutputStream}. After reaching a specific limit of written bytes, this
 * stream switches to an alternative {@link OutputStream}.
 * <p>
 * This class is useful to implement a limited memory-based buffer that switches
 * to a file-based buffering if the amount of data exceeds a specific limit.
 *
 * @author Oliver Richers
 */
public class SwitchingOutputStream extends OutputStream {

	private final int maximumSize;
	private final OutputStream memoryOutputStream;
	private final Supplier<OutputStream> alternativeOutputStreamFactory;
	private int size;
	private OutputStream alternativeOutputStream;

	/**
	 * Constructs a new instance of this class.
	 * <p>
	 * The given factory for the alternative {@link OutputStream} is responsible
	 * to close the initial {@link OutputStream} and to copy all data from the
	 * initial to the alternative {@link OutputStream}.
	 *
	 * @param maximumSize
	 *            the maximum amount of bytes that may be written to the initial
	 *            {@link OutputStream}
	 * @param initialOutputStream
	 *            the initial {@link OutputStream}
	 * @param alternativeOutputStreamFactory
	 *            the switching factory, providing the alternative
	 *            {@link OutputStream}
	 */
	public SwitchingOutputStream(int maximumSize, OutputStream initialOutputStream, Supplier<OutputStream> alternativeOutputStreamFactory) {

		if (maximumSize < 0) {
			throw new IllegalArgumentException("Maximum size may not be negative.");
		}

		this.maximumSize = maximumSize;
		this.memoryOutputStream = initialOutputStream;
		this.alternativeOutputStreamFactory = alternativeOutputStreamFactory;
		this.size = 0;
		this.alternativeOutputStream = null;
	}

	@Override
	@SuppressWarnings("resource")
	public void write(int b) throws IOException {

		getOutputStream(1).write(b);
		this.size += 1;
	}

	@Override
	@SuppressWarnings("resource")
	public void write(byte[] b, int off, int len) throws IOException {

		getOutputStream(len).write(b, off, len);
		this.size += len;
	}

	@Override
	public void write(byte[] b) throws IOException {

		write(b, 0, b.length);
	}

	@Override
	@SuppressWarnings("resource")
	public void flush() throws IOException {

		getOutputStream(0).flush();
	}

	@Override
	public void close() throws IOException {

		memoryOutputStream.close();

		Optional//
			.ofNullable(alternativeOutputStream)
			.ifPresent(StreamUtils::close);
	}

	private OutputStream getOutputStream(int bytesToWrite) {

		if (alternativeOutputStream != null) {
			return alternativeOutputStream;
		} else if (size + bytesToWrite <= maximumSize) {
			return memoryOutputStream;
		} else {
			return switchToFallback();
		}
	}

	private OutputStream switchToFallback() {

		this.alternativeOutputStream = alternativeOutputStreamFactory.get();
		return alternativeOutputStream;
	}
}
