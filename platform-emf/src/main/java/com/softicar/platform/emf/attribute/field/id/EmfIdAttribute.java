package com.softicar.platform.emf.attribute.field.id;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.attribute.field.integer.EmfIntegerDisplay;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfIdAttribute<R extends IEmfTableRow<R, ?>> extends EmfFieldAttribute<R, Integer> {

	public EmfIdAttribute(IDbField<R, Integer> field) {

		super(field);

		setDisplayFactory(EmfIntegerDisplay::new);
		setPredicateVisible(EmfPredicates.persistent());
	}

	@Override
	public final boolean isEditable() {

		return false;
	}

	@Override
	public boolean isImmutable() {

		return true;
	}
}
