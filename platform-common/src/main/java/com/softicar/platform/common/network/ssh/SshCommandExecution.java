package com.softicar.platform.common.network.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.network.sftp.JschRuntimeException;
import com.softicar.platform.common.string.unicode.Utf8Convering;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class SshCommandExecution implements AutoCloseable {

	private static final int BUFFER_SIZE = 1000;
	private final Session session;
	private final ChannelExec channel;
	private final InputStream inputStream;
	private final InputStream errorStream;

	public SshCommandExecution(Session session, String command) {

		this.session = session;
		this.channel = openChannel();
		this.channel.setCommand(command);
		this.inputStream = getInputStream();
		this.errorStream = getErrorStream();

		try {
			this.channel.connect(10 * 1000);
		} catch (JSchException exception) {
			throw new JschRuntimeException(exception);
		}
	}

	@Override
	public void close() {

		channel.disconnect();
	}

	public boolean isFinished() {

		return channel.isClosed();
	}

	public int getExitStatus() {

		return channel.getExitStatus();
	}

	public List<String> getAllOutputLines() {

		String text = Utf8Convering.fromUtf8(getAllOutput());
		return Arrays.asList(text.split("\n"));
	}

	public byte[] getAllOutput() {

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		readAllOutput(output);
		return output.toByteArray();
	}

	public void readAllOutput(OutputStream output) {

		while (waitForOutput(inputStream)) {
			getSomeOutput(inputStream, output);
		}
	}

	public List<String> getAllErrorLines() {

		String text = Utf8Convering.fromUtf8(getAllErrorOutput());
		return Arrays.asList(text.split("\n"));
	}

	public byte[] getAllErrorOutput() {

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		readAllErrorOutput(output);
		return output.toByteArray();
	}

	public void readAllErrorOutput(OutputStream output) {

		while (waitForOutput(errorStream)) {
			getSomeOutput(errorStream, output);
		}
	}

	private static int getSomeOutput(InputStream input, OutputStream output) {

		try {
			int totalCount = 0;
			while (input.available() > 0) {
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = input.read(buffer, 0, BUFFER_SIZE);
				if (count < 0) {
					break;
				}
				output.write(buffer, 0, count);
				totalCount += count;
			}
			return totalCount;
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private boolean waitForOutput(InputStream input) {

		while (!isAvailable(input) && !channel.isClosed()) {
			Sleep.sleep(100);
		}
		return isAvailable(input);
	}

	private ChannelExec openChannel() {

		try {
			return (ChannelExec) session.openChannel("exec");
		} catch (JSchException exception) {
			throw new JschRuntimeException(exception);
		}
	}

	private InputStream getInputStream() {

		try {
			return channel.getInputStream();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private InputStream getErrorStream() {

		try {
			return channel.getErrStream();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private static boolean isAvailable(InputStream input) {

		try {
			return input.available() > 0;
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
