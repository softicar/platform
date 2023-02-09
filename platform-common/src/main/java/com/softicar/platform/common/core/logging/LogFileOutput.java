package com.softicar.platform.common.core.logging;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/**
 * An {@link ILogOutput} that writes log lines to a file.
 *
 * @author Alexander Schmidt
 */
public class LogFileOutput implements ILogOutput, AutoCloseable {

	private final File file;
	private FileWriter fileWriter;

	/**
	 * Constructs a new {@link LogFileOutput} for the given {@link File}.
	 * <p>
	 * The parent directories of the file will be created when the first log
	 * line is written.
	 *
	 * @param file
	 *            the log {@link File} (never <i>null</i>)
	 * @throws IllegalArgumentException
	 *             if an existing directory is given
	 */
	public LogFileOutput(File file) {

		this.file = assertValidFile(file);
		this.fileWriter = null;
	}

	@SuppressWarnings("resource")
	@Override
	public void logLine(String line) {

		try {
			getOrCreateFileWriter(file).append(line).append("\n");
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public void close() {

		if (fileWriter != null) {
			try {
				fileWriter.close();
			} catch (IOException exception) {
				throw new SofticarIOException(exception);
			}
		}
	}

	private FileWriter getOrCreateFileWriter(File file) throws IOException {

		if (fileWriter == null) {
			Files.createDirectories(file.getParentFile().toPath());
			fileWriter = new FileWriter(file);
		}
		return fileWriter;
	}

	private File assertValidFile(File file) {

		Objects.requireNonNull(file);
		if (file.isDirectory()) {
			throw new IllegalArgumentException("The given file handle referred to a directory: %s".formatted(file));
		}
		return file;
	}
}
