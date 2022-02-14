package com.softicar.platform.dom.event.upload;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.function.Function;

/**
 * Represents a file upload.
 *
 * @author Oliver Richers
 */
public interface IDomFileUpload {

	/**
	 * The name of the file that is being uploaded.
	 * <p>
	 * This is usually the filename without path on Linux.
	 *
	 * @return the filename
	 */
	String getFilename();

	/**
	 * Returns an input stream with the file content.
	 * <p>
	 * This method may only be called once.
	 *
	 * @return the file input stream (never <i>null</i>)
	 */
	InputStream getStream();

	/**
	 * Returns the content type of the uploaded file.
	 *
	 * @return the content type or null
	 */
	String getContentType();

	/**
	 * Calls {@link #getStream()} and reads and converts the content using the
	 * provided converter function.
	 * <p>
	 * The content stream is closed automatically.
	 * <p>
	 * This method may only be called once.
	 *
	 * @param <T>
	 *            the result type
	 * @param converter
	 *            the converter function (never <i>null</i>)
	 * @return the return value of the converter (nullability depends on the
	 *         converter function)
	 */
	default <T> T getContent(Function<InputStream, T> converter) {

		try (InputStream stream = getStream()) {
			return converter.apply(stream);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	/**
	 * Reads the content as a byte array.
	 * <p>
	 * This method may only be called once.
	 *
	 * @return the content as byte array (never <i>null</i>)
	 */
	default byte[] getContentAsBytes() {

		return getContent(stream -> StreamUtils.toByteArray(stream));
	}

	/**
	 * Reads the content as a {@link String}.
	 * <p>
	 * This method may only be called once.
	 *
	 * @param charset
	 *            the character set to use (never <i>null</i>)
	 * @return the content as {@link String} (never <i>null</i>)
	 */
	default String getContentAsString(Charset charset) {

		return getContent(stream -> StreamUtils.toString(stream, charset));
	}
}
