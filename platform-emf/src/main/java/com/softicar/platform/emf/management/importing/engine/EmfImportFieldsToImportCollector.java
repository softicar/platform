package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.emf.attribute.field.transaction.EmfTransactionAttribute;
import com.softicar.platform.emf.deactivation.IEmfTableRowDeactivationStrategy;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmfImportFieldsToImportCollector<R extends IEmfTableRow<R, P>, P> {

	private final IEmfTable<R, P, ?> table;
	private Set<ISqlField<?, ?>> ignoredFields;

	public EmfImportFieldsToImportCollector(IEmfTable<R, P, ?> table) {

		this.table = table;
	}

	public List<IDbField<R, ?>> collect() {

		ignoredFields = new HashSet<>();
		ignoreActiveField();
		ignoreGeneratedFields();
		ignoreTransactionFields();
		ignoreConcealedFieldsWithDefaultValue();
		ignoreScopeField();

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

	private void ignoreConcealedFieldsWithDefaultValue() {

		for (IDbField<R, ?> field: table.getAllFields()) {
			if (field.hasDefault() && table.getAttribute(field).isConcealed()) {
				ignoredFields.add(field);
			}
		}
	}
	
	// TODO loading could be simplified...
	private void ignore() {

		table//
			.getAllFields()
			.stream()
			.filter(field -> !(field.hasDefault() && table.getAttribute(field).isConcealed()))
			.filter(field -> !(table.getAttribute(field) instanceof EmfTransactionAttribute))
			.filter(field -> !(table.getAttribute(field) instanceof EmfTransactionAttribute));
	}
}
