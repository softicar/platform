package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.core.module.email.converter.EmailToPdfConverter;
import java.util.Objects;
import java.util.Optional;

/**
 * Facilitates conversion of {@link AGStoredFile} instances.
 *
 * @author Alexander Schmidt
 */
public class StoredFileConverter {

	/**
	 * Converts the given email {@link AGStoredFile} to a PDF byte array.
	 * <p>
	 * Supports EML and MSG as input formats.
	 * <p>
	 * If no conversion strategy can be identified for the given
	 * {@link AGStoredFile}, {@link Optional#empty()} is returned.
	 *
	 * @param emailFile
	 *            the email {@link AGStoredFile} (never <i>null</i>)
	 * @return the PDF byte array
	 */
	public static Optional<byte[]> convertEmailToPdfBytes(AGStoredFile emailFile) {

		Objects.requireNonNull(emailFile);
		var converter = new EmailToPdfConverter();

		if (emailFile.hasMimeType(MimeType.MESSAGE_RFC822) || emailFile.hasFileNameExtension("eml")) {
			return Optional.of(converter.convertEmlToPdf(emailFile::getFileContentInputStream));
		}

		else if (emailFile.hasMimeType(MimeType.APPLICATION_VND_MS_OUTLOOK) || emailFile.hasFileNameExtension("msg")) {
			return Optional.of(converter.convertMsgToPdf(emailFile::getFileContentInputStream));
		}

		else {
			return Optional.empty();
		}
	}
}
