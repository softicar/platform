package com.softicar.platform.common.io.resource;

import com.softicar.platform.common.io.writer.ManagedWriter;
import com.softicar.platform.common.string.charset.Charsets;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * A {@link Reader} for the content of a textual {@link IResource}.
 *
 * @author Oliver Richers
 */
public class ResourceReader extends InputStreamReader {

	/**
	 * Constructs this {@link Reader} for the given {@link IResource}.
	 * <p>
	 * If the given {@link IResource} does not specify a {@link Charset},
	 * {@link Charsets#UTF8} is assumed.
	 *
	 * @param resource
	 *            the {@link IResource} to read (never <i>null</i>)
	 */
	@SuppressWarnings("resource")
	public ResourceReader(IResource resource) {

		super(resource.getResourceAsStream(), resource.getCharset().orElse(Charsets.UTF8));
	}

	/**
	 * Reads all characters from the given {@link IResource}, and returns them
	 * as a {@link String}.
	 *
	 * @param resource
	 *            the {@link IResource} to read from (never <i>null</i> )
	 * @return the textual content of the resource (never <i>null</i>)
	 */
	public static String readAllText(IResource resource) {

		StringBuilder builder = new StringBuilder();
		new ManagedWriter(builder).write(resource);
		return builder.toString();
	}
}
