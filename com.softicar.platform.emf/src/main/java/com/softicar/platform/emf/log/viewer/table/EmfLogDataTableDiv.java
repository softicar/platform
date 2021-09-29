package com.softicar.platform.emf.log.viewer.table;

import com.softicar.platform.db.core.transaction.DbLazyTransaction;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.log.viewer.EmfLogMarker;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * The content element of the "Table" tab.
 * <p>
 * Uses a {@link DbLazyTransaction} with implicit rollback, to make sure that
 * misbehaving display factories cannot write persistent data.
 */
public class EmfLogDataTableDiv<R extends IEmfTableRow<R, ?>> extends DomDiv {

	public EmfLogDataTableDiv(R tableRow) {

		setMarker(EmfLogMarker.TABLE_MAIN);
		EmfLogDataTable<R> dataTable = new EmfLogDataTable<>(tableRow);
		EmfDataTableDivBuilder<EmfLogDataTableRow<R>> divBuilder = new EmfDataTableDivBuilder<>(dataTable);
		try (DbLazyTransaction lazyTransaction = new DbLazyTransaction()) {
			appendChild(dataTable.applyCustomization(divBuilder).build());
		}
	}
}
