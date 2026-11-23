package com.softicar.platform.core.module.container.docker;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * Tests the input validation of {@link DockerImageBuilder}.
 * <p>
 * Building images is covered by tests that use Docker, e.g. the SMB tests.
 *
 * @author Semsudin Mekanic
 */
public class DockerImageBuilderTest extends AbstractTest {

	private final DockerImageBuilder builder = new DockerImageBuilder("softicar-platform-test/some-image");

	@Test
	public void testAddFileWithDirectory() {

		assertThrows(IllegalArgumentException.class, () -> builder.addFile("some/file", "content"));
		assertThrows(IllegalArgumentException.class, () -> builder.addFile("some\\file", "content"));
	}

	@Test
	public void testAddFileWithHiddenOrParentName() {

		assertThrows(IllegalArgumentException.class, () -> builder.addFile(".dockerignore", "content"));
		assertThrows(IllegalArgumentException.class, () -> builder.addFile("..", "content"));
	}

	@Test
	public void testAddFileWithBlankName() {

		assertThrows(IllegalArgumentException.class, () -> builder.addFile(" ", "content"));
	}

	@Test
	public void testBuildWithoutDockerfile() {

		builder.addFile("smb.conf", "content");

		assertThrows(IllegalStateException.class, builder::build);
	}
}
