package com.softicar.platform.ajax.testing.docker.compose;

import com.softicar.platform.common.testing.AbstractTest;
import java.io.File;
import org.junit.Test;

public class DockerComposeCommandGeneratorTest extends AbstractTest {

	private final DockerComposeCommandGenerator generator;

	public DockerComposeCommandGeneratorTest() {

		this.generator = new DockerComposeCommandGenerator();
	}

	@Test
	public void testGenerateDownCommand() {

		String gridId = "888b440b-5b39-4984-9231-c935323a65b8";
		File composeFile = new File("/path/to/file/docker-compose.yml");

		String downCommand = generator.generateDownCommand(gridId, composeFile);

		assertEquals(//
			"docker-compose -f /path/to/file/docker-compose.yml -p 888b440b-5b39-4984-9231-c935323a65b8 --ansi never down --remove-orphans",
			downCommand);
	}

	@Test
	public void testGenerateUpCommand() {

		String gridId = "2a785e41-d075-438e-8c68-87331e7248fd";
		File composeFile = new File("/path/to/file/docker-compose.yml");

		String upCommand = generator.generateUpCommand(gridId, composeFile);

		assertEquals(//
			"docker-compose -f /path/to/file/docker-compose.yml -p 2a785e41-d075-438e-8c68-87331e7248fd --ansi never up -d",
			upCommand);
	}
}
