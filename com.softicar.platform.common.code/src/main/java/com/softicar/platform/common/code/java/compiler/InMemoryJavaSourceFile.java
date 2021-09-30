package com.softicar.platform.common.code.java.compiler;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.io.InputStream;
import java.io.OutputStream;
import javax.tools.JavaFileObject;

/**
 * This is an in-memory implementation of {@link JavaFileObject} for Java source
 * code.
 *
 * @author Oliver Richers
 */
class InMemoryJavaSourceFile extends AbstractInMemoryJavaFile {

	private final CharSequence sourceCode;

	public InMemoryJavaSourceFile(JavaClassName className, CharSequence sourceCode) {

		super(className, Kind.SOURCE);

		this.sourceCode = sourceCode;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {

		return sourceCode;
	}

	@Override
	public InputStream openInputStream() {

		throw new UnsupportedOperationException();
	}

	@Override
	public OutputStream openOutputStream() {

		throw new UnsupportedOperationException();
	}
}
