package com.softicar.platform.core.module.container.docker;

import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.io.command.ShellCommandExecutor;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Manages startup and shutdown of a docker-containerized service.
 *
 * @author Alexander Schmidt
 */
public class DockerContainerController {

	private final String containerName;
	private final DockerCommandExecutor commandExecutor;
	private boolean shutdownHookRegistered;
	private String containerIpAddress;

	/**
	 * Constructs a new {@link DockerContainerController}.
	 *
	 * @param containerNamePrefix
	 *            a prefix for the names of the managed Docker containers; e.g.
	 *            {@code "myservice-test"} (never <i>null</i>)
	 * @param dockerImage
	 *            the name of the Docker image or repository to start; e.g.
	 *            {@code "myvendor/myservice:1.0.0"} or
	 *            {@code "myvendor/myservice"} (assuming {@code ":latest"})
	 *            (never <i>null</i>)
	 */
	public DockerContainerController(String containerNamePrefix, String dockerImage) {

		assertValidContainerNamePrefix(containerNamePrefix);
		assertValidImageName(dockerImage);

		this.containerName = "%s-%s".formatted(containerNamePrefix, UUID.randomUUID());
		this.commandExecutor = new DockerCommandExecutor(containerName, dockerImage);
		this.shutdownHookRegistered = false;
		this.containerIpAddress = null;
	}

	public String getContainerIpAddress() {

		return Optional//
			.ofNullable(containerIpAddress)
			.orElseThrow(() -> new RuntimeException("Failed to retrieve the IP address of the container: The container was not yet started."));
	}

	/**
	 * Registers a shutdown hook in the current {@link Runtime}, to terminate
	 * the container upon {@link Runtime} shutdown.
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
	 * Terminates the container, and performs cleanup operations.
	 * <p>
	 * Invoked automatically, if a shutdown hook was registered.
	 *
	 * @see #registerRuntimeShutdownHook()
	 */
	public void shutdown() {

		commandExecutor.executeContainerDown();
	}

	/**
	 * Starts the container, and performs setup operations.
	 */
	public void startup() {

		commandExecutor.executeContainerUp();
		this.containerIpAddress = commandExecutor.executeContainerIpAddressRetrieval();
	}

	private void assertValidContainerNamePrefix(String containerNamePrefix) {

		Objects.requireNonNull(containerNamePrefix);
		if (containerNamePrefix.isBlank()) {
			throw new IllegalArgumentException();
		}
	}

	private void assertValidImageName(String imageName) {

		Objects.requireNonNull(imageName);
		if (imageName.isBlank()) {
			throw new IllegalArgumentException();
		}
	}

	private static class DockerCommandGenerator {

		private final String containerName;
		private final String imageName;

		public DockerCommandGenerator(String containerName, String imageName) {

			this.containerName = containerName;
			this.imageName = imageName;
		}

		public String generateContainerDownCommand() {

			return "docker stop %s || echo 'No longer running.'".formatted(containerName);
		}

		public String generateContainerUpCommand() {

			return "docker run -dit --rm --name=\"%s\" %s".formatted(containerName, imageName);
		}

		public String generateContainerHealthStatusCommand() {

			return "docker inspect -f '{{ .State.Health.Status }}' %s".formatted(containerName);
		}

		public String generateContainerIpAddressRetrievalCommand() {

			return "docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' %s".formatted(containerName);
		}
	}

	private static class DockerCommandExecutor {

		// TODO should be configurable
		private static final int SERVICE_READY_POLLING_INTERVAL_MS = 50;
		private static final int SERVICE_READY_POLLING_RETRY_COUNT = 600;

		private final DockerCommandGenerator commandGenerator;

		public DockerCommandExecutor(String containerName, String imageName) {

			this.commandGenerator = new DockerCommandGenerator(containerName, imageName);
		}

		public void executeContainerDown() {

			execute(commandGenerator.generateContainerDownCommand(), "shutdown");
		}

		public void executeContainerUp() {

			execute(commandGenerator.generateContainerUpCommand(), "startup");
			waitForService();
		}

		public String executeContainerIpAddressRetrieval() {

			return execute(commandGenerator.generateContainerIpAddressRetrievalCommand(), "IP address retrieval").trim();
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

		private void waitForService() {

			int retryCounter = 0;
			while (true) {
				if (retryCounter >= SERVICE_READY_POLLING_RETRY_COUNT) {
					throw new RuntimeException("Containerized service startup timeout.");
				} else if (isContainerReady()) {
					break;
				} else {
					++retryCounter;
					Sleep.sleep(SERVICE_READY_POLLING_INTERVAL_MS);
				}
			}
		}

		private boolean isContainerReady() {

			return execute(commandGenerator.generateContainerHealthStatusCommand(), "health check").trim().equals("healthy");
		}
	}
}
