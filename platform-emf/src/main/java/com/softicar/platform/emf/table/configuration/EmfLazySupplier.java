package com.softicar.platform.emf.table.configuration;

import com.softicar.platform.common.core.thread.utils.ThreadSafeLazySupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This is a thread-safe lazy supplier.
 * <p>
 * Object creation is delegated to a factory {@link Function} and a
 * {@link Consumer} for customization. This class uses
 * {@link ThreadSafeLazySupplier} internally.
 *
 * @author Oliver Richers
 */
public class EmfLazySupplier<T> implements Supplier<T> {

	private final Supplier<T> factory;
	private final Consumer<T> customizer;
	private final ThreadSafeLazySupplier<T> threadSafeSupplier;

	/**
	 * Constructs this supplier with the given factory and customer.
	 * <p>
	 * The first parameter to supplied to the factory as it is.
	 *
	 * @param parameter
	 *            the parameter which will be passed to the factory (may be null
	 *            if the factory allows)
	 * @param factory
	 *            the factory to create the object (never null)
	 * @param customizer
	 *            the customizer to execute after object creation (never null)
	 */
	public <S> EmfLazySupplier(S parameter, Function<S, T> factory, Consumer<T> customizer) {

		this(() -> factory.apply(parameter), customizer);
	}

	/**
	 * Constructs this supplier with the given factory and customer.
	 *
	 * @param factory
	 *            the factory to create the object (never null)
	 * @param customizer
	 *            the customizer to execute after object creation (never null)
	 */
	public EmfLazySupplier(Supplier<T> factory, Consumer<T> customizer) {

		this.factory = factory;
		this.customizer = customizer;
		this.threadSafeSupplier = new ThreadSafeLazySupplier<>(this::createAndCustomize);
	}

	@Override
	public T get() {

		return threadSafeSupplier.get();
	}

	private T createAndCustomize() {

		T object = factory.get();
		customizer.accept(object);
		return object;
	}
}
