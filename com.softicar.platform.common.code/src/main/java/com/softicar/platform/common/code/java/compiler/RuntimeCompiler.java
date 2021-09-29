package com.softicar.platform.common.code.java.compiler;

import com.softicar.platform.common.code.java.IJavaClassSourceCode;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.string.charset.Charsets;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

/**
 * A Java compiler that compiles source code at runtime.
 *
 * @author Oliver Richers
 */
public class RuntimeCompiler implements AutoCloseable {

	private final JavaCompiler compiler;
	private final RuntimeCompilerClassLoader classLoader;
	private final InMemoryJavaFileManager fileManager;
	private final Map<JavaClassName, InMemoryJavaSourceFile> sourceFiles;

	@SuppressWarnings("resource")
	public RuntimeCompiler() {

		this.compiler = ToolProvider.getSystemJavaCompiler();
		this.classLoader = new RuntimeCompilerClassLoader(getClass().getClassLoader());
		this.fileManager = new InMemoryJavaFileManager(createStandardFileManager(), classLoader);
		this.sourceFiles = new TreeMap<>();
	}

	private JavaFileManager createStandardFileManager() {

		return compiler.getStandardFileManager(null, null, Charsets.UTF8);
	}

	@Override
	public void close() {

		try {
			fileManager.close();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	public void addSourceCode(IJavaClassSourceCode sourceCode) {

		addSourceCode(sourceCode.getClassName(), sourceCode.getSourceCode());
	}

	public void addSourceCode(JavaClassName className, CharSequence sourceCode) {

		InMemoryJavaSourceFile sourceFile = new InMemoryJavaSourceFile(className, sourceCode);
		sourceFiles.put(className, sourceFile);
		fileManager.addSourceFile(sourceFile);
	}

	public void clear() {

		classLoader.clear();
		fileManager.clear();
		sourceFiles.clear();
	}

	public Map<JavaClassName, Class<?>> compile() {

		// start compilation
		DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
		CompilationTask task = compiler.getTask(null, fileManager, diagnosticCollector, null, null, sourceFiles.values());
		Boolean success = task.call();
		if (!success) {
			throw new RuntimeCompilerException("Compilation failed.", sourceFiles.keySet(), diagnosticCollector.getDiagnostics());
		}

		try {
			Map<JavaClassName, Class<?>> compiledClasses = new TreeMap<>();
			for (JavaClassName className: sourceFiles.keySet()) {
				Class<?> compiledClass = classLoader.loadClass(className.getCanonicalName());
				compiledClasses.put(className, compiledClass);
			}
			return compiledClasses;
		} catch (ClassNotFoundException | IllegalArgumentException | SecurityException exception) {
			throw new RuntimeCompilerException(sourceFiles.keySet(), exception, diagnosticCollector.getDiagnostics());
		}
	}

	public static <T> Class<T> compile(IJavaClassSourceCode sourceCode) {

		return compile(sourceCode.getClassName(), sourceCode.getSourceCode());
	}

	public static <T> Class<T> compile(JavaClassName className, CharSequence sourceCode) {

		try (RuntimeCompiler compiler = new RuntimeCompiler()) {
			compiler.addSourceCode(className, sourceCode);
			Map<JavaClassName, Class<?>> compiledClasses = compiler.compile();
			return CastUtils.cast(compiledClasses.get(className));
		}
	}
}
