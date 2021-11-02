package com.softicar.platform.ajax.export;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * A buffer to store data of exports.
 * 
 * @author Oliver Richers
 */
public interface IAjaxExportBuffer {

	/**
	 * Opens this buffer for writing.
	 * <p>
	 * This method may only be called once.
	 * 
	 * @return an {@link OutputStream} for writing into this buffer
	 */
	OutputStream openForWriting();

	/**
	 * Opens this buffer for reading.
	 * <p>
	 * This method may only be called once and only after closing the
	 * {@link OutputStream} returned by {@link #openForWriting()}.
	 * 
	 * @return an {@link InputStream} to read the data from this buffer
	 */
	InputStream openForReading();

	/**
	 * Frees allocated resourced.
	 * <p>
	 * This method may only be called once and after after returning, no other
	 * method may be called on this buffer.
	 */
	void dispose();
}
