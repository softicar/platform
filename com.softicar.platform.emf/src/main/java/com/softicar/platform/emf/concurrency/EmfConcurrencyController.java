package com.softicar.platform.emf.concurrency;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.core.transaction.DbOptionalLazyTransaction;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.emf.attribute.field.transaction.EmfTransactionAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;
import java.util.Objects;
import java.util.Optional;

/**
 * Implements freshness peeking and reloading for {@link IEmfTableRow}.
 *
 * @author Alexander Schmidt
 */
public class EmfConcurrencyController<R extends IEmfTableRow<R, P>, P> {

	private final R tableRow;

	public EmfConcurrencyController(R tableRow) {

		this.tableRow = tableRow;
	}

	/**
	 * Peeks the database for modifications to the given entity, without causing
	 * a side effect on the local cache.
	 * <p>
	 * If the entity table possesses an {@link IEmfTransactionObject} attribute,
	 * its values in the local cache and in the database are compared.
	 *
	 * @return <i>true</i> if the {@link IEmfTransactionObject} of the entity in
	 *         the database is still the same as the
	 *         {@link IEmfTransactionObject} of the entity in the local cache,
	 *         or if the entity table possesses no {@link IEmfTransactionObject}
	 *         attribute. <i>false</i> otherwise.
	 */
	public boolean peekForFreshness() {

		if (tableRow.impermanent()) {
			// impermanent entities are always fresh
			return true;
		} else {
			try (DbOptionalLazyTransaction transaction = new DbOptionalLazyTransaction()) {
				Optional<EmfTransactionAttribute<R, ?>> attribute = getTransactionAttribute(tableRow);
				if (attribute.isPresent()) {
					IEmfTransactionObject<?> oldTransaction = attribute.get().getValue(tableRow);
					IEmfTransactionObject<?> newTransaction = loadTransactionOrNull(tableRow, attribute.get());
					return Objects.equals(oldTransaction, newTransaction);
				}
				transaction.commit();
			}
			return true;
		}
	}

	/**
	 * Performs {@link #peekForFreshness()} on all attribute owners of the given
	 * entity.
	 *
	 * @return <i>true</i> if {@link #peekForFreshness()} is true for all
	 *         attribute owners, or if the entity has no attribute owners.
	 *         <i>false</i> if {@link #peekForFreshness()} is false for at least
	 *         attribute owner.
	 */
	public boolean peekForFreshnessOfAttributeOwners() {

		return tableRow//
			.getAttributeOwners()
			.stream()
			.allMatch(this::peekForFreshness);
	}

	// ------------------------------ private ------------------------------ //

	private boolean peekForFreshness(IEmfTableRow<?, ?> entity) {

		// TODO Thanks to a defect in the Java 8 compiler we need to use CastUtils here.
		// Without this defect, we could just write `entity.getThis()`.
		return new EmfConcurrencyController<>(CastUtils.cast(entity)).peekForFreshness();
	}

	private Optional<EmfTransactionAttribute<R, ?>> getTransactionAttribute(R tableRow) {

		return tableRow.table().getTransactionAttribute();
	}

	private IEmfTransactionObject<?> loadTransactionOrNull(R tableRow, EmfTransactionAttribute<R, ?> attribute) {

		return Sql//
			.from(tableRow.table())
			.select(attribute.getField().get())
			.where(tableRow.table().getPrimaryKey().isEqual(tableRow.pk()))
			.getFirst();
	}
}
