package com.softicar.platform.core.module.web.service.dispatch.emulation;

import com.softicar.platform.ajax.simple.SimpleServletRequest;
import com.softicar.platform.ajax.simple.SimpleServletResponse;
import com.softicar.platform.common.core.thread.collection.ThreadKiller;
import com.softicar.platform.common.core.threading.InterruptedRuntimeException;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.core.module.web.service.WebServiceBrokerService;
import com.softicar.platform.core.module.web.service.dispatch.IWebServiceDispatcher;
import java.lang.Thread.State;
import java.util.Objects;
import jakarta.servlet.http.HttpServletRequest;

/**
 * This dispatcher uses an internal server thread to emulate a real host.
 * <p>
 * This class is useful to implemented unit tests of web-service interactions.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class EmulatedWebServiceDispatcher implements IWebServiceDispatcher {

	private static final long DISPATCH_TIMEOUT = 10000;
	private final IEmulatedWebServiceDispatcherEnvironment dispatcherEnvironment;
	private final EmulatedWebServiceNetworkBuffer buffer;
	private final ServerThread serverThread;

	public EmulatedWebServiceDispatcher(IEmulatedWebServiceDispatcherEnvironment dispatcherEnvironment) {

		Objects.requireNonNull(dispatcherEnvironment);
		this.dispatcherEnvironment = dispatcherEnvironment;
		this.buffer = new EmulatedWebServiceNetworkBuffer();
		this.serverThread = new ServerThread();
	}

	public void killServerThread() {

		new ThreadKiller<>(serverThread).killAll();
	}

	@Override
	public SimpleServletResponse dispatch(SimpleServletRequest request) {

		// lazy start of server thread
		if (serverThread.getState() == State.NEW) {
			this.serverThread.start();
		}

		buffer.sendRequest(request);
		return buffer.readResponse(DISPATCH_TIMEOUT);
	}

	private class ServerThread extends Thread {

		@Override
		public void run() {

			try {
				dispatcherEnvironment.setupDispatcherEnvironment();
				try {
					WebServiceBrokerService compositeService = new WebServiceBrokerService();
					compositeService.setEnvironment(dispatcherEnvironment.createRequestEnvironment());
					compositeService.initialize();
					try {
						while (true) {
							HttpServletRequest request = buffer.readRequest();
							SimpleServletResponse response = new SimpleServletResponse();
							compositeService.service(request, response);
							buffer.sendResponse(response);
						}
					} finally {
						compositeService.destroy();
					}
				} finally {
					dispatcherEnvironment.cleanupDispatcherEnvironment();
				}
			} catch (InterruptedRuntimeException exception) {
				DevNull.swallow(exception);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}
