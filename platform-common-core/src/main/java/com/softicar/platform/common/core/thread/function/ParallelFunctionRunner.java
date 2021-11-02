package com.softicar.platform.common.core.thread.function;

import com.softicar.platform.common.core.thread.collection.ThreadCollection;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ParallelFunctionRunner<T, R> {

	private final Function<T, R> function;
	private final Map<Integer, T> inputValues;
	private final Map<Integer, Exception> exceptions;
	private BiConsumer<T, R> resultConsumer;
	private BiConsumer<T, Exception> exceptionConsumer;
	private long millisPerTry;
	private long tryCount;

	public ParallelFunctionRunner(Function<T, R> function) {

		this.function = function;
		this.inputValues = new TreeMap<>();
		this.exceptions = new TreeMap<>();
	}

	public ParallelFunctionRunner<T, R> addInputValue(T values) {

		this.inputValues.put(inputValues.size(), values);
		return this;
	}

	public ParallelFunctionRunner<T, R> addInputValues(Collection<? extends T> values) {

		values.stream().forEach(this::addInputValue);
		return this;
	}

	public ParallelFunctionRunner<T, R> setResultConsumer(BiConsumer<T, R> consumer) {

		this.resultConsumer = consumer;
		return this;
	}

	public ParallelFunctionRunner<T, R> setExceptionConsumer(BiConsumer<T, Exception> exceptionConsumer) {

		this.exceptionConsumer = exceptionConsumer;
		return this;
	}

	public ParallelFunctionRunner<T, R> setMillisPerTry(long millisPerTry) {

		this.millisPerTry = millisPerTry;
		return this;
	}

	public ParallelFunctionRunner<T, R> setTryCount(long tryCount) {

		this.tryCount = tryCount;
		return this;
	}

	public void apply() {

		if (resultConsumer == null) {
			throw new IllegalStateException("No result consumer defined.");
		}

		for (int i = 0; i < tryCount && !inputValues.isEmpty(); i++) {
			// create function threads
			Map<Integer, FunctionThread<T, R>> threads = new TreeMap<>();
			for (Entry<Integer, T> entry: inputValues.entrySet()) {
				threads.put(entry.getKey(), new FunctionThread<>(function, entry.getValue()));
			}

			// run function threads
			new ThreadCollection<>(threads.values()).runAll(millisPerTry);

			// get result values from function threads
			for (Entry<Integer, FunctionThread<T, R>> entry: threads.entrySet()) {
				Integer index = entry.getKey();
				FunctionThread<T, R> thread = entry.getValue();
				if (thread.getException() == null) {
					resultConsumer.accept(thread.getInput(), thread.getResult());
					inputValues.remove(index);
					exceptions.remove(index);
				} else {
					exceptions.put(index, thread.getException());
				}
			}
		}

		// provide exceptions if wanted
		if (exceptionConsumer != null) {
			for (Entry<Integer, Exception> entry: exceptions.entrySet()) {
				T inputValue = inputValues.get(entry.getKey());
				exceptionConsumer.accept(inputValue, entry.getValue());
			}
		}
	}

	public Exception getException(T key) {

		return exceptions.get(key);
	}
}
