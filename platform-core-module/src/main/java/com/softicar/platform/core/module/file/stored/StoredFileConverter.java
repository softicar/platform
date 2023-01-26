package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.core.module.email.converter.EmailToPdfConverter;
import java.util.Objects;
import java.util.Optional;

/**
 * Facilitates conversion of {@link AGStoredFile} records.
 *
 * @author Alexander Schmidt
 */
public class StoredFileConverter {

	private final AGStoredFile file;

	/**
	 * Constructs a new {@link StoredFileConverter}.
	 *
	 * @param file
	 *            the {@link AGStoredFile} to convert (never <i>null</i>)
	 */
	public StoredFileConverter(AGStoredFile file) {

		this.file = Objects.requireNonNull(file);
	}

	/**
	 * Converts an {@link AGStoredFile} to a PDF byte array.
	 * <p>
	 * Supports the following {@link AGStoredFile} formats:
	 * <ul>
	 * <li>EML</li>
	 * <li>MSG</li>
	 * </ul>
	 *
	 * @return the PDF byte array
	 */
	public Optional<byte[]> toPdfBytes() {

		Objects.requireNonNull(file);

		if (isEmlEmail()) {
			return Optional.of(new EmailToPdfConverter().convertEmlToPdf(file::getFileContentInputStream));
		}

		else if (isMsgEmail()) {
			return Optional.of(new EmailToPdfConverter().convertMsgToPdf(file::getFileContentInputStream));
		}

		else {
			return Optional.empty();
		}
	}

	/**
	 * Determines whether this {@link StoredFileConverter} can convert the
	 * {@link AGStoredFile} to PDF.
	 *
	 * @return <i>true</i> if the {@link AGStoredFile} is convertible;
	 *         <i>false</i> otherwise
	 */
	public boolean isConvertibleToPdf() {

		return isEmlEmail() || isMsgEmail();
	}

	private boolean isEmlEmail() {

		return file.hasMimeType(MimeType.MESSAGE_RFC822) || file.hasFileNameExtension("eml");
	}

	private boolean isMsgEmail() {

		return file.hasMimeType(MimeType.APPLICATION_VND_MS_OUTLOOK) || file.hasFileNameExtension("msg");
	}
}
