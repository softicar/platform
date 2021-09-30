package com.softicar.platform.common.ocr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Common interface of classes which use OCR techniques to extract text from a
 * document (e.g. a PDF or an image file).
 *
 * @author Alexander Schmidt
 */
public interface IOcrTextExtractor {

	/**
	 * Extracts text from a given {@link InputStream}.
	 * <p>
	 * The given {@link InputStream} will <b>not</b> be closed by this method.
	 *
	 * @param inputStream
	 *            the {@link InputStream} from which text shall be extracted;
	 *            needs to be closed by the caller (never <i>null</i>)
	 * @return the extracted text (never <i>null</i>)
	 */
	String extractText(InputStream inputStream);

	/**
	 * Extracts text from a given {@link File}.
	 * <p>
	 * Implemented in terms of {@link #extractText(InputStream)}, by default.
	 *
	 * @param file
	 *            the {@link File} from which the text shall be extracted (never
	 *            <i>null</i>)
	 * @return the extracted text (never <i>null</i>)
	 */
	default String extractText(File file) {

		try (FileInputStream inputStream = new FileInputStream(file)) {
			return extractText(inputStream);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
