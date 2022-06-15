package com.softicar.platform.core.module.file.stored.set;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.set.attribute.StoredFileSetAttribute;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.entity.set.AbstractEmfEntitySetTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGStoredFileSetTable extends AbstractEmfEntitySetTable<AGStoredFileSet, AGStoredFileSetItem, AGStoredFile, AGCoreModuleInstance> {

	public AGStoredFileSetTable(IDbObjectTableBuilder<AGStoredFileSet> builder) {

		super(//
			builder,
			AGStoredFileSetItem.TABLE,
			AGStoredFileSetItem.FILE_SET,
			AGStoredFileSetItem.FILE,
			AGStoredFileSet.HASH);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGStoredFileSet, Integer, AGCoreModuleInstance> configuration) {

		configuration.setAttributeFactory(StoredFileSetAttribute::new);
	}
}
