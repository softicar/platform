package com.softicar.platform.emf.entity.table;

import com.softicar.platform.db.runtime.entity.IDbEntityTable;
import com.softicar.platform.emf.entity.EmfEntityFactory;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfEntityTable<E extends IEmfEntity<E, P>, P, S> extends IEmfTable<E, P, S>, IDbEntityTable<E, P> {

	/**
	 * Creates a new {@link IEmfTableRow} instance in the given scope.
	 * <p>
	 * For reference data, the given scope may be <i>null</i>.
	 *
	 * @param scope
	 *            the scope of the {@link IEmfEntity} (may be null)
	 * @return the newly created {@link IEmfEntity} (never null)
	 */
	default E createEntity(S scope) {

		return new EmfEntityFactory<>(this, scope).createEntity();
	}
}
