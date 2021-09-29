package com.softicar.platform.emf.table.specialization;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.attribute.initialization.EmfScopeAttributeInitializer;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.EmfTableRowDisplay;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

public abstract class AbstractEmfTableSpecialization<R extends IEmfTableRow<R, P>, P, S> implements IEmfTableSpecialization<R, P, S> {

	private final IEmfTable<R, P, S> table;

	public AbstractEmfTableSpecialization(IEmfTable<R, P, S> table) {

		this.table = table;
	}

	@Override
	public void initializeAttributeList(IEmfAttributeList<R> attributeList) {

		new EmfScopeAttributeInitializer<>(table).initialize(attributeList);
	}

	@Override
	public void initializeFields(R row, S scope) {

		table.getScopeField().ifPresent(scopeField -> scopeField.setValue(row, scope));
		table.getAttributeDefaultValueSet().applyTo(row, scope);
	}

	@Override
	public IDomElement createDefaultDisplay(R row) {

		return new EmfTableRowDisplay<>(row);
	}

	@Override
	public Optional<IEmfTableRow<?, ?>> getBase(R row) {

		return Optional.empty();
	}

	@Override
	public Optional<IEmfTable<?, ?, ?>> getBaseTable() {

		return Optional.empty();
	}
}
