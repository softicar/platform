package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.db.runtime.enums.IDbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.attribute.input.auto.complete.EmfAutoCompleteBrowseButton;

public class EmfEnumTableRowInput<R extends IDbEnumTableRow<R, E>, E extends IDbEnumTableRowEnum<E, R>> extends DomAutoCompleteInput<R>
		implements IEmfInput<R> {

	public EmfEnumTableRowInput(IDbEnumTable<R, ?> targetTable) {

		super(new EmfEnumTableRowInputEngine<>(targetTable));

		getInputBar().appendChild(new EmfAutoCompleteBrowseButton(this, getInputEngine()));
	}
}
