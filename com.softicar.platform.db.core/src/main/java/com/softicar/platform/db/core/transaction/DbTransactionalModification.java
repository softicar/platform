package com.softicar.platform.db.core.transaction;

import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.db.core.table.IDbCoreTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Auxiliary class to safely modify an {@link IDbCoreTableRow}.
 *
 * @author Oliver Richers
 */
public class DbTransactionalModification<T extends IDbCoreTableRow> {

	private final Supplier<T> rowSupplier;
	private final Collection<Predicate<T>> preconditions;

	private DbTransactionalModification(Supplier<T> rowSupplier) {

		this.rowSupplier = Objects.requireNonNull(rowSupplier);
		this.preconditions = new ArrayList<>();
	}

	/**
	 * Creates a new instance for the given {@link IDbCoreTableRow}.
	 *
	 * @param <T>
	 *            the type of {@link IDbCoreTableRow}
	 * @param tableRow
	 *            the {@link IDbCoreTableRow} to modify (never <i>null</i>)
	 * @return the new instance of {@link DbTransactionalModification} (never
	 *         <i>null</i>)
	 */
	public static <T extends IDbCoreTableRow> DbTransactionalModification<T> of(T tableRow) {

		return new DbTransactionalModification<>(() -> tableRow);
	}

	/**
	 * Creates a new instance with the given {@link Supplier}.
	 *
	 * @param <T>
	 *            the type of {@link IDbCoreTableRow}
	 * @param rowSupplier
	 *            the {@link Supplier} for the {@link IDbCoreTableRow} to modify
	 *            (never <i>null</i>)
	 * @return the new instance of {@link DbTransactionalModification} (never
	 *         <i>null</i>)
	 */
	public static <T extends IDbCoreTableRow> DbTransactionalModification<T> with(Supplier<T> rowSupplier) {

		return new DbTransactionalModification<>(rowSupplier);
	}

	/**
	 * Adds the given predicate as precondition to this
	 * {@link DbTransactionalModification}.
	 *
	 * @param precondition
	 *            the predicate to add (never <i>null</i>)
	 * @return this
	 */
	public DbTransactionalModification<T> when(Predicate<T> precondition) {

		Objects.requireNonNull(precondition);
		preconditions.add(precondition);
		return this;
	}

	/**
	 * Adds the given predicate as precondition to this
	 * {@link DbTransactionalModification}.
	 *
	 * @param precondition
	 *            the predicate to add (never <i>null</i>)
	 * @return this
	 */
	public DbTransactionalModification<T> whenNot(Predicate<T> precondition) {

		Objects.requireNonNull(precondition);
		return when(row -> !precondition.test(row));
	}

	/**
	 * Executes the given {@link Consumer} on the {@link IDbCoreTableRow}.
	 * <p>
	 * This converts the {@link Consumer} into a {@link Function} using
	 * {@link Consumers#toFunction} and calls {@link #apply(Function)}.
	 *
	 * @param consumer
	 *            the {@link Consumer} to execute (never <i>null</i>)
	 * @return <i>true</i> if all precondition were met and thus the
	 *         {@link Consumer} was executed; <i>false</i> otherwise
	 */
	public boolean accept(Consumer<T> consumer) {

		return apply(Consumers.toFunction(consumer, true)).orElse(false);
	}

	/**
	 * Executes the given {@link Function} on the {@link IDbCoreTableRow}.
	 * <p>
	 * <ul>
	 * <li>This opens a new {@link DbTransaction}, grabs the
	 * {@link IDbCoreTableRow}, calls {@link IDbCoreTableRow#reloadForUpdate()}
	 * and tests the preconditions.</li>
	 * <li>If all preconditions hold true, the {@link Function} is executed and
	 * the {@link DbTransaction} is committed.</li>
	 * <li>If one or more preconditions fail, the {@link Function} is <b>not</b>
	 * executed and the {@link DbTransaction} is rolled back.</li>
	 * <li>The preconditions are evaluated in a fail-fast manner.
	 * </ul>
	 *
	 * @param <R>
	 *            the type of the return value of the {@link Function}
	 * @param function
	 *            the {@link Function} to execute (never <i>null</i>)
	 * @return the {@link Optional} return value of the executed
	 *         {@link Function}; the {@link Optional} is empty if the
	 *         {@link Function} was not executed or if the {@link Function}
	 *         returned <i>null</i>
	 */
	public <R> Optional<R> apply(Function<T, R> function) {

		try (DbTransaction transaction = new DbTransaction()) {
			T row = rowSupplier.get();
			if (row != null && reloadRowAndTestPreconditions(row)) {
				R result = function.apply(row);
				transaction.commit();
				return Optional.ofNullable(result);
			} else {
				return Optional.empty();
			}
		}
	}

	private boolean reloadRowAndTestPreconditions(T row) {

		return row.reloadForUpdate() && testPreconditions(row);
	}

	private boolean testPreconditions(T row) {

		return preconditions.stream().allMatch(it -> it.test(row));
	}
}
