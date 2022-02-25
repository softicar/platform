package com.softicar.platform.core.module.file.smb.testing;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.io.command.ShellCommandExecutor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Facilitates startup and shutdown of a SMB server, to enable Docker based
 * local execution of SMB based unit tests.
 *
 * @author Alexander Schmidt
 */
public class SmbTestServerController {

	private final String containerName;
	private final DockerCommandExecutor commandExecutor;
	private boolean shutdownHookRegistered;
	private String serverIpAddress;

	/**
	 * Creates a new {@link SmbTestServerController} with a globally-unique ID.
	 *
	 * @param configuration
	 *            a {@link SmbTestServerConfiguration} for this
	 *            {@link SmbTestServerController} (never <i>null</i>)
	 */
	public SmbTestServerController(SmbTestServerConfiguration configuration) {

		Objects.requireNonNull(configuration);
		this.containerName = "smb-test-%s".formatted(UUID.randomUUID());
		this.commandExecutor = new DockerCommandExecutor(configuration, containerName);
		this.shutdownHookRegistered = false;
		this.serverIpAddress = null;
	}

	public String getServerIpAddress() {

		return Optional//
			.ofNullable(serverIpAddress)
			.orElseThrow(() -> new SofticarDeveloperException("Failed to retrieve the IP address of the SMB server: The server was not yet started."));
	}

	/**
	 * Registers a shutdown hook in the current {@link Runtime}, to terminate
	 * the SMB server upon {@link Runtime} shutdown.
	 *
	 * @see #shutdown()
	 */
	public void registerRuntimeShutdownHook() {

		if (!shutdownHookRegistered) {
			Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
			this.shutdownHookRegistered = true;
		}
	}

	/**
	 * Starts the SMB server container, and performs setup operations.
	 */
	public void startup() {

		// TODO remove
		Log.ferror("SmbTestServerController: startup");

		commandExecutor.executeServerUp();
		this.serverIpAddress = commandExecutor.executeServerIpAddressRetrieval();
	}

	/**
	 * Terminates the SMB server container, and performs cleanup operations.
	 * <p>
	 * Invoked automatically, if a shutdown hook was registered.
	 *
	 * @see #registerRuntimeShutdownHook()
	 */
	public void shutdown() {

		// TODO remove
		Log.ferror("SmbTestServerController: shutdown");

		commandExecutor.executeServerDown();
	}

	private static class DockerCommandGenerator {

		private final SmbTestServerConfiguration configuration;
		private final String containerName;

		public DockerCommandGenerator(SmbTestServerConfiguration configuration, String containerName) {

			this.configuration = configuration;
			this.containerName = containerName;
		}

		public String generateServerDownCommand() {

			return "docker stop %s || echo 'No longer running.'".formatted(containerName);
		}

		// FIXME This is a proof-of-concept ONLY.
		// FIXME The code in here is totally misplaced, hackish, and plain ugly.
		// FIXME It will fill disks with dangling images.
		public String generateServerUpCommand() {

			String tempDirectory = "/tmp/samba-image-build-%s".formatted(UUID.randomUUID());

			try {
				Files.createDirectory(Path.of(tempDirectory));
			} catch (IOException exception) {
				throw new RuntimeException(exception);
			}

			String smbConfContent = """
					[global]
					log file = /dev/stdout
					server role = standalone server
					server min protocol = SMB2_02

					[testshare]
					path = /testshare
					read only = no
					valid users = testuser
					""";
			try {
				Files.writeString(Path.of(tempDirectory, "smb.conf"), smbConfContent);
			} catch (IOException exception) {
				throw new RuntimeException(exception);
			}

			String dockerfileContent = """
					FROM ubuntu:20.04

					RUN	apt-get update && \
						apt-get install -y samba smbclient dumb-init && \
						adduser --no-create-home --disabled-password --disabled-login --gecos "" testuser && \
						(echo "testpassword"; echo "testpassword") | smbpasswd -s -a testuser && \
						mkdir /testshare && \
						chown testuser:testuser /testshare && \
						rm /etc/samba/smb.conf && \
						rm -rf /var/lib/apt/lists/*

					COPY smb.conf /etc/samba/

					# RUN service smbd force-reload

					EXPOSE 137/udp 138/udp 139 445

					# HEALTHCHECK --interval=60s --timeout=15s CMD smbclient -L \\localhost -U % -m SMB3
					HEALTHCHECK CMD smbclient -L \\localhost -U % -m SMB3

					# ENTRYPOINT ["/sbin/tini", "--"]
					ENTRYPOINT ["/usr/bin/dumb-init", "-v", "--"]
					CMD ["/usr/sbin/smbd", "-FS", "--no-process-group"]
					""";

			try {
				Files.writeString(Path.of(tempDirectory, "Dockerfile"), dockerfileContent);
			} catch (IOException exception) {
				throw new RuntimeException(exception);
			}

			return new StringBuilder()//
				.append("cd %s && ".formatted(tempDirectory))
				.append("docker build -t softicar/samba-server . && ")
				.append("docker run -dit --rm ")
				.append("--name=\"%s\" ".formatted(containerName))
				.append("softicar/samba-server ")
				.toString();
		}

		public String generateServerLogsCommand() {

			return "docker logs %s".formatted(containerName);
		}

		public String generateServerIpAddressRetrievalCommand() {

			return "docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' %s".formatted(containerName);
		}
	}

	private static class DockerCommandExecutor {

		// FIXME this should be lower
		private static final int SERVER_READY_POLLING_INTERVAL_MS = 500;
		private static final int SERVER_READY_POLLING_RETRY_COUNT = 400;

		private final DockerCommandGenerator commandGenerator;

		public DockerCommandExecutor(SmbTestServerConfiguration configuration, String containerName) {

			this.commandGenerator = new DockerCommandGenerator(configuration, containerName);
		}

		public void executeServerDown() {

			execute(commandGenerator.generateServerDownCommand(), "shutdown");
		}

		public void executeServerUp() {

			execute(commandGenerator.generateServerUpCommand(), "startup");
			waitForServer();
		}

		public String executeServerIpAddressRetrieval() {

			return execute(commandGenerator.generateServerIpAddressRetrievalCommand(), "IP address retrieval").trim();
		}

		private String execute(String command, String commandPurpose) {

			var executor = new ShellCommandExecutor();
			executor.execute(command);

			if (executor.isExecutionSuccessful()) {
				return executor.getOutput();
			} else {
				String message = String.format("Docker '%s' command failed:\n", commandPurpose);
				if (executor.isExecutionTimedOut()) {
					message += "Timeout encountered.\n";
				}
				message += "Command was:\n";
				message += ">>>>\n";
				message += command + "\n";
				message += "<<<<\n";
				message += "Output was:\n";
				message += ">>>>\n";
				message += executor.getOutput() + "\n";
				message += "<<<<\n";
				throw new RuntimeException(message);
			}
		}

		private void waitForServer() {

			int retryCounter = 0;
			while (true) {
				if (retryCounter >= SERVER_READY_POLLING_RETRY_COUNT) {
					throw new SofticarDeveloperException("SMB server startup timeout.");
				} else if (isServerReady()) {
					break;
				} else {
					++retryCounter;
					Sleep.sleep(SERVER_READY_POLLING_INTERVAL_MS);
				}
			}
		}

		private boolean isServerReady() {

			return execute(commandGenerator.generateServerLogsCommand(), "ready-check").contains("daemon_ready");
		}
	}
}
