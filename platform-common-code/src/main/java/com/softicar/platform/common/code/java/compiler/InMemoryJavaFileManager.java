package com.softicar.platform.common.code.java.compiler;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;

/**
 * This is a {@link JavaFileManager} holding source code and class files in
 * memory.
 *
 * @author Oliver Richers
 */
class InMemoryJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

	private final RuntimeCompilerClassLoader classLoader;
	private final Map<JavaClassName, InMemoryJavaSourceFile> sourceFiles;
	private final Map<JavaClassName, InMemoryJavaClassFile> classFiles;

	public InMemoryJavaFileManager(JavaFileManager fileManager, RuntimeCompilerClassLoader classLoader) {

		super(fileManager);

		this.classLoader = classLoader;
		this.sourceFiles = new TreeMap<>();
		this.classFiles = new TreeMap<>();
	}

	public void clear() {

		sourceFiles.clear();
		classFiles.clear();
	}

	public void addSourceFile(InMemoryJavaSourceFile sourceFile) {

		sourceFiles.put(sourceFile.getClassName(), sourceFile);
	}

	@Override
	public JavaFileObject getJavaFileForInput(JavaFileManager.Location location, String qualifiedName, JavaFileObject.Kind kind) throws IOException {

		if (location == StandardLocation.SOURCE_PATH && kind == Kind.SOURCE) {
			JavaClassName className = new JavaClassName(qualifiedName);
			InMemoryJavaSourceFile sourceFile = sourceFiles.get(className);
			if (sourceFile != null) {
				return sourceFile;
			}
		}

		return super.getJavaFileForInput(location, qualifiedName, kind);
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String qualifiedName, Kind kind, FileObject sibling) throws IOException {

		if (location == StandardLocation.CLASS_OUTPUT && kind == Kind.CLASS) {
			JavaClassName className = new JavaClassName(qualifiedName);
			InMemoryJavaClassFile classFile = new InMemoryJavaClassFile(className);
			classFiles.put(className, classFile);
			classLoader.addCompiledClass(qualifiedName, classFile);
			return classFile;
		}

		return super.getJavaFileForOutput(location, qualifiedName, kind, sibling);
	}

	@Override
	public ClassLoader getClassLoader(JavaFileManager.Location location) {

		return classLoader;
	}

	@Override
	public String inferBinaryName(Location location, JavaFileObject file) {

		if (file instanceof AbstractInMemoryJavaFile) {
			return ((AbstractInMemoryJavaFile) file).getClassName().getCanonicalName();
		} else {
			return super.inferBinaryName(location, file);
		}
	}

	@Override
	public Iterable<JavaFileObject> list(Location location, String packageName, Set<Kind> kinds, boolean recurse) throws IOException {

		// add in-memory files
		ArrayList<JavaFileObject> files = new ArrayList<>();
		if (location == StandardLocation.CLASS_PATH && kinds.contains(JavaFileObject.Kind.CLASS)) {
			for (InMemoryJavaClassFile file: classFiles.values()) {
				if (file.getClassName().getPackageName().getName().startsWith(packageName)) {
					files.add(file);
				}
			}
		} else if (location == StandardLocation.SOURCE_PATH && kinds.contains(JavaFileObject.Kind.SOURCE)) {
			for (InMemoryJavaSourceFile file: sourceFiles.values()) {
				if (file.getClassName().getPackageName().getName().startsWith(packageName)) {
					files.add(file);
				}
			}
		}

		// add files from super class
		Iterable<JavaFileObject> result = super.list(location, packageName, kinds, recurse);
		for (JavaFileObject file: result) {
			files.add(file);
		}

		return files;
	}
}
