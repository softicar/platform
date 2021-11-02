package com.softicar.platform.emf.attribute.initialization;

import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * Initializes the scope field of an {@link IEmfTable}.
 *
 * @author Oliver Richers
 */
public class EmfScopeAttributeInitializer<R extends IEmfTableRow<R, ?>> {

	private final IEmfTable<R, ?, ?> table;

	public EmfScopeAttributeInitializer(IEmfTable<R, ?, ?> table) {

		this.table = table;
	}

	public void initialize(IEmfAttributeList<R> attributeList) {

		table//
			.getScopeField()
			.ifPresent(
				scopeField -> attributeList//
					.editAttribute(scopeField)
					.setEditable(false)
					.setImmutable(true));
	}
}
