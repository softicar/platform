package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmfImportColumnsCollector<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> table;
	private final List<IDbField<R, ?>> fieldsToImport;
	private List<EmfImportColumn<R, ?>> csvFileColumns;
	private List<EmfImportColumn<R, P>> tableColumns;
	private List<IDbField<R, ?>> fieldsOfTableColumns;

	public EmfImportColumnsCollector(IEmfTable<R, P, S> table, List<IDbField<R, ?>> fieldsToImport) {

		this.table = table;
		this.fieldsToImport = fieldsToImport;
	}

	public EmfImportColumnsCollector(IEmfTable<R, P, S> table) {

		this(table, new ArrayList<>(table.getAllFields()));
	}

	private void collect() {

		csvFileColumns = new ArrayList<>();
		for (EmfImportColumn<R, P> tableColumn: collectTableColumns()) {
			csvFileColumns.addAll(resolveToCsvFileColumns(tableColumn));
		}
	}

	private List<EmfImportColumn<R, P>> collectTableColumns() {

		tableColumns = new ArrayList<>();

		for (IDbField<R, ?> field: fieldsToImport) {

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
			return List.of(tableColumn);
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

		if (fieldsOfTableColumns == null) {
			fieldsOfTableColumns = getTableColumns().stream().map(EmfImportColumn::getField).collect(Collectors.toList());
		}
		return fieldsOfTableColumns;
	}

	public IDbField<R, ?> getFieldOfTableColumnByIndex(int index) {

		return getFieldsOfTableColumns().get(index);
	}

	public IEmfTable<R, P, S> getTable() {

		return table;
	}
}
