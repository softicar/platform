package com.softicar.platform.emf.attribute.initialization;

import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.sub.object.IEmfSubObject;
import com.softicar.platform.emf.sub.object.table.IEmfSubObjectTable;

/**
 * Initializes the base field of an {@link IEmfSubObjectTable}.
 *
 * @author Oliver Richers
 */
public class EmfBaseAttributeInitializer<E extends IEmfSubObject<E, ?>> {

	private final IEmfSubObjectTable<E, ?, ?, ?> table;

	public EmfBaseAttributeInitializer(IEmfSubObjectTable<E, ?, ?, ?> table) {

		this.table = table;
	}

	public void initialize(IEmfAttributeList<E> attributeList) {

		attributeList//
			.editAttribute(table.getBaseField())
			.setConcealed(true)
			.setEditable(false)
			.setImmutable(true);
	}
}
