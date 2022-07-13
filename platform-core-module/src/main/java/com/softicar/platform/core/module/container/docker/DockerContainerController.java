package com.softicar.platform.core.module.container.docker;

import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.io.command.ShellCommandExecutor;
import com.softicar.platform.common.string.Imploder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Manages startup and shutdown of a docker-containerized service.
 *
 * @author Alexander Schmidt
 */
public class DockerContainerController {

	private final DockerCommandExecutor commandExecutor;
	private boolean shutdownHookRegistered;
	private String containerIpAddress;

	/**
	 * Constructs a new {@link DockerContainerController}.
	 *
	 * @param containerNamePrefix
	 *            a prefix for the names of the managed Docker containers; e.g.
	 *            {@code "myservice-test"} (never <i>null</i>)
	 * @param imageName
	 *            the name of the Docker image or repository to start; e.g.
	 *            {@code "myvendor/myservice:1.0.0"} or
	 *            {@code "myvendor/myservice"} (assuming {@code ":latest"})
	 *            (never <i>null</i>)
	 * @param runOptions
	 *            options to pass to the {@code "docker run"} command; e.g.
	 *            {@code "-dit", "--rm"} (never <i>null</i>)
	 */
	public DockerContainerController(String containerNamePrefix, String imageName, String...runOptions) {

		this.commandExecutor = new DockerCommandExecutor(containerNamePrefix, imageName, runOptions);
		this.shutdownHookRegistered = false;
		this.containerIpAddress = null;
	}

	/**
	 * Returns the IP address of the started container.
	 *
	 * @return the IP address (never <i>null</i>)
	 * @throws RuntimeException
	 *             if the container was not yet started
	 */
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

	private static class DockerCommandExecutor {

		// TODO should be configurable
		private static final int SERVICE_READY_POLLING_INTERVAL_MS = 50;
		private static final int SERVICE_READY_POLLING_RETRY_COUNT = 600;

		private final String containerName;
		private final String imageName;
		private final String runOptions;

		public DockerCommandExecutor(String containerNamePrefix, String imageName, String[] runOptions) {

			assertValidContainerNamePrefix(containerNamePrefix);
			assertValidImageName(imageName);
			assertValidRunOptions(runOptions);

			this.containerName = "%s-%s".formatted(containerNamePrefix, UUID.randomUUID());
			this.imageName = imageName;
			this.runOptions = Imploder.implode(runOptions, " ");
		}

		public void executeContainerDown() {

			new ContainerDownCommand(containerName).execute();
		}

		public void executeContainerUp() {

			new ContainerUpCommand(containerName, imageName, runOptions).execute();
			waitForService();
		}

		public String executeContainerIpAddressRetrieval() {

			return new ContainerIpAddressCommand(containerName).execute();
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

			if (new ContainerHealthCommand(containerName).execute().equals("<nil>")) {
				return true;
			}

			return new ContainerHealthStatusCommand(containerName).execute().equals("healthy");
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

		private void assertValidRunOptions(String[] runOptions) {

			Objects.requireNonNull(runOptions);
			List<String> optionList = Arrays.asList(runOptions);
			optionList.forEach(option -> {
				Objects.requireNonNull(option);
				if (option.contains("--name")) {
					throw new IllegalArgumentException();
				}
			});
		}
	}

	private static class ShellCommand {

		private final String command;

		public ShellCommand(String commandFormat, Object...args) {

			this.command = commandFormat.formatted(args);
		}

		public String execute() {

			var executor = new ShellCommandExecutor();
			executor.execute(command);

			if (executor.isExecutionSuccessful()) {
				return executor.getOutput().trim();
			} else {
				String message = String.format("Docker command failed:\n");
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
	}

	private static class ContainerDownCommand extends ShellCommand {

		public ContainerDownCommand(String containerName) {

			super("docker stop %s || echo 'No longer running.'", containerName);
		}
	}

	private static class ContainerUpCommand extends ShellCommand {

		public ContainerUpCommand(String containerName, String imageName, String runOptions) {

			super("docker run --name=\"%s\" %s %s", containerName, runOptions, imageName);
		}
	}

	private static class ContainerHealthCommand extends ShellCommand {

		public ContainerHealthCommand(String containerName) {

			super("docker inspect -f '{{.State.Health}}' %s", containerName);
		}
	}

	private static class ContainerHealthStatusCommand extends ShellCommand {

		public ContainerHealthStatusCommand(String containerName) {

			super("docker inspect -f '{{.State.Health.Status}}' %s", containerName);
		}
	}

	private static class ContainerIpAddressCommand extends ShellCommand {

		public ContainerIpAddressCommand(String containerName) {

			super("docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' %s", containerName);
		}
	}
}
