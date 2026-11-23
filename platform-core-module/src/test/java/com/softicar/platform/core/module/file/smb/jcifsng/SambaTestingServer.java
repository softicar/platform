package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.core.module.container.docker.DockerContainerController;
import com.softicar.platform.core.module.container.docker.DockerImageBuilder;
import com.softicar.platform.core.module.file.smb.SmbCredentials;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * An ephemeral Samba server in a local Docker container, for SMB tests.
 * <p>
 * The image is built locally from the {@code Dockerfile} and {@code smb.conf}
 * in the resource directory {@value #RESOURCE_DIRECTORY} next to this class.
 * The SMB port of the container is published on a random port of
 * {@code 127.0.0.1}, so the server is also reachable where container IP
 * addresses are not, e.g. under Docker Desktop on macOS.
 *
 * @author Semsudin Mekanic
 */
public class SambaTestingServer {

	public static final String WORKGROUP = "WORKGROUP";
	public static final String USER = "testuser";
	public static final String PASSWORD = "testpassword";
	public static final String SHARE = "testshare";

	private static final String CONTAINER_NAME_PREFIX = "smb-test";
	private static final String DEFAULT_MINIMUM_PROTOCOL = "SMB2_02";
	private static final String IMAGE_REPOSITORY = "softicar-platform-test/samba-testing-server";
	private static final String MINIMUM_PROTOCOL_LINE = "server min protocol = ";
	private static final String RESOURCE_DIRECTORY = "samba-testing-server/";
	private static final int SMB_PORT = 445;

	private final String minimumProtocol;
	private DockerContainerController containerController;
	private String shareUrl;

	/**
	 * Constructs a {@link SambaTestingServer} that accepts SMB 2.0.2 and newer.
	 */
	public SambaTestingServer() {

		this(DEFAULT_MINIMUM_PROTOCOL);
	}

	/**
	 * Constructs a {@link SambaTestingServer} with the given minimum protocol.
	 *
	 * @param minimumProtocol
	 *            the oldest SMB dialect the server accepts, as for the Samba
	 *            option {@code "server min protocol"}; e.g. {@code "SMB2_02"}
	 *            or {@code "SMB3"} (never <i>null</i>)
	 */
	public SambaTestingServer(String minimumProtocol) {

		this.minimumProtocol = Objects.requireNonNull(minimumProtocol);
		this.containerController = null;
		this.shareUrl = null;
	}

	/**
	 * Builds the image if necessary, and starts the container.
	 * <p>
	 * Returns when the server accepts connections.
	 */
	public void startup() {

		if (containerController == null) {
			containerController = new DockerContainerController(//
				CONTAINER_NAME_PREFIX,
				buildImage(),
				"-d",
				"--rm",
				"-p",
				"127.0.0.1::" + SMB_PORT);
			containerController.registerRuntimeShutdownHook();
		}
		containerController.startup();
		shareUrl = "smb://127.0.0.1:%s/%s".formatted(containerController.getPublishedPort(SMB_PORT), SHARE);
	}

	/**
	 * Stops the container, if it was started.
	 */
	public void shutdown() {

		Optional.ofNullable(containerController).ifPresent(DockerContainerController::shutdown);
		shareUrl = null;
	}

	/**
	 * Returns the URL of the share, without a trailing slash; e.g.
	 * {@code "smb://127.0.0.1:55415/testshare"}.
	 *
	 * @return the share URL (never <i>null</i>)
	 * @throws IllegalStateException
	 *             if the server was not started
	 */
	public String getShareUrl() {

		return Optional//
			.ofNullable(shareUrl)
			.orElseThrow(() -> new IllegalStateException("The Samba testing server was not started."));
	}

	/**
	 * Returns the credentials of the user that has full access to the share.
	 *
	 * @return the credentials (never <i>null</i>)
	 */
	public SmbCredentials getCredentials() {

		return new SmbCredentials(WORKGROUP, USER, PASSWORD);
	}

	private String buildImage() {

		String smbConf = readResource("smb.conf");
		String defaultLine = MINIMUM_PROTOCOL_LINE + DEFAULT_MINIMUM_PROTOCOL;
		if (!smbConf.contains(defaultLine)) {
			throw new IllegalStateException("Missing line in smb.conf: '%s'".formatted(defaultLine));
		}
		return new DockerImageBuilder(IMAGE_REPOSITORY)//
			.addFile("Dockerfile", readResource("Dockerfile"))
			.addFile("smb.conf", smbConf.replace(defaultLine, MINIMUM_PROTOCOL_LINE + minimumProtocol))
			.build();
	}

	private static String readResource(String filename) {

		try (var inputStream = SambaTestingServer.class.getResourceAsStream(RESOURCE_DIRECTORY + filename)) {
			if (inputStream == null) {
				throw new IllegalStateException("Missing resource: %s%s".formatted(RESOURCE_DIRECTORY, filename));
			}
			return StreamUtils.toString(inputStream, Charsets.UTF8);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
