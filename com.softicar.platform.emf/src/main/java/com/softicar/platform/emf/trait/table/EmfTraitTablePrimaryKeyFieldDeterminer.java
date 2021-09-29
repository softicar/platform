package com.softicar.platform.emf.trait.table;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.trait.IEmfTrait;
import java.util.List;

public class EmfTraitTablePrimaryKeyFieldDeterminer<T extends IEmfTrait<T, R>, R extends IEmfTableRow<R, ?>> {

	private final EmfTraitTable<T, R> traitTable;

	public EmfTraitTablePrimaryKeyFieldDeterminer(EmfTraitTable<T, R> traitTable) {

		this.traitTable = traitTable;
	}

	public IDbForeignRowField<T, R, ?> determine() {

		List<IDbField<T, ?>> fields = traitTable.getPrimaryKey().getFields();
		if (fields.size() == 1) {
			return CastUtils.cast(fields.get(0));
		} else {
			throw new IllegalStateException(String.format("The primary key of table '%s' must consist of a single field.", getClass().getSimpleName()));
		}
	}
}
