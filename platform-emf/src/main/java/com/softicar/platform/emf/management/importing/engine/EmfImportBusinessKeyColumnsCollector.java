package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfImportBusinessKeyColumnsCollector<R extends IEmfTableRow<R, P>, P, S> {

	private final EmfImportColumn<R, ?> foreignKeyColumn;
	private final IEmfAttribute<R, ?> foreignKeyColumnAttribute;
	private Boolean businessKeysValidity;

	public EmfImportBusinessKeyColumnsCollector(EmfImportColumn<R, ?> foreignKeyColumn, IEmfAttribute<R, ?> foreignKeyColumnAttribute) {

		this.foreignKeyColumnAttribute = foreignKeyColumnAttribute;
		this.foreignKeyColumn = foreignKeyColumn;
	}

	public EmfImportBusinessKeyColumnsCollector<R, P, S> collect() {

		IEmfTable<R, P, S> targetTable = fetchTargetTable();

		businessKeysValidity = withoutScopeFieldOrBusinessKeyComprisesScopeField(targetTable);

		for (IDbField<R, ?> targetTableBusinessKeyField: targetTable.getBusinessKey().getFields()) {

			EmfImportColumn<R, ?> column = new EmfImportColumn<>(targetTableBusinessKeyField);
			foreignKeyColumn.addParentColumn(column);

			IEmfAttribute<R, ?> attribute = targetTable.getAttribute(targetTableBusinessKeyField);

			if (attribute instanceof EmfForeignRowAttribute) {
				boolean superTablesBusinessKeysValidity = new EmfImportBusinessKeyColumnsCollector<>(column, attribute)//
					.collect()
					.getBusinessKeysValidity();
				if (!superTablesBusinessKeysValidity) {
					businessKeysValidity = false;
				}
			}
		}
		return this;
	}

	private IEmfTable<R, P, S> fetchTargetTable() {

		IEmfTable<?, ?, ?> targetTable = ((EmfForeignRowAttribute<R, ?>) foreignKeyColumnAttribute).getTargetTable();
		return CastUtils.cast(targetTable);
	}

	private boolean withoutScopeFieldOrBusinessKeyComprisesScopeField(IEmfTable<R, P, S> table) {

		// TODO
//		if (table.getScopeField().isPresent()) {
//			return businessKeyComprisesScopeField(table);
//		} else {
//			return true;
//		}

		return table//
			.getScopeField()
			.map(it -> businessKeyComprisesScopeField(table))
			.orElse(true);
	}

	private boolean businessKeyComprisesScopeField(IEmfTable<R, P, S> table) {

		for (IDbField<R, ?> field: table.getBusinessKey().getFields()) {
			EmfFieldAttribute<R, ?> emfFieldAttribute = (EmfFieldAttribute<R, ?>) table.getAttribute(field);
			if (emfFieldAttribute.isScopeAttribute(table)) {
				return true;
			}
		}

		Log
			.finfo(
				"The business key (%s) of table %s does not comprise the table's scope field (%s).",//
				Imploder.implode(table.getBusinessKey().getFields(), " & "),
				table.toString(),
				table.getScopeField().get());

		return false;
	}

	public boolean getBusinessKeysValidity() {

		if (businessKeysValidity == null) {
			collect();
		}
		return businessKeysValidity;
	}
}
