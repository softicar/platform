package com.softicar.platform.common.core.singleton;

import com.softicar.platform.common.core.interfaces.Consumers;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Represents a thread local variable of a singleton.
 * <p>
 * This class should only be used to declare private static final fields.
 *
 * @author Oliver Richers
 * @param <T>
 *            the type of the singleton instance
 */
public class Singleton<T> {

	private Supplier<T> initialValueSupplier;
	private UnaryOperator<T> inheritOperator;
	private Consumer<T> scopeCloseHandler;

	/**
	 * Constructs this {@link Singleton} with an initial value of <i>null</i>.
	 */
	public Singleton() {

		this(() -> null);
	}

	/**
	 * Constructs this {@link Singleton} with the given initial value supplier.
	 *
	 * @param initialValueSupplier
	 *            the initial value supplier (never <i>null</i>)
	 */
	public Singleton(Supplier<T> initialValueSupplier) {

		this.initialValueSupplier = Objects.requireNonNull(initialValueSupplier);
		this.inheritOperator = parentValue -> initialValueSupplier.get();
		this.scopeCloseHandler = Consumers.noOperation();
	}

	/**
	 * Defines the supplier for the initial value of this {@link Singleton}.
	 * <p>
	 * The default implementation returns <i>null</i>.
	 *
	 * @param initialValueSupplier
	 *            the initial value supplier (never <i>null</i>)
	 * @return this object
	 */
	public Singleton<T> setInitialValueSupplier(Supplier<T> initialValueSupplier) {

		this.initialValueSupplier = Objects.requireNonNull(initialValueSupplier);
		return this;
	}

	/**
	 * Defines an operator, how to transform the value of this {@link Singleton}
	 * from the parent {@link SingletonSet} into the value in the child
	 * {@link SingletonSet}.
	 * <p>
	 * The default implementation drops the parent {@link SingletonSet} value
	 * and uses the value given by the initial value supplier.
	 * <p>
	 * Usually, this mechanism is used when inheriting the {@link SingletonSet}
	 * from a parent {@link Thread}. Be careful when inheriting non-primitive
	 * {@link Singleton} values: Those must be either immutable, deeply copied,
	 * or thread safe (regarding both read and write access). Otherwise,
	 * race-conditions may occur.
	 *
	 * @param inheritOperator
	 *            the inheriting operator
	 * @return this object
	 */
	public Singleton<T> setInheritOperator(UnaryOperator<T> inheritOperator) {

		this.inheritOperator = Objects.requireNonNull(inheritOperator);
		return this;
	}

	/**
	 * Calls {@link #setInheritOperator} with {@link UnaryOperator#identity()}.
	 *
	 * @return this object
	 */
	public Singleton<T> setInheritByIdentity() {

		return setInheritOperator(UnaryOperator.identity());
	}

	/**
	 * Defines a handler which is called when a {@link SingletonSetScope} is
	 * closed.
	 * <p>
	 * The given handler is called when {@link SingletonSetScope#close()} is
	 * called, before {@link CurrentSingletonSet} is changed.
	 * <p>
	 * The handler is only called if the current {@link Singleton} value is not
	 * <i>null</i>. The current value is provided to the handler as
	 * {@link Consumer} argument, and thus, the value is never <i>null</i>.
	 * <p>
	 * This handler provides the opportunity to the {@link Singleton} to clean
	 * up resources. Be aware that closing a {@link SingletonSetScope} does not
	 * necessarily imply that the current singleton value will never be used
	 * again. For example, short-lived resources like database connections might
	 * be closed by such a handler.
	 *
	 * @param scopeCloseHandler
	 *            the handler (never <i>null</i>)
	 * @return this object
	 */
	public Singleton<T> setScopeCloseHandler(Consumer<T> scopeCloseHandler) {

		this.scopeCloseHandler = Objects.requireNonNull(scopeCloseHandler);
		return this;
	}

	/**
	 * Returns the current value of this {@link Singleton}.
	 *
	 * @return the current value (may be <i>null</i>)
	 */
	public T get() {

		return CurrentSingletonSet.get().getSingletonValue(this);
	}

	/**
	 * Sets the current value of this {@link Singleton}.
	 *
	 * @param value
	 *            the new value (may be <i>null</i>)
	 */
	public void set(T value) {

		CurrentSingletonSet.get().setSingletonValue(this, value);
	}

	/**
	 * Resets the current value of this {@link Singleton} to its initial value.
	 * <p>
	 * Resetting this {@link Singleton} will <b>not</b> reset its value to the
	 * value inherited from a parent {@link SingletonSet}. In any case, the
	 * value of the initial value supplier will be used.
	 */
	public void reset() {

		CurrentSingletonSet.get().resetSingletonValue(this);
	}

	// ------------------------------ package private ------------------------------ //

	T getInitalValue() {

		return initialValueSupplier.get();
	}

	T getInheritedValue(T parentValue) {

		return inheritOperator.apply(parentValue);
	}

	void callScopeCloseHandler(T value) {

		scopeCloseHandler.accept(value);
	}
}
