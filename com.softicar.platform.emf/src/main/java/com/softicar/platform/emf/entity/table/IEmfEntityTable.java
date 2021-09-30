package com.softicar.platform.emf.entity.table;

import com.softicar.platform.db.runtime.entity.IDbEntityTable;
import com.softicar.platform.emf.entity.EmfEntityFactory;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfEntityTable<E extends IEmfEntity<E, P>, P, S> extends IEmfTable<E, P, S>, IDbEntityTable<E, P> {

	/**
	 * Returns the {@link IEmfEntity} with the given ID.
	 * <p>
	 * TODO we want to get rid of this method (see i54611)
	 *
	 * @param id
	 *            the ID of the {@link IEmfEntity} to return (may be null)
	 * @return the matching {@link IEmfEntity} or <i>null</i> if no matching
	 *         {@link IEmfEntity} exists or if the given ID was <i>null</i>
	 */
	E get(Integer id);

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
