package com.softicar.platform.common.io.zip.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Facilitates reading the content of a ZIP archive.
 *
 * @author Alexander Schmidt
 */
public class ZipFileReader {

	private final File zipFile;

	/**
	 * Constructs a new {@link ZipFileReader}.
	 *
	 * @param zipFile
	 *            the {@link File} that represents the ZIP archive to read
	 *            (never <i>null</i>)
	 */
	public ZipFileReader(File zipFile) {

		this.zipFile = Objects.requireNonNull(zipFile);
	}

	/**
	 * Reads the textual content of the file at the given {@link Path} inside
	 * the archive.
	 * <p>
	 * Returns {@link Optional#empty()} if there is no file at the given
	 * {@link Path}.
	 *
	 * @param filePath
	 *            the path of the file to read, relative to the root of the
	 *            archive (never <i>null</i>)
	 * @return the textual content
	 */
	public Optional<String> readTextContent(Path filePath) {

		try (var fileSystem = FileSystems.newFileSystem(zipFile.toPath()); var outputStream = new ByteArrayOutputStream()) {
			Path path = fileSystem.getPath(filePath.toString());
			if (Files.exists(path)) {
				Files.copy(path, outputStream);
				return Optional.of(outputStream.toString(StandardCharsets.UTF_8));
			} else {
				return Optional.empty();
			}
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Finds the {@link Path} of the first file with the given name, inside the
	 * archive.
	 * <p>
	 * The directory tree is traversed in <i>depth-first</i> order.
	 * <p>
	 * Returns {@link Optional#empty()} if there is no matching file.
	 *
	 * @param filename
	 *            the name of the file to find (never <i>null</i>)
	 * @return the {@link Path} of the first matching file, relative to the root
	 *         of the archive
	 */
	public Optional<Path> findFirstPath(String filename) {

		return findPaths(filename).stream().findFirst();
	}

	/**
	 * Finds the {@link Path} of the first file with a name that matches the
	 * given {@link Pattern}, inside the archive.
	 * <p>
	 * The directory tree is traversed in <i>depth-first</i> order.
	 * <p>
	 * Returns {@link Optional#empty()} if there is no matching file.
	 *
	 * @param filenamePattern
	 *            the {@link Pattern} against which file names shall be matched
	 *            (never <i>null</i>)
	 * @return the {@link Path} of the first matching file, relative to the root
	 *         of the archive
	 */
	public Optional<Path> findFirstPath(Pattern filenamePattern) {

		return findPaths(filenamePattern).stream().findFirst();
	}

	/**
	 * Finds the {@link Path}s of all files with the given name, inside the
	 * archive.
	 * <p>
	 * The directory tree is traversed in <i>depth-first</i> order.
	 *
	 * @param filename
	 *            the name of the file to find (never <i>null</i>)
	 * @return the {@link Path}s of all files with the given name (never
	 *         <i>null</i>)
	 */
	public List<Path> findPaths(String filename) {

		return findFilePaths(filename::equals);
	}

	/**
	 * Finds the {@link Path}s of all files with a name that matches the given
	 * {@link Pattern}, inside the archive.
	 * <p>
	 * The directory tree is traversed in <i>depth-first</i> order.
	 *
	 * @param filenamePattern
	 *            the {@link Pattern} against which file names shall be matched
	 *            (never <i>null</i>)
	 * @return the {@link Path}s of all files with a name that matches the given
	 *         {@link Pattern} (never <i>null</i>)
	 */
	public List<Path> findPaths(Pattern filenamePattern) {

		return findFilePaths(filename -> filenamePattern.matcher(filename).find());
	}

	private List<Path> findFilePaths(Predicate<String> filenamePredicate) {

		Objects.requireNonNull(filenamePredicate);
		try (var fileSystem = FileSystems.newFileSystem(zipFile.toPath())) {
			return Files//
				.walk(fileSystem.getPath("."))
				.filter(filePath -> filenamePredicate.test(filePath.getFileName().toString()))
				.collect(Collectors.toList());
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
