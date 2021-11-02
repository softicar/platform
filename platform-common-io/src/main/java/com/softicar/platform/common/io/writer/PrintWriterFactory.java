package com.softicar.platform.common.io.writer;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * This class simplifies the creation of a {@link PrintWriter}.
 *
 * @author Oliver Richers
 */
public class PrintWriterFactory {

	public static PrintWriter print(Writer writer) {

		return new PrintWriter(writer);
	}

	public static PrintWriter printUtf8(File file) {

		return new PrintWriter(BufferedWriterFactory.writeUtf8(file));
	}
}
