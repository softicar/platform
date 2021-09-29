package com.softicar.platform.ajax.testing.docker.compose;

import java.io.File;

/**
 * Generates <tt>docker-compose</tt> commands to manage multi-container
 * applications.
 *
 * @author Alexander Schmidt
 */
public class DockerComposeCommandGenerator {

	/**
	 * Generates a command which invokes <tt>docker-compose</tt> to stop a
	 * multi-container application, as defined in a <tt>docker-compose.yml</tt>
	 * file.
	 *
	 * @param projectName
	 *            the project name to use (never <i>null</i>)
	 * @param composeFile
	 *            the <tt>docker-compose</tt> file to use in the generated
	 *            command (never <i>null</i>)
	 * @return the generated command (never <i>null</i>)
	 */
	public String generateDownCommand(String projectName, File composeFile) {

		return String
			.format(//
				"docker-compose -f %s -p %s --no-ansi down --remove-orphans",
				composeFile.getAbsolutePath(),
				projectName);
	}

	/**
	 * Generates a command which invokes <tt>docker-compose</tt> to start a
	 * multi-container application, as defined in a <tt>docker-compose.yml</tt>
	 * file.
	 *
	 * @param projectName
	 *            the project name to use (never <i>null</i>)
	 * @param composeFile
	 *            the <tt>docker-compose</tt> file to use in the generated
	 *            command (never <i>null</i>)
	 * @return the generated command (never <i>null</i>)
	 */
	public String generateUpCommand(String projectName, File composeFile) {

		return String
			.format(//
				"docker-compose -f %s -p %s --no-ansi up -d",
				composeFile.getAbsolutePath(),
				projectName);
	}
}
