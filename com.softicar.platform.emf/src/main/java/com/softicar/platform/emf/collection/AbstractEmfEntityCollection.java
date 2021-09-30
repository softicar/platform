package com.softicar.platform.emf.collection;

import com.softicar.platform.common.core.i18n.DisplayStringJoiningCollector;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;

public abstract class AbstractEmfEntityCollection<C extends AbstractEmfEntityCollection<C, E, EC>, E extends IEmfEntity<E, ?>, EC extends Collection<E>>
		extends AbstractDbObject<C>
		implements IEmfEntityCollection<C, E, EC> {

	protected EC elements;

	@Override
	public EC getElements() {

		if (elements == null) {
			this.elements = table().loadElements(getThis());
		}
		return getUnmodifiableConverter().apply(elements);
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getElements()//
			.stream()
			.map(IEmfTableRow::toDisplay)
			.collect(new DisplayStringJoiningCollector(IDisplayString.create(", ")));
	}
}
