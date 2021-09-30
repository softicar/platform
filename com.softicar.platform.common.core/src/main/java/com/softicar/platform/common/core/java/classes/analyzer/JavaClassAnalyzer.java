package com.softicar.platform.common.core.java.classes.analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.RetentionPolicy;
import java.util.function.Supplier;
import org.objectweb.asm.ClassReader;

/**
 * Analyzes a given Java class regarding its dependencies and other properties.
 * <p>
 * Known limitation: References to annotations with
 * {@link RetentionPolicy#SOURCE} cannot be and will not be considered.
 *
 * @author Oliver Richers
 */
public class JavaClassAnalyzer {

	private final Supplier<InputStream> inputStreamSupplier;

	/**
	 * Constructs this analyzer with the given {@link InputStream} supplier.
	 *
	 * @param inputStreamSupplier
	 *            the {@link InputStream} supplier, used to read the class
	 *            content (never null)
	 */
	public JavaClassAnalyzer(Supplier<InputStream> inputStreamSupplier) {

		this.inputStreamSupplier = inputStreamSupplier;
	}

	/**
	 * Constructs this analyzer to analyze the given {@link File}.
	 *
	 * @param classFile
	 *            the class {@link File} to analyze (never null)
	 */
	public JavaClassAnalyzer(File classFile) {

		this(() -> getFileInputStream(classFile));
	}

	/**
	 * Constructs this analyzer to analyze the given {@link Class}.
	 *
	 * @param classToAnalyze
	 *            the {@link Class} to analyze (never null)
	 */
	public JavaClassAnalyzer(Class<?> classToAnalyze) {

		this(() -> getClassInputStream(classToAnalyze));
	}

	/**
	 * Analyzes the supplied class and returns a new {@link AnalyzedJavaClass}
	 * object.
	 *
	 * @return the analysis result in form of an {@link AnalyzedJavaClass}
	 *         (never null)
	 */
	public AnalyzedJavaClass analyze() {

		try (InputStream inputStream = inputStreamSupplier.get()) {
			return analyze(inputStream);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private AnalyzedJavaClass analyze(InputStream inputStream) {

		AnalyzedJavaClass javaClass = new AnalyzedJavaClass();
		try {
			new ClassReader(inputStream).accept(new InternalClassAnalyzer(javaClass), 0);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		return javaClass;
	}

	private static InputStream getClassInputStream(Class<?> classToRead) {

		return JavaClassAnalyzer.class//
			.getClassLoader()
			.getResourceAsStream(classToRead.getName().replace('.', '/') + ".class");
	}

	private static InputStream getFileInputStream(File file) {

		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
}
