package com.softicar.platform.emf.log;

import com.softicar.platform.db.core.transaction.IDbTransaction;
import com.softicar.platform.db.core.transaction.IDbTransactionListener;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This class keeps track of which fields of an entity where changed.
 *
 * @author Oliver Richers
 */
public class EmfChangeTracker implements IDbTransactionListener {

	private final Map<IEmfTableRow<?, ?>, Set<IDbField<?, ?>>> changedFields;

	public EmfChangeTracker() {

		this.changedFields = new IdentityHashMap<>();
	}

	public <R extends IEmfTableRow<R, ?>> void trackChanges(Collection<R> tableRows) {

		for (R tableRow: tableRows) {
			for (IDbField<R, ?> field: tableRow.table().getDataFields()) {
				if (tableRow.impermanent() || tableRow.dataChanged(field)) {
					addField(tableRow, field);
				}
			}
		}
	}

	public <R extends IEmfTableRow<R, ?>> boolean isChanged(R tableRow, IDbField<R, ?> field) {

		return changedFields.getOrDefault(tableRow, Collections.emptySet()).contains(field);
	}

	@Override
	public void afterCommit(IDbTransaction transaction) {

		transaction//
			.getParentTransaction()
			.ifPresent(this::elevateToParent);
	}

	private void elevateToParent(IDbTransaction parentTransaction) {

		parentTransaction//
			.getOrPutData(EmfChangeTracker.class, EmfChangeTracker::new)
			.mergeChildTracker(this);
	}

	private void mergeChildTracker(EmfChangeTracker childTracker) {

		for (Entry<IEmfTableRow<?, ?>, Set<IDbField<?, ?>>> entry: childTracker.changedFields.entrySet()) {
			for (IDbField<?, ?> field: entry.getValue()) {
				addField(entry.getKey(), field);
			}
		}
	}

	private void addField(IEmfTableRow<?, ?> entity, IDbField<?, ?> field) {

		changedFields.computeIfAbsent(entity, it -> new HashSet<>()).add(field);
	}
}
