package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfImportBusinessKeyColumnsCollector<R extends IEmfTableRow<R, P>, P, S> {

	private final EmfImportColumn<R, ?> foreignKeyColumn;
	private final IEmfAttribute<R, ?> foreignKeyColumnAttribute;

	private boolean isTargetTableScopeFieldPartOfBusinessKey = false;
	private Boolean isModuleInstanceTarget = null;
	private boolean collectedBusinessKeysValidity = true;

	public EmfImportBusinessKeyColumnsCollector(EmfImportColumn<R, ?> foreignKeyColumn, IEmfAttribute<R, ?> foreignKeyColumnAttribute) {

		this.foreignKeyColumnAttribute = foreignKeyColumnAttribute;
		this.foreignKeyColumn = foreignKeyColumn;
	}

	public boolean collect() {

		IEmfTable<R, P, S> targetTable = fetchTargetTable();

		for (IDbField<R, ?> targetTableBusinessKeyField: targetTable.getBusinessKey().getFields()) {

			EmfImportColumn<R, ?> column = new EmfImportColumn<>(targetTableBusinessKeyField);
			foreignKeyColumn.addParentColumn(column);

			IEmfAttribute<R, ?> attribute = targetTable.getAttribute(targetTableBusinessKeyField);

			if (isModuleInstanceTarget == null) {
				isModuleInstanceTarget = isModuleInstanceTarget(targetTableBusinessKeyField);
			}
			if (collectedBusinessKeysValidity && !isModuleInstanceTarget && !isTargetTableScopeFieldPartOfBusinessKey) {
				EmfFieldAttribute<R, ?> emfAttribute = (EmfFieldAttribute<R, ?>) attribute;
				isTargetTableScopeFieldPartOfBusinessKey = isScopeAttribute(emfAttribute, targetTable);
			}
			if (attribute instanceof EmfForeignRowAttribute) {
				boolean isBusinessKeyValid = new EmfImportBusinessKeyColumnsCollector<>(column, attribute).collect();
				if (!isBusinessKeyValid) {
					collectedBusinessKeysValidity = false;
				}
			}
		}

//		Log.finfo("foreignKeyColumn " + foreignKeyColumn);
//		Log.finfo("isTargetModuleInstance " + isTargetModuleInstance);
//		Log.finfo("targetTable.getScopeField() " + targetTable.getScopeField());
//		Log.finfo("targetTableScopeFieldIsPartOfBusinessKey " + isTargetTableScopeFieldPartOfBusinessKey);
//		Log.finfo("");

		return collectedBusinessKeysValidity && (isModuleInstanceTarget || isTargetTableScopeFieldPartOfBusinessKey);
	}

	private boolean isModuleInstanceTarget(IDbField<R, ?> field) {

		Class<R> valueClass = field.getTable().getValueClass();
		return IEmfModuleInstance.class.isAssignableFrom(valueClass);
	}

	private static <R extends IEmfTableRow<R, P>, P, V, S> boolean isScopeAttribute(EmfFieldAttribute<R, V> fieldAttribute, IEmfTable<R, ?, S> table) {

		return table//
			.getScopeField()
			.map(fieldAttribute.getTable()::getAttribute)
			.filter(attribute -> fieldAttribute.equals(attribute))
			.isPresent();
	}

	private IEmfTable<R, P, S> fetchTargetTable() {

		IEmfTable<?, ?, ?> targetTable = ((EmfForeignRowAttribute<R, ?>) foreignKeyColumnAttribute).getTargetTable();
		return CastUtils.cast(targetTable);
	}
}
