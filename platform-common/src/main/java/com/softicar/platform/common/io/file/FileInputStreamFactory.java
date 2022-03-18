package com.softicar.platform.common.io.file;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class simplifies the creation of a {@link FileInputStream}.
 *
 * @author Oliver Richers
 */
public class FileInputStreamFactory {

	public static FileInputStream create(File file) {

		try {
			return new FileInputStream(file);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
