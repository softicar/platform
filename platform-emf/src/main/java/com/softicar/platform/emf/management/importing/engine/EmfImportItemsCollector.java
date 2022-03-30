package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.utils.CastUtils;
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

	public List<EmfImportItem> getCsvFileItems() {

		List<EmfImportItem> csvItems = new ArrayList<>();
		for (EmfImportItem item: collect()) {
			csvItems.addAll(resolveCsvItems(item));
		}
		return csvItems;
	}

	private List<EmfImportItem> collect() {

		List<EmfImportItem> items = new ArrayList<>();

		for (IDbField<R, ?> field: getFieldsToImport()) {

			EmfImportItem item = new EmfImportItem(field);
			items.add(item);

			IEmfAttribute<R, ?> attribute = table.getAttribute(field);
			if (attribute instanceof EmfForeignRowAttribute) {
				collectBusinessKeyItems(item, attribute);
			}
		}
		return items;
	}

	private void collectBusinessKeyItems(EmfImportItem item, IEmfAttribute<R, ?> attribute) {

		EmfForeignRowAttribute<R, ?> foreignRowAttribute = CastUtils.cast(attribute);
		IEmfTable<?, ?, ?> emfTable = foreignRowAttribute.getTargetTable();
		new EmfImportBusinessKeyItemsCollector<>(emfTable, item).collect();
	}

	private List<EmfImportItem> resolveCsvItems(EmfImportItem item) {

		List<EmfImportItem> constituents = item.getConstituents();
		if (constituents.isEmpty()) {
			return Arrays.asList(item);
		} else {
			List<EmfImportItem> csvItems = new ArrayList<>();
			for (EmfImportItem constituent: constituents) {
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
