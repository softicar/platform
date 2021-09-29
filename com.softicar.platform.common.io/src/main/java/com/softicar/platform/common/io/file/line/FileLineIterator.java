package com.softicar.platform.common.io.file.line;

import com.softicar.platform.common.container.iterator.AbstractIteratorAdapter;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.file.FileInputStreamFactory;
import com.softicar.platform.common.io.reader.InputStreamReaderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.function.Supplier;

/**
 * An {@link Iterable} of {@link String} for file lines.
 * <p>
 * If you hate to write the same {@link BufferedReader} code over and over
 * again, with its annoying checked exceptions: <code>
 * try
 * {
 *     String line;
 *     BufferedReader reader(new File("example.txt"));
 *     while((line = reader.readLine()) != 0)
 *          System.out.println(line);
 * }
 * catch(IOException exception)
 * {
 *     throw new SofticarIOException(exception);
 * }
 * </code>
 * <p>
 * This is the solution: <code>
 * for(String line: FileLineIterator.create("example.txt"))
 *     System.out.println(line);
 * </code>
 * <p>
 * Note that closing of the iterator is only necessary if you do not iterate to
 * the end of the file. All allocated resources are automatically freed after
 * reading the last line. If you cancel the iteration earlier, you should call
 * the {@link #close()} method to release the resources properly.
 *
 * @author Oliver Richers
 */
public class FileLineIterator extends AbstractIteratorAdapter<String> implements AutoCloseable {

	private final BufferedReader reader;

	private FileLineIterator(Reader reader) {

		this.reader = new BufferedReader(reader);
	}

	private FileLineIterator(Supplier<Reader> reader) {

		this.reader = new BufferedReader(reader.get());
	}

	@Override
	public String fetchNext() {

		String line = readLine();

		if (line == null) {
			close();
			setFinished();
		}

		return line;
	}

	private String readLine() {

		try {
			return reader.readLine();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	/**
	 * Closes the internal reader object, free all allocated resources.
	 */
	@Override
	public void close() {

		StreamUtils.close(reader);
	}

	// -------------------- FACTORY METHODS -------------------- //

	public static FileLineIterator readUtf8(Supplier<InputStream> streamSupplier) {

		return new FileLineIterator(() -> InputStreamReaderFactory.readUtf8(streamSupplier));
	}

	public static FileLineIterator readUtf8(InputStream inputStream) {

		return new FileLineIterator(() -> InputStreamReaderFactory.readUtf8(inputStream));
	}

	public static FileLineIterator readUtf8(File file) {

		return readUtf8(() -> FileInputStreamFactory.create(file));
	}
}
