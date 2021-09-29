package com.softicar.platform.common.code.java.compiler;

import com.softicar.platform.common.core.utils.DevNull;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

class RuntimeCompilerClassLoader extends ClassLoader {

	private final Map<String, InMemoryJavaClassFile> compiledClasses = new TreeMap<>();

	public RuntimeCompilerClassLoader(ClassLoader parentClassLoader) {

		super(parentClassLoader);
	}

	@Override
	protected Class<?> findClass(String qualifiedClassName) throws ClassNotFoundException {

		InMemoryJavaClassFile file = compiledClasses.get(qualifiedClassName);
		if (file != null) {
			byte[] bytes = file.getByteCode();
			return defineClass(qualifiedClassName, bytes, 0, bytes.length);
		}
		// Workaround for "feature" in Java 6
		// see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6434149
		try {
			Class<?> c = Class.forName(qualifiedClassName);
			return c;
		} catch (ClassNotFoundException nf) {
			// Ignore and fall through
			DevNull.swallow(nf);
		}
		return super.findClass(qualifiedClassName);
	}

	@Override
	public InputStream getResourceAsStream(String resourceName) {

		if (resourceName.endsWith(".class")) {
			String qualifiedClassName = resourceName.substring(0, resourceName.length() - ".class".length()).replace('/', '.');
			InMemoryJavaClassFile file = compiledClasses.get(qualifiedClassName);
			if (file != null) {
				return new ByteArrayInputStream(file.getByteCode());
			}
		}
		return super.getResourceAsStream(resourceName);
	}

	public void clear() {

		compiledClasses.clear();
	}

	public void addCompiledClass(String qualifiedClassName, InMemoryJavaClassFile javaClassFile) {

		compiledClasses.put(qualifiedClassName, javaClassFile);
	}

	public Collection<InMemoryJavaClassFile> getCompiledClasses() {

		return Collections.unmodifiableCollection(compiledClasses.values());
	}
}
