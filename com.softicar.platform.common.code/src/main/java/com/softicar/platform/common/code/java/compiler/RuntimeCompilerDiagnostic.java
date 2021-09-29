package com.softicar.platform.common.code.java.compiler;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class RuntimeCompilerDiagnostic {

	private final Diagnostic<? extends JavaFileObject> diagnostic;

	public RuntimeCompilerDiagnostic(Diagnostic<? extends JavaFileObject> diagnostic) {

		this.diagnostic = diagnostic;
	}

	@Override
	public String toString() {

		if (diagnostic != null) {
			JavaFileObject source = diagnostic.getSource();
			return String.format("Error in %s:%s: %s", source != null? source.getName() : "unknown", diagnostic.getLineNumber(), diagnostic.getMessage(null));
		} else {
			return String.format("Error: cannot show diagnostic message because diagnostic object is null");
		}
	}
}
