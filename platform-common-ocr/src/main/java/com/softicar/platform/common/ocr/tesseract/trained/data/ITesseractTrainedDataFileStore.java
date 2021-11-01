package com.softicar.platform.common.ocr.tesseract.trained.data;

import java.io.File;

/**
 * Common interface of file stores which contain Tesseract4 trained data files.
 *
 * @author Alexander Schmidt
 */
public interface ITesseractTrainedDataFileStore {

	/**
	 * Adds a file to this file store.
	 * <p>
	 * If a file with the given name already exists in this file store, no
	 * action is performed and <i>false</i> is returned. Otherwise, a file gets
	 * added and <i>true</i> is returned.
	 *
	 * @param fileName
	 *            the name of the file to put (never <i>null</i>)
	 * @param content
	 *            the content of the file (never <i>null</i>)
	 * @return <i>true</i> if a file was actually added; <i>false</i> otherwise
	 */
	boolean put(String fileName, byte[] content);

	/**
	 * Retrieves a file from this file store.
	 * <p>
	 * Returns <i>null</i> if this file stores does not contain a file with the
	 * given name.
	 *
	 * @param fileName
	 *            the name of the file to retrieve (n1ever <i>null</i>)
	 * @return the retrieved file (may be <i>null</i>)
	 */
	File get(String fileName);

	/**
	 * Returns the directory which contains the files of this file store.
	 *
	 * @return the directory of this file store (never <i>null</i>)
	 */
	File getDirectory();
}
