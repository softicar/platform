package com.softicar.platform.ajax.testing.selenium.grid;

import com.softicar.platform.ajax.testing.docker.compose.DockerComposeCommandGenerator;
import com.softicar.platform.ajax.testing.selenium.AjaxSeleniumTestProperties;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.grid.ISeleniumGridConfiguration;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.grid.SeleniumGridConfigurationBySystemPropertyFactory;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.io.command.ShellCommandExecutor;
import com.softicar.platform.common.io.file.lock.LockFile;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Facilitates startup and shutdown of a Selenium grid, to enable Docker based
 * local execution of Selenium based unit tests.
 *
 * @author Alexander Schmidt
 */
public class AjaxSeleniumGridController {

	private static final AjaxSeleniumGridController INSTANCE = new AjaxSeleniumGridController();
	private final ISeleniumGridConfiguration gridConfiguration;
	private final TemporaryFileManager fileManager;
	private final ComposeCommandExecutor commandExecutor;
	private boolean shutdownHookRegistered;

	private AjaxSeleniumGridController() {

		this.gridConfiguration = new SeleniumGridConfigurationBySystemPropertyFactory().create();
		this.fileManager = new TemporaryFileManager(gridConfiguration);
		this.commandExecutor = new ComposeCommandExecutor(gridConfiguration, fileManager.getComposeFile());
		this.shutdownHookRegistered = false;
	}

	/**
	 * Returns the singleton instance of this {@link AjaxSeleniumGridController}
	 *
	 * @return the singleton {@link AjaxSeleniumGridController} (never
	 *         <i>null</i>)
	 */
	public static AjaxSeleniumGridController getInstance() {

		return INSTANCE;
	}

	/**
	 * Registers a shutdown hook in the current {@link Runtime}, to terminate
	 * the Selenium grid upon {@link Runtime} shutdown.
	 * <p>
	 * If {@link ISeleniumGridConfiguration#isDeferredGridShutdown()} is
	 * <i>true</i>, this method does nothing.
	 *
	 * @see #shutdown()
	 */
	public synchronized void registerRuntimeShutdownHook() {

		if (!gridConfiguration.isDeferredGridShutdown() && !shutdownHookRegistered) {
			Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
			this.shutdownHookRegistered = true;
		}
	}

	/**
	 * Starts the Selenium grid, and performs setup operations.
	 */
	public synchronized void startup() {

		try (FluentFileLock lock = createOperationLock()) {
			if (!fileManager.getRunningLockFile().isLocked()) {
				fileManager.writeComposeFile();
				if (gridConfiguration.isDeferredGridShutdown()) {
					fileManager.writeShutdownScript();
				}
				commandExecutor.executeGridUp();
				fileManager.getRunningLockFile().lock();
			}
		}
	}

	/**
	 * Terminates the Selenium grid, and performs cleanup operations.
	 * <p>
	 * Invoked automatically, if a shutdown hook was registered.
	 *
	 * @see #registerRuntimeShutdownHook()
	 */
	public synchronized void shutdown() {

		if (fileManager.getDirectory().exists()) {
			try (FluentFileLock lock = createOperationLock()) {
				if (fileManager.getRunningLockFile().isLocked()) {
					commandExecutor.executeGridDown();
					fileManager.deleteDirectory();
				}
			}
		}
	}

	private FluentFileLock createOperationLock() {

		return new FluentFileLock(fileManager.getOperationLockFile());
	}

	private static class FluentFileLock implements AutoCloseable {

		private static final int POLLING_RATE_MILLIS = 50;
		private final LockFile lockFile;

		public FluentFileLock(LockFile lockFile) {

			this.lockFile = lockFile;

			while (true) {
				if (!lockFile.lock()) {
					Sleep.sleep(POLLING_RATE_MILLIS);
				} else {
					return;
				}
			}
		}

		@Override
		public void close() {

			lockFile.unlock();
		}
	}

	private static class ComposeCommandGenerator {

		private final ISeleniumGridConfiguration gridConfiguration;
		private final File composeFile;

		public ComposeCommandGenerator(ISeleniumGridConfiguration gridConfiguration, File composeFile) {

			this.gridConfiguration = gridConfiguration;
			this.composeFile = composeFile;
		}

		public String generateGridDownCommand() {

			return new DockerComposeCommandGenerator()
				.generateDownCommand(//
					gridConfiguration.getUuid(),
					composeFile);
		}

		public String generateGridUpCommand() {

			return new DockerComposeCommandGenerator()
				.generateUpCommand(//
					gridConfiguration.getUuid(),
					composeFile);
		}
	}

	private static class ComposeCommandExecutor {

		private final ISeleniumGridConfiguration gridConfiguration;
		private final File composeFile;

		public ComposeCommandExecutor(ISeleniumGridConfiguration gridConfiguration, File composeFile) {

			this.gridConfiguration = gridConfiguration;
			this.composeFile = composeFile;
		}

		public void executeGridDown() {

			String command = new ComposeCommandGenerator(gridConfiguration, composeFile).generateGridDownCommand();
			execute(command, "shutdown");
		}

		public void executeGridUp() {

			String command = new ComposeCommandGenerator(gridConfiguration, composeFile).generateGridUpCommand();
			execute(command, "startup");
		}

		private void execute(String command, String commandPurpose) {

			ShellCommandExecutor executor = new ShellCommandExecutor();
			executor.execute(command);
			if (!executor.isExecutionSuccessful()) {
				String message = String.format("Grid %s command failed:\n", commandPurpose);
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

	private static class TemporaryFileManager {

		private final ISeleniumGridConfiguration gridConfiguration;
		private final File directory;
		private final LockFile operationLockFile;
		private final LockFile runningLockFile;
		private final File composeFile;
		private final File shutdownCommandFile;

		public TemporaryFileManager(ISeleniumGridConfiguration gridConfiguration) {

			this.gridConfiguration = gridConfiguration;
			this.directory = new File(gridConfiguration.getTemporaryDirectory());
			this.operationLockFile = new LockFile(directory, "grid-operation.lock");
			this.runningLockFile = new LockFile(directory, "grid-running.lock");
			this.composeFile = new File(directory, "docker-compose.yml");
			this.shutdownCommandFile = new File(directory, AjaxSeleniumTestProperties.GRID_SHUTDOWN_SCRIPT.getValue());
		}

		public File getDirectory() {

			return directory;
		}

		public LockFile getOperationLockFile() {

			return operationLockFile;
		}

		public LockFile getRunningLockFile() {

			return runningLockFile;
		}

		public void deleteDirectory() {

			for (File file: directory.listFiles()) {
				file.delete();
			}
			directory.delete();
		}

		public File getComposeFile() {

			return composeFile;
		}

		public TemporaryFileManager writeComposeFile() {

			return writeFile(composeFile, generateComposeFileContent());
		}

		public TemporaryFileManager writeShutdownScript() {

			return writeFile(shutdownCommandFile, generateShutdownCommandFileContent());
		}

		private TemporaryFileManager writeFile(File file, String content) {

			file.getParentFile().mkdirs();
			try (FileWriter fileWriter = new FileWriter(file)) {
				fileWriter.write(content);
				return this;
			} catch (IOException exception) {
				throw new RuntimeException(exception);
			}
		}

		private String generateComposeFileContent() {

			return new SleniumGridDockerComposeFileGenerator().generate(gridConfiguration);
		}

		/**
		 * Generates a shell script to shut down the Selenium grid.
		 * <p>
		 * The generated script will trap <tt>SIGTERM</tt> to avoid that the CI
		 * build executor kills <tt>docker-compose</tt> prematurely when a job
		 * run concludes.
		 *
		 * @return the content of the generated shell script (never <i>null</i>)
		 */
		private String generateShutdownCommandFileContent() {

			StringBuilder content = new StringBuilder();
			content.append("#!/bin/sh\n");
			content.append("trap -- '' TERM\n");
			content.append(new ComposeCommandGenerator(gridConfiguration, composeFile).generateGridDownCommand());
			return content.toString();
		}
	}
}
