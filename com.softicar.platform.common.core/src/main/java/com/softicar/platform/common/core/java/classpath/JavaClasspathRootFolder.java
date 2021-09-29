package com.softicar.platform.common.core.java.classpath;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.analyzer.JavaClassAnalyzer;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Represents a root folder on the class path.
 *
 * @author Oliver Richers
 */
public class JavaClasspathRootFolder extends AbstractJavaClasspathRoot {

	private final Collection<File> files;

	public JavaClasspathRootFolder(File file) {

		super(file);

		this.files = new ArrayList<>();
		addFilesDeep(file);
	}

	public Collection<File> getFiles() {

		return files;
	}

	@Override
	protected Collection<AnalyzedJavaClass> analyzeClasses() {

		return getFiles()//
			.stream()
			.filter(this::isClassFile)
			.map(this::analyzeClass)
			.collect(Collectors.toList());
	}

	private boolean isClassFile(File file) {

		return file.getName().endsWith(".class");
	}

	private AnalyzedJavaClass analyzeClass(File file) {

		try {
			return new JavaClassAnalyzer(file).analyze();
		} catch (Exception exception) {
			System.out.printf("failed: %s\n", file.getName());
			throw exception;
		}
	}

	private void addFilesDeep(File currentFolder) {

		for (File file: currentFolder.listFiles()) {
			if (file.isDirectory()) {
				addFilesDeep(file);
			} else {
				files.add(file);
			}
		}
	}
}
