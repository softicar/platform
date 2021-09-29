package com.softicar.platform.common.io.file;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;

/**
 * A static factory for {@link FileOutputStream}.
 *
 * @author Oliver Richers
 */
public class FileOutputStreamFactory {

	public static FileOutputStream create(File file) {

		try {
			return new FileOutputStream(file);
		} catch (FileNotFoundException exception) {
			throw new SofticarIOException(exception);
		}
	}

	public static FileOutputStream create(Path filePath) {

		return create(filePath.toFile());
	}
}
