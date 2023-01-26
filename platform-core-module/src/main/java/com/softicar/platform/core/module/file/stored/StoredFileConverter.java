package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.core.module.email.converter.EmailToPdfConverter;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Facilitates conversion of {@link AGStoredFile} records.
 *
 * @author Alexander Schmidt
 */
public class StoredFileConverter {

	private static final Map<MimeType, Function<Supplier<InputStream>, byte[]>> CONVERTERS = Map
		.ofEntries(//
			Map.entry(MimeType.MESSAGE_RFC822, new EmailToPdfConverter()::convertEmlToPdf),
			Map.entry(MimeType.APPLICATION_VND_MS_OUTLOOK, new EmailToPdfConverter()::convertMsgToPdf)
		//
		);
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
		return getConverterFunction().map(it -> it.apply(file::getFileContentInputStream));
	}

	/**
	 * Determines whether this {@link StoredFileConverter} can convert the
	 * {@link AGStoredFile} to PDF.
	 *
	 * @return <i>true</i> if the {@link AGStoredFile} is convertible;
	 *         <i>false</i> otherwise
	 */
	public boolean isConvertibleToPdf() {

		return getConverterFunction().isPresent();
	}

	private Optional<Function<Supplier<InputStream>, byte[]>> getConverterFunction() {

		return CONVERTERS//
			.entrySet()
			.stream()
			.filter(entry -> file.hasMimeTypeOrExtension(entry.getKey()))
			.findFirst()
			.map(entry -> entry.getValue());
	}
}
