package com.softicar.platform.common.network.udp;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.network.address.Ipv4Address;
import com.softicar.platform.common.string.unicode.Utf8Convering;
import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * A simple implementation for Udp connections.
 * <p>
 * This class can be used for sending Udp messages to the remote end and to wait
 * for a reply message.
 *
 * @author Oliver Richers
 */
public class UdpConnection implements Closeable {

	private static final int MAX_REPLY_SIZE = 10000;
	private static final int TIMEOUT = 1000;
	private static final int RETRY = 8;
	private final int targetPort;
	private final InetAddress targetAddress;
	private final DatagramSocket socket;

	public UdpConnection(String targetAddress, int targetPort) {

		this.targetPort = targetPort;
		this.targetAddress = new Ipv4Address(targetAddress).toInetAddress();
		this.socket = createSocket();
	}

	public static String sendAndReceive(String address, int port, String message) {

		try (UdpConnection udpConnection = new UdpConnection(address, port)) {
			return udpConnection.sendAndReceive(message);
		}
	}

	@Override
	public void close() {

		socket.close();
	}

	public void sendMessage(String message) {

		byte[] buffer = Utf8Convering.toUtf8(message);
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, targetAddress, targetPort);
		sendPacket(packet);
	}

	/**
	 * Sends the specified message to the target address and port, and waits for
	 * a reply.
	 *
	 * @param message
	 *            the message to send
	 * @return the reply message
	 * @throws NoReplyException
	 *             if no reply was received before timeout
	 * @throws SofticarIOException
	 *             if some kind of i/o error occurred
	 */
	public String sendAndReceive(String message) {

		byte[] buf = new byte[MAX_REPLY_SIZE];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		for (int i = 0; i < RETRY; ++i) {
			setTimeout(computeTimeout(i));
			sendMessage(message);

			try {
				receivePacket(packet);
				return Utf8Convering.fromUtf8(buf, packet.getOffset(), packet.getLength());
			} catch (SocketTimeoutException timeout) {
				DevNull.swallow(timeout);
				Log.finfo("%s. timeout while waiting for answer.", i + 1);
				continue;
			}
		}

		throw new NoReplyException(message, targetAddress.getHostAddress(), targetPort);
	}

	private int computeTimeout(int i) {

		// 1 << i == 2 ^ i
		return (1 << i) * TIMEOUT;
	}

	private void setTimeout(int timeout) {

		try {
			socket.setSoTimeout(timeout);
		} catch (SocketException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private void receivePacket(DatagramPacket packet) throws SocketTimeoutException {

		try {
			socket.receive(packet);
		} catch (SocketTimeoutException exception) {
			throw exception;
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private void sendPacket(DatagramPacket packet) {

		try {
			socket.send(packet);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private static DatagramSocket createSocket() {

		try {
			return new DatagramSocket();
		} catch (SocketException exception) {
			throw new SofticarIOException(exception);
		}
	}

	public static class NoReplyException extends SofticarException {

		private static final long serialVersionUID = 1L;

		public NoReplyException(String sentMessage, String address, int port) {

			super("No reply when sending message '%s' to %s:%s.", sentMessage, address, port);
			m_sentMessage = sentMessage;
			m_address = address;
			m_port = port;
		}

		public String getSentMessage() {

			return m_sentMessage;
		}

		public String getAddress() {

			return m_address;
		}

		public int getPort() {

			return m_port;
		}

		private final String m_sentMessage;
		private final String m_address;
		private final int m_port;
	}
}
