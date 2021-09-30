package com.softicar.platform.emf.attribute.field.item;

import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.attribute.field.string.EmfStringDisplay;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

public class EmfBasicItemAttribute<R extends IEmfTableRow<R, ?>, V extends IBasicItem> extends EmfFieldAttribute<R, V> {

	public EmfBasicItemAttribute(IDbField<R, V> field) {

		super(field);

		setDisplayFactory(this::createIdDisplay);
	}

	private EmfStringDisplay createIdDisplay(V value) {

		return new EmfStringDisplay(getIdValueString(value));
	}

	private String getIdValueString(V value) {

		return Optional//
			.ofNullable(value)
			.map(it -> it.getItemId())
			.map(it -> it.toString())
			.orElse("");
	}
}
