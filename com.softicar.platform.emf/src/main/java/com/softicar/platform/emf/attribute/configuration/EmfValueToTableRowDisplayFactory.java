package com.softicar.platform.emf.attribute.configuration;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.display.IEmfAttributeTableRowDisplayFactory;
import com.softicar.platform.emf.attribute.display.IEmfAttributeValueDisplayFactory;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

class EmfValueToTableRowDisplayFactory<R extends IEmfTableRow<R, ?>, V> implements IEmfAttributeTableRowDisplayFactory<R> {

	private final IEmfAttribute<R, V> attribute;
	private final IEmfAttributeValueDisplayFactory<V> factory;

	public EmfValueToTableRowDisplayFactory(IEmfAttribute<R, V> attribute, IEmfAttributeValueDisplayFactory<V> factory) {

		this.attribute = attribute;
		this.factory = factory;
	}

	@Override
	public IDomElement createDisplay(R tableRow) {

		V value = attribute.getValue(tableRow);
		return factory.createDisplay(Optional.ofNullable(value));
	}
}
