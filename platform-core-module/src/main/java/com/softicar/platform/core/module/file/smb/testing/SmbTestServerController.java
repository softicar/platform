package com.softicar.platform.core.module.file.smb.testing;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.io.command.ShellCommandExecutor;
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
	 */
	public SmbTestServerController() {

		this.containerName = "smb-test-%s".formatted(UUID.randomUUID());
		this.commandExecutor = new DockerCommandExecutor(containerName);
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

		commandExecutor.executeServerDown();
	}

	private static class DockerCommandGenerator {

		private final String containerName;

		public DockerCommandGenerator(String containerName) {

			this.containerName = containerName;
		}

		public String generateServerDownCommand() {

			return "docker stop %s || echo 'No longer running.'".formatted(containerName);
		}

		public String generateServerUpCommand() {

			return new StringBuilder()//
				.append("docker run -dit --rm ")
				.append("--name=\"%s\" ".formatted(containerName))
				.append("softicar/samba-testing-server ")
				.toString();
		}

		public String generateServerHealthStatusCommand() {

			return "docker inspect -f '{{ .State.Health.Status }}' %s".formatted(containerName);
		}

		public String generateServerIpAddressRetrievalCommand() {

			return "docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' %s".formatted(containerName);
		}
	}

	private static class DockerCommandExecutor {

		private static final int SERVER_READY_POLLING_INTERVAL_MS = 50;
		private static final int SERVER_READY_POLLING_RETRY_COUNT = 200;

		private final DockerCommandGenerator commandGenerator;

		public DockerCommandExecutor(String containerName) {

			this.commandGenerator = new DockerCommandGenerator(containerName);
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

			return execute(commandGenerator.generateServerHealthStatusCommand(), "health check").trim().equals("healthy");
		}
	}
}
