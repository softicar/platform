package com.softicar.platform.common.code.java.compiler;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.net.URI;
import java.net.URISyntaxException;
import javax.tools.SimpleJavaFileObject;

/**
 * Abstract base class of {@link InMemoryJavaSourceFile} and
 * {@link InMemoryJavaClassFile}.
 *
 * @author Oliver Richers
 */
abstract class AbstractInMemoryJavaFile extends SimpleJavaFileObject {

	private final JavaClassName className;

	public AbstractInMemoryJavaFile(JavaClassName className, Kind kind) {

		super(createUri(className, kind), kind);

		this.className = className;
	}

	private static URI createUri(JavaClassName className, Kind kind) {

		return createUri(className.getCanonicalName("/") + kind.extension);
	}

	private static URI createUri(String uriString) {

		try {
			return new URI(uriString);
		} catch (URISyntaxException exception) {
			throw new RuntimeException(exception);
		}
	}

	public JavaClassName getClassName() {

		return className;
	}
}
