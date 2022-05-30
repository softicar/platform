package com.softicar.platform.emf.entity.set;

import com.softicar.platform.common.core.i18n.DisplayStringJoiningCollector;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collections;
import java.util.Set;

public abstract class AbstractEmfEntitySet<ES extends AbstractEmfEntitySet<ES, E>, E extends IEmfEntity<E, ?>> extends AbstractDbObject<ES>
		implements IEmfEntitySet<ES, E> {

	protected Set<E> elements;

	public void setElements(Set<E> elements) {

		this.elements = elements;
	}

	@Override
	public Set<E> getElements() {

		if (elements == null) {
			this.elements = table().loadElements(getThis());
		}
		return Collections.unmodifiableSet(elements);
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getElements()//
			.stream()
			.map(IEmfTableRow::toDisplay)
			.collect(new DisplayStringJoiningCollector(IDisplayString.create(", ")));
	}
}
