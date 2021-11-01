package com.softicar.platform.emf.entity.table;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.attribute.field.foreign.entity.EmfForeignEntityAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.IEmfForeignRowAttributeFactory;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.table.specialization.AbstractEmfTableSpecialization;

public class EmfEntityTableSpecialization<R extends IEmfEntity<R, P>, P, S> extends AbstractEmfTableSpecialization<R, P, S> {

	private final IEmfEntityTable<R, P, S> table;

	public EmfEntityTableSpecialization(IEmfEntityTable<R, P, S> table) {

		super(table);

		this.table = table;
	}

	@Override
	public R createImpermanentCopy(R tableRow) {

		return tableRow.copy();
	}

	@Override
	public DomPopup createNewTableRowPopup(S scope) {

		R tableRow = table.createEntity(scope);
		return new EmfFormPopup<>(tableRow);
	}

	@Override
	public IEmfForeignRowAttributeFactory<R, P> getDefaultAttributeFactory() {

		return EmfForeignEntityAttribute::new;
	}
}
