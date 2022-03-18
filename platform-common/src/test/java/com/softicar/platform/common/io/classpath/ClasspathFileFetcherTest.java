package com.softicar.platform.common.io.classpath;

import com.softicar.platform.common.core.java.classpath.finder.JavaClasspathFinder;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.io.classpath.file.IClasspathFile;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

public class ClasspathFileFetcherTest extends Assert {

	private final Class<?> classInMainSourceFolderFromPlainFilesystem;
	private final Class<?> classInTestSourceFolderFromPlainFilesystem;
	private final Class<?> classFromJar;

	public ClasspathFileFetcherTest() {

		// a class from "src/main/java"
		this.classInMainSourceFolderFromPlainFilesystem = ClasspathFileFetcher.class;

		// a class from "src/test/java"
		this.classInTestSourceFolderFromPlainFilesystem = ClasspathFileFetcherTest.class;

		// a class from "src/main/java" in a .jar archive
		this.classFromJar = IBasicUser.class;
	}

	@Test
	public void testWithClassFileInSameSourceFolderFromPlainFilesystem() {

		Collection<File> classpaths = new JavaClasspathFinder().findAll();

		ClasspathFileFetcher fetcher = new ClasspathFileFetcher(classpaths);

		assertTrue(
			fetcher//
				.getClasspathFiles()
				.stream()
				.map(it -> it.getName())
				.filter(it -> it.contains(classInMainSourceFolderFromPlainFilesystem.getSimpleName() + ".class"))
				.findAny()
				.isPresent());
	}

	@Test
	public void testWithClassFileInOtherSourceFolderFromPlainFilesystem() {

		Collection<File> classpaths = new JavaClasspathFinder().findAll();

		ClasspathFileFetcher fetcher = new ClasspathFileFetcher(classpaths);

		assertTrue(
			fetcher//
				.getClasspathFiles()
				.stream()
				.map(it -> it.getName())
				.filter(it -> it.contains(classInTestSourceFolderFromPlainFilesystem.getSimpleName() + ".class"))
				.findAny()
				.isPresent());
	}

	@Test
	public void testWithClassFileFromJarArchive() {

		Collection<File> classpaths = new JavaClasspathFinder().findAll();

		ClasspathFileFetcher fetcher = new ClasspathFileFetcher(classpaths);

		assertTrue(
			fetcher//
				.getClasspathFiles()
				.stream()
				.map(it -> it.getName())
				.filter(it -> it.contains(classFromJar.getSimpleName() + ".class"))
				.findAny()
				.isPresent());
	}

	@Test
	public void testPackageFiltering() {

		Class<?> classInSubPackage = IClasspathFile.class;
		Collection<File> classpaths = new JavaClasspathFinder().findAll();

		ClasspathFileFetcher fetcher = new ClasspathFileFetcher(classpaths);
		fetcher.setPackageFilter(classInSubPackage.getPackage());

		assertFalse(
			fetcher//
				.getClasspathFiles()
				.stream()
				.map(it -> it.getName())
				.filter(it -> it.contains(classInMainSourceFolderFromPlainFilesystem.getSimpleName() + ".class"))
				.findAny()
				.isPresent());

		assertTrue(
			fetcher//
				.getClasspathFiles()
				.stream()
				.map(it -> it.getName())
				.filter(it -> it.contains(classInSubPackage.getSimpleName() + ".class"))
				.findAny()
				.isPresent());
	}

	@Test
	public void testExtensionFilteringPositive() {

		Collection<File> classpaths = new JavaClasspathFinder().findAll();

		ClasspathFileFetcher fetcher = new ClasspathFileFetcher(classpaths);
		fetcher.setExtensionFilter(Collections.singleton(".class"));

		assertTrue(
			fetcher//
				.getClasspathFiles()
				.stream()
				.map(it -> it.getName())
				.filter(it -> it.contains(classInMainSourceFolderFromPlainFilesystem.getSimpleName() + ".class"))
				.findAny()
				.isPresent());
	}

	@Test
	public void testExtensionFilteringNegative() {

		Collection<File> classpaths = new JavaClasspathFinder().findAll();

		ClasspathFileFetcher fetcher = new ClasspathFileFetcher(classpaths);
		fetcher.setExtensionFilter(Collections.singleton(".qwe"));

		assertFalse(
			fetcher//
				.getClasspathFiles()
				.stream()
				.map(it -> it.getName())
				.filter(it -> it.contains(classInMainSourceFolderFromPlainFilesystem.getSimpleName() + ".class"))
				.findAny()
				.isPresent());
	}
}
