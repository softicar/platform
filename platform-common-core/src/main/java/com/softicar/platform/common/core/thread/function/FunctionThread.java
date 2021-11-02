package com.softicar.platform.common.core.thread.function;

import java.util.function.Function;

public class FunctionThread<T, R> extends Thread {

	private final Function<T, R> function;
	private final T input;
	private R result;
	private Exception exception;

	public FunctionThread(Function<T, R> function, T input) {

		this.function = function;
		this.input = input;
	}

	public Exception getException() {

		return exception;
	}

	public T getInput() {

		return input;
	}

	public R getResult() {

		return result;
	}

	@Override
	public void run() {

		try {
			this.result = function.apply(input);
		} catch (Exception exception) {
			this.exception = exception;
		}
	}
}
