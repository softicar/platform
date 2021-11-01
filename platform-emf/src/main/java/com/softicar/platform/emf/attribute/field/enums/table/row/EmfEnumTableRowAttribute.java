package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.db.runtime.enums.IDbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfEnumTableRowAttribute<R extends IEmfTableRow<R, ?>, E extends IDbEnumTableRow<E, V>, V extends IDbEnumTableRowEnum<V, E>>
		extends EmfFieldAttribute<R, E> {

	@SuppressWarnings("unchecked")
	public EmfEnumTableRowAttribute(IDbForeignField<R, E> field) {

		super(field);

		setDisplayFactory(EmfEnumTableRowDisplay::new);
		setInputFactory(() -> new EmfEnumTableRowInput<>((IDbEnumTable<E, V>) field.getTargetTable()));
	}
}
