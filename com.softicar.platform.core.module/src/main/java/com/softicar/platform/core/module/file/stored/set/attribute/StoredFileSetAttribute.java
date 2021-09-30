package com.softicar.platform.core.module.file.stored.set.attribute;

import com.softicar.platform.core.module.file.stored.set.AGStoredFileSet;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.emf.attribute.field.foreign.entity.EmfForeignEntityAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class StoredFileSetAttribute<R extends IEmfTableRow<R, ?>> extends EmfForeignEntityAttribute<R, AGStoredFileSet> {

	public StoredFileSetAttribute(IDbForeignRowField<R, AGStoredFileSet, Integer> field) {

		super(field);
		setDisplayFactory(StoredFileSetDisplay::new);
		setInputFactory(StoredFileSetInput::new);
	}
}
