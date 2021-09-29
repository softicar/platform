package com.softicar.platform.common.core.optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Evaluates a list of {@link Optional} values.
 *
 * @author Oliver Richers
 */
public class OptionalListEvaluator<T> {

	private final T defaultValue;
	private final List<T> values;

	/**
	 * Initializes this evaluator with a default value of null.
	 */
	public OptionalListEvaluator() {

		this(null);
	}

	/**
	 * Initializes this evaluator with the default value.
	 *
	 * @param defaultValue
	 *            the default value to return (may be null)
	 */
	public OptionalListEvaluator(T defaultValue) {

		this.defaultValue = defaultValue;
		this.values = new ArrayList<>();
	}

	/**
	 * Adds the given {@link Optional} value to this list.
	 *
	 * @param optionalValue
	 *            the value to add (never null)
	 * @return this object
	 */
	public OptionalListEvaluator<T> add(Optional<T> optionalValue) {

		optionalValue.ifPresent(values::add);
		return this;
	}

	/**
	 * Adds the given nullable value to this list.
	 *
	 * @param nullableValue
	 *            the value to add (may be null)
	 * @return this object
	 */
	public OptionalListEvaluator<T> addNullable(T nullableValue) {

		return add(Optional.ofNullable(nullableValue));
	}

	/**
	 * Returns the first non-empty {@link Optional}.
	 *
	 * @return the first non-empty {@link Optional} (never null)
	 */
	public Optional<T> getFirst() {

		return values.stream().findFirst();
	}

	/**
	 * Returns the value of the first non-empty {@link Optional} or else the
	 * default value.
	 *
	 * @return the value of the first non-empty {@link Optional} or the default
	 *         value (may be null)
	 */
	public T getFirstOrDefault() {

		return getFirst().orElse(defaultValue);
	}
}
