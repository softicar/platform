package com.softicar.platform.emf.trait.table;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.emf.record.table.EmfRecordTable;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.trait.IEmfTrait;
import java.util.Collection;
import java.util.Map;

public class EmfTraitTable<T extends IEmfTrait<T, R>, R extends IEmfTableRow<R, ?>> extends EmfRecordTable<T, R, R> implements IEmfTraitTable<T, R> {

	private final IDbForeignRowField<T, R, ?> primaryKeyField;
	private final IEmfTable<R, ?, ?> targetTable;

	public EmfTraitTable(IDbTableBuilder<T, R> builder) {

		super(builder);

		this.primaryKeyField = new EmfTraitTablePrimaryKeyFieldDeterminer<>(this).determine();
		this.targetTable = CastUtils.cast(primaryKeyField.getTargetTable());

		configuration.setScopeField(primaryKeyField);
	}

	@Override
	public IEmfTable<R, ?, ?> getTargetTable() {

		return targetTable;
	}

	@Override
	public T getOrCreate(R key) {

		T trait = super.getOrCreate(key);
		initializeFieldsIfNecessary(trait);
		return trait;
	}

	@Override
	public Map<R, T> getOrCreateAsMap(Collection<R> keys) {

		Map<R, T> map = super.getOrCreateAsMap(keys);
		map//
			.values()
			.forEach(this::initializeFieldsIfNecessary);
		return map;
	}

	private void initializeFieldsIfNecessary(T trait) {

		if (trait.impermanent()) {
			trait.table().getTableSpecialization().initializeFields(trait, trait.pk());
		}
	}
}
