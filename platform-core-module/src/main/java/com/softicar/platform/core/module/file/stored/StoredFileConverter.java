package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.core.module.email.converter.EmailToPdfConverter;
import java.util.Objects;
import java.util.Optional;

/**
 * Facilitates conversion of {@link AGStoredFile} records.
 * <p>
 * Instantiated via {@link AGStoredFile#convert()}
 *
 * @author Alexander Schmidt
 */
public class StoredFileConverter {

	private final AGStoredFile file;

	/**
	 * Constructs a new {@link StoredFileConverter}.
	 *
	 * @param file
	 *            the {@link AGStoredFile} to convert(never <i>null</i>)
	 */
	StoredFileConverter(AGStoredFile file) {

		this.file = Objects.requireNonNull(file);
	}

	/**
	 * Converts an email {@link AGStoredFile} to a PDF byte array.
	 * <p>
	 * Supports EML and MSG as input formats.
	 * <p>
	 * If no conversion strategy can be identified for the {@link AGStoredFile},
	 * {@link Optional#empty()} is returned.
	 *
	 * @return the PDF byte array
	 */
	public Optional<byte[]> fromEmailToPdfBytes() {

		Objects.requireNonNull(file);
		var converter = new EmailToPdfConverter();

		if (file.hasMimeType(MimeType.MESSAGE_RFC822) || file.hasFileNameExtension("eml")) {
			return Optional.of(converter.convertEmlToPdf(file::getFileContentInputStream));
		}

		else if (file.hasMimeType(MimeType.APPLICATION_VND_MS_OUTLOOK) || file.hasFileNameExtension("msg")) {
			return Optional.of(converter.convertMsgToPdf(file::getFileContentInputStream));
		}

		else {
			return Optional.empty();
		}
	}
}
