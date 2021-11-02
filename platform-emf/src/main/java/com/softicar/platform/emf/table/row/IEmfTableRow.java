package com.softicar.platform.emf.table.row;

import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.emf.concurrency.EmfConcurrencyController;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;
import java.util.Collection;

public interface IEmfTableRow<R extends IEmfTableRow<R, P>, P> extends IDbTableRow<R, P>, IDisplayable {

	@Override
	IEmfTable<R, P, ?> table();

	default Collection<IEmfTableRow<?, ?>> getAttributeOwners() {

		return table().getAttributeOwners(getThis());
	}

	default void validate(IEmfValidationResult result) {

		table().validateTableRow(getThis(), result);
	}

	/**
	 * Checks whether all attributes of this {@link IEmfTableRow} are fresh or
	 * stale.
	 * <p>
	 * An attribute is considered to be fresh if there was no concurrent
	 * modification of its value.
	 * <p>
	 * To determine the freshness of all attributes, the freshness of all
	 * attribute owners will be determined. Only attribute owners that have a
	 * transaction field will be checked. The transaction field value of the
	 * in-memory representation of the attribute owner is compared to its
	 * persistent value. If they are equal, the owner and thus its attributes
	 * are fresh.
	 * <p>
	 * If one or more attribute owners are stale, this {@link IEmfTableRow} is
	 * considered to be stale.
	 *
	 * @return <i>true</i> if all attribute owners are fresh; <i>false</i>
	 *         otherwise
	 */
	default boolean isFresh() {

		return new EmfConcurrencyController<>(getThis()).peekForFreshnessOfAttributeOwners();
	}
}
