package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;

public class EmfImportColumnsStructure<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> table;
	private final List<IDbField<R, ?>> fieldsToImport;
	private List<EmfImportColumn<R, ?>> csvFileColumns;
	private List<EmfImportColumn<R, P>> tableColumns;

	public EmfImportColumnsStructure(IEmfTable<R, P, S> table, List<IDbField<R, ?>> fieldsToImport) {

		this.table = table;
		this.fieldsToImport = fieldsToImport;
	}

	public EmfImportColumnsStructure(IEmfTable<R, P, S> table) {

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
				new EmfImportBusinessKeyColumnsCollector<>(tableColumn, fieldAttribute).collect();
			}
		}
		return tableColumns;
	}

	private List<EmfImportColumn<R, ?>> resolveToCsvFileColumns(EmfImportColumn<R, ?> tableColumn) {

		List<EmfImportColumn<R, ?>> parentColumns = tableColumn.getParentColumns();
		if (parentColumns.isEmpty()) {
			return List.of(tableColumn);
		} else {
			List<EmfImportColumn<R, ?>> csvFileColumns = new ArrayList<>();
			for (EmfImportColumn<R, ?> parentColumn: parentColumns) {
				csvFileColumns.addAll(resolveToCsvFileColumns(parentColumn));
			}
			return csvFileColumns;
		}
	}

	public List<EmfImportColumn<R, ?>> getCsvFileColumns() {

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

	public IEmfTable<R, P, S> getTable() {

		return table;
	}
}
