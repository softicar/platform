package com.softicar.platform.common.code.java.compiler;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.tools.JavaFileObject;

/**
 * This is an in-memory implementation of {@link JavaFileObject} for compiled
 * Java classes.
 *
 * @author Oliver Richers
 */
class InMemoryJavaClassFile extends AbstractInMemoryJavaFile {

	private final ByteArrayOutputStream byteCode = new ByteArrayOutputStream();

	public InMemoryJavaClassFile(JavaClassName className) {

		super(className, Kind.CLASS);
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {

		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream openInputStream() {

		return new ByteArrayInputStream(byteCode.toByteArray());
	}

	@Override
	public OutputStream openOutputStream() {

		return byteCode;
	}

	public byte[] getByteCode() {

		return byteCode.toByteArray();
	}
}
