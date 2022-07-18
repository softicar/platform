package com.softicar.platform.emf.attribute.field.foreign.row;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfForeignRowAttribute<R extends IEmfTableRow<R, ?>, F extends IEmfTableRow<F, ?>> extends EmfFieldAttribute<R, F>
		implements IEmfForeignRowAttribute<R, F> {

	private final IDbForeignRowField<R, F, ?> entityField;

	public EmfForeignRowAttribute(IDbForeignRowField<R, F, ?> field) {

		super(field);

		this.entityField = field;

		setDisplayFactory(this::createForeignDisplay);
	}

	@Override
	public IEmfTable<F, ?, ?> getTargetTable() {

		// FIXME this cast is ugly
		return CastUtils.cast(entityField.getTargetTable());
	}

	@Override
	public IDbForeignRowField<R, F, ?> getForeignRowField() {

		return entityField;
	}

	private IDomElement createForeignDisplay(F value) {

		return getTargetTable().getDisplayFactory().apply(value);
	}
}
