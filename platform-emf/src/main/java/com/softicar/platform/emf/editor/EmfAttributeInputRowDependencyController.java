package com.softicar.platform.emf.editor;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.core.transaction.DbLazyTransaction;
import com.softicar.platform.db.core.transaction.IDbTransaction;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * This class controls the dependencies between input rows.
 *
 * @author Oliver Richers
 */
public class EmfAttributeInputRowDependencyController<R extends IEmfTableRow<R, ?>> {

	private final IEmfTable<R, ?, ?> table;
	private final Collection<EmfAttributeValueInputFrame<R, ?>> inputRows;
	private final Map<IEmfAttribute<R, ?>, EmfAttributeValueInputFrame<R, ?>> inputRowMap;
	private final HashSet<EmfAttributeValueInputFrame<R, ?>> refreshingInputRows;

	public EmfAttributeInputRowDependencyController(IEmfTable<R, ?, ?> table) {

		this.table = table;
		this.inputRows = new ArrayList<>();
		this.inputRowMap = new HashMap<>();
		this.refreshingInputRows = new HashSet<>();
	}

	public Collection<EmfAttributeValueInputFrame<R, ?>> getInputRows() {

		return inputRows;
	}

	public <V> void addInputRow(IEmfAttribute<R, V> attribute, EmfAttributeValueInputFrame<R, V> inputRow) {

		if (inputRowMap.put(attribute, inputRow) == null) {
			inputRows.add(inputRow);

			// are there inputs that the new input depends on?
			table//
				.getAttributeDependencies()
				.getDependees(attribute)
				.stream()
				.map(inputRowMap::get)
				.filter(row -> row != null)
				.forEach(this::setChangeCallback);

			// are there inputs depending on the new input?
			if (table//
				.getAttributeDependencies()
				.getDependers(attribute)
				.stream()
				.anyMatch(inputRowMap::containsKey)) {
				setChangeCallback(inputRow);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <V> EmfAttributeValueInputFrame<R, V> getInputRow(IEmfAttribute<R, V> attribute) {

		return (EmfAttributeValueInputFrame<R, V>) inputRowMap.get(attribute);
	}

	private void setChangeCallback(EmfAttributeValueInputFrame<R, ?> inputRow) {

		if (!refreshingInputRows.contains(inputRow)) {
			inputRow.setChangeCallback(this::refreshInputConstraints);
			refreshingInputRows.add(inputRow);
		}
	}

	private void refreshInputConstraints() {

		// using transaction to apply the attribute values temporarily
		try (IDbTransaction transaction = new DbLazyTransaction()) {
			for (EmfAttributeValueInputFrame<R, ?> row: inputRows) {
				try {
					row.applyToTableRow();
				} catch (Exception exception) {
					// failure to read input value shall be ignored
					DevNull.swallow(exception);
				}
			}

			inputRows.forEach(row -> row.refreshInputConstraints());
		}
	}
}
