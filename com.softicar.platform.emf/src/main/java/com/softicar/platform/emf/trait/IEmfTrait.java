package com.softicar.platform.emf.trait;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.record.IEmfRecord;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.trait.table.IEmfTraitTable;

/**
 * An {@link IEmfTrait} extends an {@link IEmfTableRow} with new attributes.
 *
 * @author Oliver Richers
 */
public interface IEmfTrait<T extends IEmfTrait<T, R>, R extends IEmfTableRow<R, ?>> extends IEmfRecord<T, R> {

	@Override
	IEmfTraitTable<T, R> table();

	@Override
	default IDisplayString toDisplay() {

		return EmfI18n.ARG1_OF_ARG2.toDisplay(table().getTitle(), pk().toDisplay());
	}
}
