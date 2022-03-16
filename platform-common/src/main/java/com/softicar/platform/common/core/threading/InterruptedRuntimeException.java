package com.softicar.platform.common.core.threading;

public class InterruptedRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 7149080503589082907L;

	public InterruptedRuntimeException(InterruptedException exception) {

		super(exception);
	}

	public InterruptedRuntimeException(InterruptedException interruptedException, String message) {

		super(message, interruptedException);
	}

	public InterruptedRuntimeException(InterruptedException interruptedException, String message, Object...args) {

		super(String.format(message, args), interruptedException);
	}
}
