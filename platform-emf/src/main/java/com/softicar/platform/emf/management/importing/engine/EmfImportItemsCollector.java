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

public class EmfImportItemsCollector<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> table;
	private final Set<ISqlField<?, ?>> ignoredFields;
	private final Optional<S> scope;
	private List<EmfImportItem<R, ?>> csvItems;
	private List<EmfImportItem<R, P>> tableItems;

	public EmfImportItemsCollector(IEmfTable<R, P, S> table) {

		this.table = table;
		this.ignoredFields = new HashSet<>();
		this.scope = Optional.empty();

		ignoreActiveField();
		ignoreGeneratedFields();
		ignoreTransactionFields();
//		ignoreConcealedFields();
//		ignoreScopeField();
	}

	public List<EmfImportItem<R, ?>> getCsvFileItems() {

		csvItems = new ArrayList<>();
		for (EmfImportItem<R, P> item: collect()) {
			csvItems.addAll(resolveCsvItems(item));
		}
		return csvItems;
	}

	public List<EmfImportItem<R, P>> getTableItems() {

		return tableItems;
	}

	private List<EmfImportItem<R, P>> collect() {

		tableItems = new ArrayList<>();

		for (IDbField<R, ?> field: getFieldsToImport()) {

			EmfImportItem<R, P> item = new EmfImportItem<>(field);
			tableItems.add(item);

			IEmfAttribute<R, ?> fieldAttribute = table.getAttribute(field);
			if (fieldAttribute instanceof EmfForeignRowAttribute) {
				new EmfImportBusinessKeyItemsCollector<>(fieldAttribute, item).collect();
			}
		}
		return tableItems;
	}

	private List<EmfImportItem<R, ?>> resolveCsvItems(EmfImportItem<R, ?> item) {

		List<EmfImportItem<R, ?>> constituents = item.getConstituents();
		if (constituents.isEmpty()) {
			return Arrays.asList(item);
		} else {
			List<EmfImportItem<R, ?>> csvItems = new ArrayList<>();
			for (EmfImportItem<R, ?> constituent: constituents) {
				csvItems.addAll(resolveCsvItems(constituent));
			}
			return csvItems;
		}
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
