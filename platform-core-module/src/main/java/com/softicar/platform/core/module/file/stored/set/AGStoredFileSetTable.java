package com.softicar.platform.core.module.file.stored.set;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.set.attribute.StoredFileSetAttribute;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.emf.collection.set.AbstractEmfEntitySetTable;
import com.softicar.platform.emf.object.table.IEmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGStoredFileSetTable extends AbstractEmfEntitySetTable<AGStoredFileSet, AGStoredFileSetItem, AGStoredFile, SystemModuleInstance> {

	public AGStoredFileSetTable(IDbObjectTableBuilder<AGStoredFileSet> builder) {

		super(builder, AGStoredFileSet.HASH);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGStoredFileSet, Integer, SystemModuleInstance> configuration) {

		configuration.setAttributeFactory(StoredFileSetAttribute::new);
	}

	@Override
	protected IDbRecordTable<AGStoredFileSetItem, Tuple2<AGStoredFileSet, AGStoredFile>> getItemTable() {

		return AGStoredFileSetItem.TABLE;
	}

	@Override
	protected IDbForeignField<AGStoredFileSetItem, AGStoredFileSet> getItemSetField() {

		return AGStoredFileSetItem.FILE_SET;
	}

	@Override
	protected IDbForeignRowField<AGStoredFileSetItem, AGStoredFile, ?> getItemElementField() {

		return AGStoredFileSetItem.FILE;
	}

	@Override
	public IEmfObjectTable<AGStoredFile, ?> getElementTable() {

		return AGStoredFile.TABLE;
	}
}
