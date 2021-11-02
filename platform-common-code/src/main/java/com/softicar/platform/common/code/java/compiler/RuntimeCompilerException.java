package com.softicar.platform.common.code.java.compiler;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class RuntimeCompilerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Set<JavaClassName> classNames;
	private final RuntimeCompilerDiagnostics diagnostics;

	public RuntimeCompilerException(String message, Set<JavaClassName> classNames, List<Diagnostic<? extends JavaFileObject>> diagnostics) {

		super(message + "\n" + new RuntimeCompilerDiagnostics(diagnostics).toString());
		this.classNames = classNames;
		this.diagnostics = new RuntimeCompilerDiagnostics(diagnostics);
	}

	public RuntimeCompilerException(Set<JavaClassName> classNames, Throwable cause, List<Diagnostic<? extends JavaFileObject>> diagnostics) {

		super(new RuntimeCompilerDiagnostics(diagnostics).toString(), cause);
		this.classNames = classNames;
		this.diagnostics = new RuntimeCompilerDiagnostics(diagnostics);
	}

	public Collection<JavaClassName> getClassNames() {

		return Collections.unmodifiableSet(classNames);
	}

	public List<RuntimeCompilerDiagnostic> getDiagnostics() {

		return diagnostics.getDiagnostics();
	}
}
