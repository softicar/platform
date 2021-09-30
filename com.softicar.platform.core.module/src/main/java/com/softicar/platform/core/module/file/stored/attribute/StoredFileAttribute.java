package com.softicar.platform.core.module.file.stored.attribute;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class StoredFileAttribute<R extends IEmfTableRow<R, ?>> extends EmfForeignRowAttribute<R, AGStoredFile> {

	public StoredFileAttribute(IDbForeignRowField<R, AGStoredFile, Integer> field) {

		super(field);
		setDisplayFactory(StoredFileDisplay::new);
		setInputFactory(StoredFileInput::new);
	}
}
