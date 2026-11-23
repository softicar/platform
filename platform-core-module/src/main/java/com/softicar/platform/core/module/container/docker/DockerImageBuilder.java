package com.softicar.platform.core.module.container.docker;

import com.softicar.platform.common.io.command.ShellCommandExecutor;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * Builds a local Docker image from a {@code Dockerfile} and the files it
 * copies into the image.
 * <p>
 * The image is tagged with a hash of the names and contents of all files. It
 * is only built if no image with that tag exists yet, so repeated builds are
 * cheap, and a changed file always leads to a new image.
 *
 * @author Semsudin Mekanic
 */
public class DockerImageBuilder {

	private static final String DOCKERFILE = "Dockerfile";
	private static final int TAG_LENGTH = 16;

	private final String repository;
	private final Map<String, byte[]> files;

	/**
	 * Constructs a new {@link DockerImageBuilder}.
	 *
	 * @param repository
	 *            the repository part of the image name, in lower case; e.g.
	 *            {@code "myvendor/myservice-test"} (never <i>null</i>)
	 */
	public DockerImageBuilder(String repository) {

		this.repository = Objects.requireNonNull(repository);
		this.files = new TreeMap<>();
	}

	/**
	 * Adds a file to the build context of the image.
	 *
	 * @param filename
	 *            the name of the file, without any directory; e.g.
	 *            {@code "Dockerfile"} (never <i>null</i>)
	 * @param content
	 *            the content of the file (never <i>null</i>)
	 * @return this
	 */
	public DockerImageBuilder addFile(String filename, byte[] content) {

		Objects.requireNonNull(filename);
		Objects.requireNonNull(content);
		if (filename.isBlank() || filename.contains("/") || filename.contains("\\") || filename.startsWith(".")) {
			throw new IllegalArgumentException("Illegal file name: '%s'".formatted(filename));
		}
		files.put(filename, content.clone());
		return this;
	}

	/**
	 * Adds a text file to the build context of the image, encoded in UTF-8.
	 *
	 * @param filename
	 *            the name of the file, without any directory; e.g.
	 *            {@code "Dockerfile"} (never <i>null</i>)
	 * @param content
	 *            the content of the file (never <i>null</i>)
	 * @return this
	 */
	public DockerImageBuilder addFile(String filename, String content) {

		return addFile(filename, content.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Builds the image, unless an image with the same files was built before.
	 *
	 * @return the name of the image, including its tag; e.g.
	 *         {@code "myvendor/myservice-test:0123456789abcdef"} (never
	 *         <i>null</i>)
	 * @throws IllegalStateException
	 *             if no {@code Dockerfile} was added
	 * @throws RuntimeException
	 *             if the image could not be built
	 */
	public String build() {

		if (!files.containsKey(DOCKERFILE)) {
			throw new IllegalStateException("Missing file: %s".formatted(DOCKERFILE));
		}
		String imageName = repository + ":" + computeTag();
		if (!isImagePresent(imageName)) {
			buildImage(imageName);
		}
		return imageName;
	}

	private String computeTag() {

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			for (Entry<String, byte[]> file: files.entrySet()) {
				digest.update(file.getKey().getBytes(StandardCharsets.UTF_8));
				digest.update((byte) 0);
				digest.update(file.getValue());
				digest.update((byte) 0);
			}
			return HexFormat.of().formatHex(digest.digest()).substring(0, TAG_LENGTH);
		} catch (NoSuchAlgorithmException exception) {
			throw new RuntimeException(exception);
		}
	}

	private boolean isImagePresent(String imageName) {

		var executor = new ShellCommandExecutor();
		executor.execute("docker image inspect '%s'".formatted(imageName));
		return executor.isExecutionSuccessful();
	}

	private void buildImage(String imageName) {

		Path directory = createBuildContext();
		try {
			var executor = new ShellCommandExecutor();
			executor.execute("docker build -t '%s' '%s'".formatted(imageName, directory));
			if (!executor.isExecutionSuccessful()) {
				throw new RuntimeException("Failed to build Docker image '%s':\n%s".formatted(imageName, executor.getOutput()));
			}
		} finally {
			deleteBuildContext(directory);
		}
	}

	private Path createBuildContext() {

		try {
			Path directory = Files.createTempDirectory("docker-build-");
			for (Entry<String, byte[]> file: files.entrySet()) {
				Files.write(directory.resolve(file.getKey()), file.getValue());
			}
			return directory;
		} catch (IOException exception) {
			throw new UncheckedIOException(exception);
		}
	}

	private void deleteBuildContext(Path directory) {

		try (Stream<Path> paths = Files.walk(directory)) {
			for (Path path: paths.sorted(Comparator.reverseOrder()).toList()) {
				Files.deleteIfExists(path);
			}
		} catch (IOException exception) {
			throw new UncheckedIOException(exception);
		}
	}
}
