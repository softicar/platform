package com.softicar.platform.common.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Common interface of classes to extract text from a PDF document.
 *
 * @author Alexander Schmidt
 */
public interface IPdfTextExtractor {

	/**
	 * Extracts text from a given PDF {@link InputStream}.
	 * <p>
	 * The given {@link InputStream} will <b>not</b> be closed by this method.
	 *
	 * @param inputStream
	 *            the PDF {@link InputStream} from which text shall be
	 *            extracted; needs to be closed by the caller (never
	 *            <i>null</i>)
	 * @return the extracted text (never <i>null</i>)
	 */
	String extractText(InputStream inputStream);

	/**
	 * Extracts text from a given PDF {@link File}.
	 *
	 * @param file
	 *            the PDF {@link File} from which the text shall be extracted
	 *            (never <i>null</i>)
	 * @return the extracted text (never <i>null</i>)
	 */
	default String extractText(File file) {

		try (var inputStream = new FileInputStream(file)) {
			return extractText(inputStream);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
