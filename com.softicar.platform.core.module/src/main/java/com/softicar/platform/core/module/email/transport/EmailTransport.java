package com.softicar.platform.core.module.email.transport;

import com.softicar.platform.common.core.thread.sleep.Sleep;
import java.util.List;
import java.util.function.Supplier;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

public class EmailTransport {

	private static final int MAX_CONNECT_RETRY = 100;
	private final IEmailTransportServer server;
	private final Supplier<Transport> transportSupplier;

	public EmailTransport(IEmailTransportServer server, Supplier<Transport> transportSupplier) {

		this.server = server;
		this.transportSupplier = transportSupplier;
	}

	public void sendMessage(Message message, List<Address> addresses) {

		try (Transport transport = transportSupplier.get()) {
			tryToConnectTransport(transport);
			transport.sendMessage(message, getAsArray(addresses));
		} catch (MessagingException exception1) {
			throw new RuntimeException(exception1);
		}
	}

	private void tryToConnectTransport(Transport transport) {

		// entry connection retry loop
		MessagingException connectionException = null;
		for (int i = 0; i < MAX_CONNECT_RETRY; ++i) {
			try {
				connectTransport(transport);
				return;
			} catch (MessagingException exception) {
				connectionException = exception;
				Sleep.sleep(500);
			}
		}

		if (connectionException != null) {
			throw new RuntimeException(connectionException);
		}
	}

	private void connectTransport(Transport transport) throws MessagingException {

		transport.connect(server.getAddress(), server.getPort(), server.getUsername(), server.getPassword());
	}

	private Address[] getAsArray(List<Address> addressesList) {

		Address[] addressesArray = new Address[addressesList.size()];

		for (int i = 0; i < addressesList.size(); i++) {
			addressesArray[i] = addressesList.get(i);
		}

		return addressesArray;
	}
}
