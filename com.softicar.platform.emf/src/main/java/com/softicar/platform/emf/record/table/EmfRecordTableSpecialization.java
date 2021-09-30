package com.softicar.platform.emf.record.table;

import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.IEmfForeignRowAttributeFactory;
import com.softicar.platform.emf.record.EmfRecordCreationPopup;
import com.softicar.platform.emf.record.IEmfRecord;
import com.softicar.platform.emf.table.specialization.AbstractEmfTableSpecialization;

public class EmfRecordTableSpecialization<R extends IEmfRecord<R, P>, P, S> extends AbstractEmfTableSpecialization<R, P, S> {

	private final IEmfRecordTable<R, P, S> table;

	public EmfRecordTableSpecialization(IEmfRecordTable<R, P, S> table) {

		super(table);

		this.table = table;
	}

	@Override
	public R createImpermanentCopy(R record) {

		return table//
			.getRowFactory()
			.get()
			.initializer()
			.initializeFullCopy(record, DbTableRowFlag.IMPERMANENT);
	}

	@Override
	public DomPopup createNewTableRowPopup(S scope) {

		return new EmfRecordCreationPopup<>(table, scope);
	}

	@Override
	public IEmfForeignRowAttributeFactory<R, P> getDefaultAttributeFactory() {

		return EmfForeignRowAttribute::new;
	}
}
