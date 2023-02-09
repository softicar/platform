package com.softicar.platform.dom.engine;

import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.mime.MimeType;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.function.Supplier;

/**
 * Represents the export of data through the {@link IDomEngine}.
 *
 * @author Oliver Richers
 */
public interface IDomExport {

	/**
	 * Defines the suggested filename of the export.
	 * <p>
	 * The given filename is only a suggestion for the user.
	 *
	 * @param filename
	 *            the suggested filename (never <i>null</i>)
	 * @return this
	 */
	IDomExport setFilename(String filename);

	/**
	 * Defines the {@link IMimeType} of the exported data.
	 * <p>
	 * The default is {@link MimeType#APPLICATION_OCTET_STREAM}.
	 *
	 * @param mimeType
	 *            the {@link IMimeType} (never <i>null</i>)
	 * @return this
	 */
	IDomExport setMimeType(IMimeType mimeType);

	/**
	 * Defines the {@link Charset} of the exported data.
	 * <p>
	 * The {@link Charset} is only relevant for textual data.
	 * <p>
	 * The default is <i>null</i>.
	 *
	 * @param charset
	 *            the {@link Charset} (may be <i>null</i>)
	 * @return this
	 */
	IDomExport setCharset(Charset charset);

	/**
	 * Returns an {@link OutputStream} to export the data to.
	 * <p>
	 * The caller must ensure that the returned {@link OutputStream} will be
	 * closed properly.
	 *
	 * @return the {@link OutputStream} (never <i>null</i>)
	 */
	OutputStream openOutputStream();

	default void write(Supplier<InputStream> inputStreamSupplier) {

		StreamUtils.copy(inputStreamSupplier, this::openOutputStream);
	}

	default void write(byte[] data) {

		write(() -> new ByteArrayInputStream(data));
	}
}
