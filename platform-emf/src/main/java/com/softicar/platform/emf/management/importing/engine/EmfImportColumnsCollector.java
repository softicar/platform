package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EmfImportColumnsCollector<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> table;
	private final boolean ignoreScopeField;
	private List<EmfImportColumn<R, P>> tableColumns;
	private List<EmfImportColumn<R, ?>> csvFileColumns;

	public EmfImportColumnsCollector(IEmfTable<R, P, S> table, boolean ignoreScopeField) {

		this.table = table;
		this.ignoreScopeField = ignoreScopeField;
	}

	private void collect() {

		csvFileColumns = new ArrayList<>();
		for (EmfImportColumn<R, P> tableColumn: collectTableColumns()) {
			csvFileColumns.addAll(resolveToCsvFileColumns(tableColumn));
		}
	}

	private List<EmfImportColumn<R, P>> collectTableColumns() {

		tableColumns = new ArrayList<>();

		for (IDbField<R, ?> field: new EmfImportFieldsToImportCollector<>(table).collect(ignoreScopeField)) {

			EmfImportColumn<R, P> tableColumn = new EmfImportColumn<>(field);
			tableColumns.add(tableColumn);

			IEmfAttribute<R, ?> fieldAttribute = table.getAttribute(field);
			if (fieldAttribute instanceof EmfForeignRowAttribute) {
				new EmfImportBusinessKeyColumnsCollector<>(fieldAttribute, tableColumn).collect();
			}
		}
		return tableColumns;
	}

	private List<EmfImportColumn<R, ?>> resolveToCsvFileColumns(EmfImportColumn<R, ?> tableColumn) {

		List<EmfImportColumn<R, ?>> foreignKeyColumns = tableColumn.getForeignKeyColumns();
		if (foreignKeyColumns.isEmpty()) {
			return Arrays.asList(tableColumn);
		} else {
			List<EmfImportColumn<R, ?>> csvFileColumns = new ArrayList<>();
			for (EmfImportColumn<R, ?> foreignKeyColumn: foreignKeyColumns) {
				csvFileColumns.addAll(resolveToCsvFileColumns(foreignKeyColumn));
			}
			return csvFileColumns;
		}
	}

	public List<EmfImportColumn<R, ?>> getCsvFileColumnsToImport() {

		if (csvFileColumns == null) {
			collect();
		}
		return csvFileColumns;
	}

	public List<EmfImportColumn<R, P>> getTableColumns() {

		if (tableColumns == null) {
			collect();
		}
		return tableColumns;
	}

	public List<IDbField<R, ?>> getFieldsOfTableColumns() {

		return getTableColumns().stream().map(EmfImportColumn::getField).collect(Collectors.toList());
	}
}
