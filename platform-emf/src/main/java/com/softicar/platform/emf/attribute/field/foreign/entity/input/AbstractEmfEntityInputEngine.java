package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteDefaultInputEngine;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.Collection;

/**
 * An input engine for {@link IEmfEntity} instances.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public abstract class AbstractEmfEntityInputEngine<E extends IEmfEntity<E, ?>> extends DomAutoCompleteDefaultInputEngine<E> {

	public AbstractEmfEntityInputEngine(IDbTable<E, ?> table) {

		setLoader(() -> {
			var values = loadItems();
			prefetchData(values);
			return values;
		});
		addDependsOn(table);
	}

	/**
	 * Loads all values that match the current filter settings.
	 *
	 * @return all values that match the current filter settings (never
	 *         <i>null</i>)
	 */
	protected abstract Collection<E> loadItems();

	protected void prefetchData(Collection<E> values) {

		// nothing to do by default
		DevNull.swallow(values);
	}
}
