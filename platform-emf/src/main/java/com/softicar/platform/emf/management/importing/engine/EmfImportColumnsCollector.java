package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.attribute.field.transaction.EmfTransactionAttribute;
import com.softicar.platform.emf.deactivation.IEmfTableRowDeactivationStrategy;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class EmfImportColumnsCollector<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> table;
	private final Set<ISqlField<?, ?>> ignoredFields;
	private final Optional<S> scope;
	private List<EmfImportColumn<R, P>> tableColumns;
	private List<EmfImportColumn<R, ?>> csvFileColumns;

	public EmfImportColumnsCollector(IEmfTable<R, P, S> table) {

		this.table = table;
		this.ignoredFields = new HashSet<>();
		this.scope = Optional.empty();

		ignoreActiveField();
		ignoreGeneratedFields();
		ignoreTransactionFields();
//		ignoreConcealedFields();
//		ignoreScopeField();
	}

	public EmfImportColumnsCollector<R, P, S> collect() {

		csvFileColumns = new ArrayList<>();
		for (EmfImportColumn<R, P> tableColumn: collectTableColumns()) {
			csvFileColumns.addAll(resolveCsvFileColumns(tableColumn));
		}
		return this;
	}

	private List<EmfImportColumn<R, P>> collectTableColumns() {
	
		tableColumns = new ArrayList<>();
	
		for (IDbField<R, ?> field: getFieldsToImport()) {
	
			EmfImportColumn<R, P> tableColumn = new EmfImportColumn<>(field);
			tableColumns.add(tableColumn);
	
			IEmfAttribute<R, ?> fieldAttribute = table.getAttribute(field);
			if (fieldAttribute instanceof EmfForeignRowAttribute) {
				new EmfImportBusinessKeyColumnsCollector<>(fieldAttribute, tableColumn).collect();
			}
		}
		return tableColumns;
	}

	private List<EmfImportColumn<R, ?>> resolveCsvFileColumns(EmfImportColumn<R, ?> tableColumn) {
	
		List<EmfImportColumn<R, ?>> foreignKeyColumns = tableColumn.getForeignKeyColumns();
		if (foreignKeyColumns.isEmpty()) {
			return Arrays.asList(tableColumn);
		} else {
			List<EmfImportColumn<R, ?>> csvFileColumns = new ArrayList<>();
			for (EmfImportColumn<R, ?> foreignKeyColumn: foreignKeyColumns) {
				csvFileColumns.addAll(resolveCsvFileColumns(foreignKeyColumn));
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

	

	//////////////////////////////////////
	//
	//  Copied from EmfImportEngine:
	//

	private Collection<IDbField<R, ?>> getFieldsToImport() {

		return table//
			.getAllFields()
			.stream()
			.filter(field -> !ignoredFields.contains(field))
			.collect(Collectors.toList());
	}

	private void ignoreActiveField() {

		IEmfTableRowDeactivationStrategy<R> deactivationStrategy = table.getEmfTableConfiguration().getDeactivationStrategy();
		if (deactivationStrategy.isDeactivationSupported()) {
			for (IDbField<R, ?> field: table.getAllFields()) {
				if (deactivationStrategy.isActiveAttribute(table.getAttribute(field))) {
					ignoredFields.add(field);
				}
			}
		}
	}

	private void ignoreGeneratedFields() {

		if (table.getPrimaryKey().isGenerated()) {
			ignoredFields.addAll(table.getPrimaryKey().getFields());
		}
	}

	private void ignoreScopeField() {

		table.getScopeField().ifPresent(field -> ignoredFields.add(field));
	}

	private void ignoreTransactionFields() {

		for (IDbField<R, ?> field: table.getAllFields()) {
			if (table.getAttribute(field) instanceof EmfTransactionAttribute) {
				ignoredFields.add(field);
			}
		}
	}

	private void ignoreConcealedFields() {

		for (IDbField<R, ?> field: table.getAllFields()) {
			if (table.getAttribute(field).isConcealed()) {
				ignoredFields.add(field);
			}
		}
	}
}
