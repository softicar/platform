package com.softicar.platform.common.code.java.compiler;

import com.softicar.platform.common.string.Imploder;
import java.util.ArrayList;
import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class RuntimeCompilerDiagnostics {

	private final List<RuntimeCompilerDiagnostic> diagnostics;

	public RuntimeCompilerDiagnostics(List<Diagnostic<? extends JavaFileObject>> diagnostics) {

		this.diagnostics = new ArrayList<>();

		for (Diagnostic<? extends JavaFileObject> diagnostic: diagnostics) {
			this.diagnostics.add(new RuntimeCompilerDiagnostic(diagnostic));
		}
	}

	@Override
	public String toString() {

		return Imploder.implode(diagnostics, "\n");
	}

	public List<RuntimeCompilerDiagnostic> getDiagnostics() {

		return diagnostics;
	}
}
