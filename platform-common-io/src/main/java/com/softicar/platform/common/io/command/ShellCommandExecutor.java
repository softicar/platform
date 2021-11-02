package com.softicar.platform.common.io.command;

import com.softicar.platform.common.io.file.line.FileLineIterator;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Executes a shell command in a separate process.
 *
 * @author Alexander Schmidt
 */
public class ShellCommandExecutor {

	private static final String DEFAULT_SHELL = "sh";
	private static final String DEFAULT_SHELL_COMMAND_ARGUMENT = "-c";
	private static final long DEFAULT_TIMEOUT = 0;

	private String shell;
	private String shellCommandArgument;
	private long timeout;
	private TimeUnit timeoutTimeUnit;
	private StringBuilder executionOutput;
	private boolean executionSuccessful;
	private boolean executionTimedOut;

	public ShellCommandExecutor() {

		this.shell = DEFAULT_SHELL;
		this.shellCommandArgument = DEFAULT_SHELL_COMMAND_ARGUMENT;
		this.timeout = DEFAULT_TIMEOUT;
		this.timeoutTimeUnit = null;
		resetExecutionResults();
	}

	/**
	 * Specifies the name of the shell binary which shall be used to invoke the
	 * command.
	 * <p>
	 * Typical examples include "<tt>sh</tt>" and "<tt>bash</tt>".
	 * <p>
	 * By default, {@value #DEFAULT_SHELL} is used.
	 *
	 * @param shell
	 *            the name of the shell binary to use (never <i>null</i>)
	 * @return this {@link ShellCommandExecutor}
	 */
	public ShellCommandExecutor setShell(String shell) {

		this.shell = Objects.requireNonNull(shell);
		return this;
	}

	/**
	 * Specifies the name of the shell argument which is required to make the
	 * shell binary expect a command from a passed argument, rather than from
	 * standard input.
	 * <p>
	 * Typically, "<tt>-c</tt>" is required here.
	 * <p>
	 * By default, {@value #DEFAULT_SHELL_COMMAND_ARGUMENT} is used.
	 *
	 * @param shellCommandArgument
	 *            the command mode argument of the shell binary (never
	 *            <i>null</i>)
	 * @return this {@link ShellCommandExecutor}
	 * @see #setShell(String)
	 */
	public ShellCommandExecutor setShellCommandArgument(String shellCommandArgument) {

		this.shellCommandArgument = Objects.requireNonNull(shellCommandArgument);
		return this;
	}

	/**
	 * Defines the amount of time to wait for the shell command to return.
	 * <p>
	 * If the given timeout value is <tt>0</tt>, the current thread will wait
	 * upon command execution, until the created {@link Process} has terminated.
	 * <p>
	 * By default, a timeout of {@value #DEFAULT_TIMEOUT} is used.
	 *
	 * @param timeout
	 *            the maximum time to wait for the shell command to return (at
	 *            least <tt>0</tt>)
	 * @param timeoutTimeUnit
	 *            the {@link TimeUnit} that qualifies the waiting time (never
	 *            <i>null</i>)
	 * @return this {@link ShellCommandExecutor}
	 * @throws IllegalArgumentException
	 *             if the given timeout value is <tt>&lt;0</tt>
	 */
	public ShellCommandExecutor setTimeout(long timeout, TimeUnit timeoutTimeUnit) {

		if (timeout < 0) {
			throw new IllegalArgumentException();
		}
		this.timeout = timeout;
		this.timeoutTimeUnit = Objects.requireNonNull(timeoutTimeUnit);
		return this;
	}

	/**
	 * Executes the given shell command in a separate process.
	 * <p>
	 * Records the output of the command (with the error output being redirected
	 * to the standard output), for subsequent retrieval via
	 * {@link #getOutput()}.
	 *
	 * @param command
	 *            the shell command to execute (never <i>null</i>)
	 */
	public void execute(String command) {

		try {
			resetExecutionResults();

			ProcessBuilder processBuilder = new ProcessBuilder(new String[] { shell, shellCommandArgument, command });
			processBuilder.directory(new File("."));
			processBuilder.redirectErrorStream(true);
			Process process = processBuilder.start();

			try (FileLineIterator iterator = FileLineIterator.readUtf8(process::getInputStream)) {
				for (String line: iterator) {
					executionOutput.append(line + "\n");
				}
			}

			if (isTimeoutDefined()) {
				this.executionSuccessful = process.waitFor(timeout, timeoutTimeUnit);
				this.executionTimedOut = !executionSuccessful;
			} else {
				this.executionSuccessful = process.waitFor() == 0;
				this.executionTimedOut = false;
			}
		} catch (IOException | InterruptedException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Retrieves the output of the most recently executed command.
	 *
	 * @return the output of the command (never <i>null</i>)
	 */
	public String getOutput() {

		return executionOutput.toString();
	}

	/**
	 * Determines whether the command terminated normally.
	 * <p>
	 * That is, if the exit value was <tt>0</tt> and if it terminated before the
	 * defined timeout (if any) was encountered.
	 *
	 * @return <i>true</i> if the command terminated normally; <i>false</i>
	 *         otherwise
	 */
	public boolean isExecutionSuccessful() {

		return executionSuccessful;
	}

	/**
	 * Determines whether a timeout was encountered while waiting for the
	 * termination of the command.
	 *
	 * @return <i>true</i> if a timeout was encountered; <i>false</i> otherwise
	 */
	public boolean isExecutionTimedOut() {

		return executionTimedOut;
	}

	private boolean isTimeoutDefined() {

		return timeout > 0 && timeoutTimeUnit != null;
	}

	private void resetExecutionResults() {

		this.executionOutput = new StringBuilder();
		this.executionSuccessful = false;
		this.executionTimedOut = false;
	}
}
