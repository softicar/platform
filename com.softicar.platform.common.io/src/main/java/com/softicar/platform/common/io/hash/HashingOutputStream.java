package com.softicar.platform.common.io.hash;

import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.common.string.hash.Hash;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This {@link OutputStream} computes a hash over the data going through an
 * underlying output stream.
 *
 * @author Oliver Richers
 */
public class HashingOutputStream extends OutputStream {

	private final OutputStream outputStream;
	private final MessageDigest digest;
	private Consumer<HashingOutputStream> onCloseCallback;
	private long totalLength;
	private byte[] finalHash;

	/**
	 * Constructs this {@link HashingOutputStream} using the specified
	 * underlying {@link OutputStream} and the given {@link Hash} algorithm.
	 *
	 * @param outputStreamSupplier
	 *            a {@link Supplier} for the underlying {@link OutputStream}
	 *            (never <i>null</i>)
	 * @param hash
	 *            the {@link Hash} algorithm to use (never <i>null</i>)
	 */
	public HashingOutputStream(Supplier<OutputStream> outputStreamSupplier, Hash hash) {

		this.outputStream = outputStreamSupplier.get();
		this.digest = hash.createDigest();
		this.onCloseCallback = Consumers.noOperation();
		this.totalLength = 0;
		this.finalHash = null;
	}

	/**
	 * Defines an on-close callback for this {@link HashingOutputStream}.
	 * <p>
	 * When this {@link HashingOutputStream} is closed, the given
	 * {@link Consumer} is called with this {@link HashingOutputStream} as
	 * parameter. The callback is only called once, even if this
	 * {@link HashingOutputStream} is closed multiple times.
	 *
	 * @param onCloseCallback
	 *            the {@link Consumer} to call when this
	 *            {@link HashingOutputStream} is closed (never <i>null</i>)
	 */
	public void setOnCloseCallback(Consumer<HashingOutputStream> onCloseCallback) {

		this.onCloseCallback = Objects.requireNonNull(onCloseCallback);
	}

	/**
	 * Returns the hash over all bytes, gone through this output stream.
	 * <p>
	 * This stream must be closed before the final hash can be returned.
	 *
	 * @return the hash over the complete data
	 * @throws IllegalStateException
	 *             if this stream has not been closed, yet
	 */
	public byte[] getFinalHash() {

		if (finalHash == null) {
			throw new IllegalStateException("Stream must be closed before retrieving the final hash.");
		}

		return finalHash;
	}

	/**
	 * Returns the number of bytes gone through this output stream.
	 * <p>
	 * In contrast to {@link #getFinalHash()}, this method may be called at any
	 * time.
	 *
	 * @return the number of bytes gone through this stream
	 */
	public long getTotalLength() {

		return totalLength;
	}

	@Override
	public void flush() throws IOException {

		outputStream.flush();
	}

	@Override
	public void close() throws IOException {

		if (finalHash == null) {
			this.finalHash = digest.digest();

			outputStream.close();
			onCloseCallback.accept(this);
		}
	}

	@Override
	public void write(int b) throws IOException {

		++totalLength;
		digest.update((byte) b);
		outputStream.write(b);
	}

	@Override
	public void write(byte[] bytes) throws IOException {

		totalLength += bytes.length;
		digest.update(bytes);
		outputStream.write(bytes);
	}

	@Override
	public void write(byte[] bytes, int offset, int length) throws IOException {

		totalLength += length;
		digest.update(bytes, offset, length);
		outputStream.write(bytes, offset, length);
	}
}
