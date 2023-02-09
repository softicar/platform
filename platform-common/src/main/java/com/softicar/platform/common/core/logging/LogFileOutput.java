package com.softicar.platform.common.core.logging;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * An {@link ILogOutput} that writes log lines to a file.
 *
 * @author Alexander Schmidt
 */
public class LogFileOutput implements ILogOutput, AutoCloseable {

	private final Path filePath;
	private FileWriter fileWriter;

	/**
	 * Constructs a new {@link LogFileOutput} for the file referenced by the
	 * given {@link Path}.
	 * <p>
	 * The parent directories of the file will be created when the first log
	 * line is written.
	 *
	 * @param filePath
	 *            the {@link Path} to the log file (never <i>null</i>)
	 * @throws IllegalArgumentException
	 *             if the given path refers to a directory
	 */
	public LogFileOutput(Path filePath) {

		this.filePath = assertValidFilePath(filePath);
		this.fileWriter = null;
	}

	@SuppressWarnings("resource")
	@Override
	public void logLine(String line) {

		try {
			getOrCreateFileWriter(filePath).append(line).append("\n");
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

	private FileWriter getOrCreateFileWriter(Path filePath) throws IOException {

		if (fileWriter == null) {
			var file = filePath.toFile();
			Files.createDirectories(file.getParentFile().toPath());
			fileWriter = new FileWriter(file);
		}
		return fileWriter;
	}

	private Path assertValidFilePath(Path filePath) {

		Objects.requireNonNull(filePath);

		if (filePath.toFile().isDirectory()) {
			throw new IllegalArgumentException("The given path must not refer to a directory: %s".formatted(filePath));
		}

		return filePath;
	}
}
