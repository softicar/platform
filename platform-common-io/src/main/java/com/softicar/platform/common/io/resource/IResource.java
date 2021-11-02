package com.softicar.platform.common.io.resource;

import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.hash.ResourceHash;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Represents a general resource.
 * <p>
 * Typical examples of resources include images, CSS files, and exports of
 * generated files.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface IResource {

	/**
	 * Returns this resource as an input stream.
	 * <p>
	 * When this resource is requested, the complete data returned by this input
	 * stream is sent to the requester.
	 *
	 * @return input stream with the content of this resource (may be
	 *         <i>null</i>)
	 */
	InputStream getResourceAsStream();

	/**
	 * Returns the mime type of this resource.
	 * <p>
	 * In case the mime type is unknown, {@link MimeType#getDefaultMimeType()}
	 * may be used.
	 *
	 * @return the mime type (never <i>null</i>)
	 */
	IMimeType getMimeType();

	/**
	 * Returns the character set (i.e. the textual encoding) of this resource,
	 * if any.
	 * <p>
	 * When a textual resource is converted back and forth between binary and
	 * text, the same character set must be used. This method defines which
	 * character set was used to convert the original text of this resource into
	 * binary data.
	 * <p>
	 * For example, the character set should be given to an {@link InputStream}
	 * reader, to convert the binary data into text.
	 * <p>
	 * Returns {@link Optional#empty()} for non-textual resources.
	 *
	 * @return the {@link Charset} that this resource was encoded with
	 */
	Optional<Charset> getCharset();

	/**
	 * Returns the filename of this resource, if any.
	 * <p>
	 * For example, if this resource is downloaded through a web browser, the
	 * return value of this method might be used to suggest a filename.
	 *
	 * @return the filename of this resource
	 */
	Optional<String> getFilename();

	/**
	 * Returns an optional {@link ResourceHash} for the content of this
	 * {@link IResource}.
	 * <p>
	 * The {@link ResourceHash} is over the data provided by the
	 * {@link InputStream} returned from {@link #getResourceAsStream()}. It
	 * uniquely identifies the content, that is, equal content always returns an
	 * equal {@link ResourceHash}. The actual hash algorithm is an
	 * implementation detail and might be a SHA1, MD5 or similar.
	 * <p>
	 * Since not every implementation is able to provide a {@link ResourceHash}
	 * over its data, the returned value is an {@link Optional} and might be
	 * empty.
	 *
	 * @return the optional {@link ResourceHash} over the content
	 */
	Optional<ResourceHash> getContentHash();
}
