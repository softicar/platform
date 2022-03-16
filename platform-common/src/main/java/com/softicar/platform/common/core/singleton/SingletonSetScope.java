package com.softicar.platform.common.core.singleton;

import java.util.ArrayList;

/**
 * This class can be used to control {@link CurrentSingletonSet}.
 * <p>
 * This class should only be used in a try block to ensure proper restoration of
 * the original value of {@link CurrentSingletonSet}.
 *
 * @author Oliver Richers
 */
public class SingletonSetScope implements AutoCloseable {

	private final SingletonSet singletonSet;
	private final SingletonSet originalSet;

	/**
	 * Activates a new {@link SingletonSet} which inherits its values from the
	 * {@link SingletonSet} returned by {@link CurrentSingletonSet#get}.
	 */
	public SingletonSetScope() {

		this(new SingletonSet(CurrentSingletonSet.get()));
	}

	/**
	 * Activates the given {@link SingletonSet}.
	 *
	 * @param singletonSet
	 *            the {@link SingletonSet} to activate (never null)
	 */
	public SingletonSetScope(SingletonSet singletonSet) {

		this.singletonSet = singletonSet;
		this.originalSet = CurrentSingletonSet.get();

		CurrentSingletonSet.set(singletonSet);
	}

	/**
	 * Restores the originally active {@link SingletonSet}.
	 */
	@Override
	public void close() {

		// copying singletons to prevent potential concurrent modification
		new ArrayList<>(singletonSet.getSingletons()).forEach(this::callCloseHandler);

		CurrentSingletonSet.set(originalSet);
	}

	private <T> void callCloseHandler(Singleton<T> singleton) {

		T value = singletonSet.getSingletonValue(singleton);
		if (value != null) {
			singleton.callScopeCloseHandler(value);
		}
	}
}
