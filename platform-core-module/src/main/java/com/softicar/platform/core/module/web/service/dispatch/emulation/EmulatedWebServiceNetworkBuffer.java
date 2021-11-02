package com.softicar.platform.core.module.web.service.dispatch.emulation;

import com.softicar.platform.ajax.simple.SimpleServletResponse;
import com.softicar.platform.common.core.threading.InterruptedRuntimeException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

/**
 * A buffer that can be used to emulate web service related client-server
 * network communication.
 * <p>
 * Utilizes two independent {@link SynchronousQueue} instances for request and
 * response communication.
 * 
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
class EmulatedWebServiceNetworkBuffer {

	private final BlockingQueue<HttpServletRequest> requestQueue;
	private final BlockingQueue<SimpleServletResponse> responseQueue;

	public EmulatedWebServiceNetworkBuffer() {

		this.requestQueue = new SynchronousQueue<>();
		this.responseQueue = new SynchronousQueue<>();
	}

	public void sendRequest(HttpServletRequest request) {

		try {
			requestQueue.put(request);
		} catch (InterruptedException exception) {
			throw new InterruptedRuntimeException(exception);
		}
	}

	public HttpServletRequest readRequest() {

		try {
			return requestQueue.take();
		} catch (InterruptedException exception) {
			throw new InterruptedRuntimeException(exception);
		}
	}

	public void sendResponse(SimpleServletResponse response) {

		try {
			responseQueue.put(response);
		} catch (InterruptedException exception) {
			throw new InterruptedRuntimeException(exception);
		}
	}

	public SimpleServletResponse readResponse(long timeoutMillis) {

		try {
			return responseQueue.poll(timeoutMillis, TimeUnit.MILLISECONDS);
		} catch (InterruptedException exception) {
			throw new InterruptedRuntimeException(exception);
		}
	}
}
