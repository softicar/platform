package com.softicar.platform.common.core.logging;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Objects;

/**
 * An {@link ILogOutput} that writes log lines to a file.
 *
 * @author Alexander Schmidt
 */
public class LogFileOutput implements ILogOutput, AutoCloseable {

	private final File file;
	private PrintWriter writer;

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
		this.writer = null;
	}

	@SuppressWarnings("resource")
	@Override
	public void logLine(String line) {

		try {
			var writer = getOrCreateWriter(file);
			writer.println(line);
			writer.flush();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public void close() {

		if (writer != null) {
			writer.close();
		}
	}

	private PrintWriter getOrCreateWriter(File file) throws IOException {

		if (writer == null) {
			Files.createDirectories(file.getParentFile().toPath());
			writer = new PrintWriter(file);
		}
		return writer;
	}

	private File assertValidFile(File file) {

		Objects.requireNonNull(file);
		if (file.isDirectory()) {
			throw new IllegalArgumentException("The given file handle referred to a directory: %s".formatted(file));
		}
		return file;
	}
}
