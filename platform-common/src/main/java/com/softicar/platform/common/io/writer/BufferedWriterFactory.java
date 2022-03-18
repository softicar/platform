package com.softicar.platform.common.io.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.Writer;

/**
 * A static factory for {@link BufferedWriter}.
 *
 * @author Oliver Richers
 */
public class BufferedWriterFactory {

	public static BufferedWriter write(Writer writer) {

		return new BufferedWriter(writer);
	}

	public static BufferedWriter writeUtf8(File file) {

		return new BufferedWriter(OutputStreamWriterFactory.writeUtf8(file));
	}
}
