
package com.softicar.platform.common.io.mime;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Enumeration of common MIME types.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public enum MimeType implements IMimeType {

	APPLICATION_JSON("application/json", "json"),
	APPLICATION_OCTET_STREAM("application/octet-stream"),
	APPLICATION_PDF("application/pdf", "pdf"),
	APPLICATION_VND_MS_OUTLOOK("application/vnd.ms-outlook", "msg"),
	APPLICATION_X_EXCEL("application/x-excel", "xls"),

	AUDIO_MPEG("audio/mpeg", "mpeg", "mpg"),
	AUDIO_OGG("audio/ogg", "ogg"),
	AUDIO_WAV("audio/wav", "wav"),

	IMAGE_BMP("image/bmp", "bmp"),
	IMAGE_GIF("image/gif", "gif"),
	IMAGE_JPEG("image/jpeg", "jpeg", "jpg"),
	IMAGE_PNG("image/png", "png"),
	IMAGE_SVG_XML("image/svg+xml", "svg"),
	IMAGE_TIFF("image/tiff", "tiff", "tif"),

	MESSAGE_RFC822("message/rfc822", "eml"),

	MULTIPART_FORM_DATA("multipart/form-data"),

	TEXT_CSS("text/css", "css"),
	TEXT_CSV("text/csv", "csv"),
	TEXT_HTML("text/html", "html"),
	TEXT_JAVASCRIPT("text/javascript", "js"),
	TEXT_PLAIN("text/plain", "txt"),
	TEXT_XML("text/xml", "xml"),

	//
	;

	private static final MimeType DEFAULT_MIME_TYPE = APPLICATION_OCTET_STREAM;
	private String superType;
	private String subType;
	private Collection<String> filenameSuffixes;

	private MimeType(String identifier, String...filenameSuffixes) {

		String[] parts = identifier.split("/");

		if (parts.length != 2) {
			throw new IllegalArgumentException("Illegal mime type identifier: " + identifier);
		}

		this.superType = parts[0];
		this.subType = parts[1];
		this.filenameSuffixes = List.of(filenameSuffixes);
	}

	@Override
	public String getIdentifier() {

		return superType + '/' + subType;
	}

	@Override
	public Collection<String> getParameters() {

		return Collections.emptyList();
	}

	@Override
	public Optional<String> getParameter(String parameter) {

		return Optional.empty();
	}

	@Override
	public String toString() {

		return getIdentifier();
	}

	/**
	 * Returns the default {@link #APPLICATION_OCTET_STREAM}.
	 *
	 * @return the default {@link MimeType} (never <i>null</i>)
	 */
	public static MimeType getDefaultMimeType() {

		return DEFAULT_MIME_TYPE;
	}

	/**
	 * Determines the {@link MimeType} from the given filename.
	 * <p>
	 * The filename is treated in case-insensitive manner.
	 *
	 * @param filename
	 *            the filename (never <i>null</i>)
	 * @return the matching {@link MimeType}, if any
	 */
	public static Optional<MimeType> getByFilename(String filename) {

		return new MimeTypeDeterminer(filename).getMimeType();
	}

	/**
	 * Same as {@link #getByFilename} but with a fallback of
	 * {@link #getDefaultMimeType()}.
	 *
	 * @return the matching {@link MimeType} or the default (never <i>null</i>)
	 */
	public static MimeType getByFilenameOrDefault(String filename) {

		return getByFilename(filename).orElse(getDefaultMimeType());
	}

	/**
	 * Determines the {@link MimeType} from the given filename suffix.
	 * <p>
	 * The suffix is treated in case-insensitive manner.
	 *
	 * @param filenameSuffix
	 *            the filename suffix (never <i>null</i>)
	 * @return the matching {@link MimeType}, if any
	 */
	public static Optional<MimeType> getByFilenameSuffix(String filenameSuffix) {

		return MimeTypeSuffixMap.getInstance().getMimeTypeForSuffix(filenameSuffix);
	}

	/**
	 * Returns the super type of this {@link MimeType}.
	 * <p>
	 * The super type is the identifier part before the slash.
	 *
	 * @return the super type (never <i>null</i>)
	 */
	public String getSuperType() {

		return superType;
	}

	/**
	 * Returns the sub-type of this {@link MimeType}.
	 * <p>
	 * The sub-type is the identifier part after the slash.
	 *
	 * @return the sub-type (never <i>null</i>)
	 */
	public String getSubType() {

		return subType;
	}

	/**
	 * Returns a list of all known filename suffixes for this {@link MimeType}.
	 *
	 * @return list of all filename suffixes (never <i>null</i>)
	 */
	public Collection<String> getFilenameSuffixes() {

		return filenameSuffixes;
	}
}
