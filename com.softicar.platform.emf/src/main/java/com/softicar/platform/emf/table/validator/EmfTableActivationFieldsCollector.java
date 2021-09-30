package com.softicar.platform.emf.table.validator;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.deactivation.IEmfTableRowDeactivationStrategy;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Searches for all activation fields of an {@link IEmfTable}.
 * <p>
 * Usually there is a maximum of one activation field.
 *
 * @author Oliver Richers
 */
public class EmfTableActivationFieldsCollector {

	private final IEmfTable<?, ?, ?> rootTable;
	private Collection<IDbField<?, Boolean>> activeFields;

	/**
	 * Constructs this object.
	 *
	 * @param table
	 *            the {@link IEmfTable} to scan (never null)
	 */
	public EmfTableActivationFieldsCollector(IEmfTable<?, ?, ?> table) {

		this.rootTable = table;
	}

	/**
	 * The found activation fields.
	 *
	 * @return all activation fields (never null)
	 */
	public Collection<IDbField<?, Boolean>> collect() {

		this.activeFields = new ArrayList<>();
		collectDeep(rootTable);
		return activeFields;
	}

	private void collectDeep(IEmfTable<?, ?, ?> table) {

		collect(table);
		table.getTableSpecialization().getBaseTable().ifPresent(this::collectDeep);
	}

	private <R extends IEmfTableRow<R, ?>> void collect(IEmfTable<R, ?, ?> table) {

		IEmfTableRowDeactivationStrategy<R> deactivationStrategy = table.getEmfTableConfiguration().getDeactivationStrategy();

		table//
			.getDataFields()
			.stream()
			.filter((IDbField<R, ?> field) -> deactivationStrategy.isActiveAttribute(table.getAttribute(field)))
			.map(this::castToBooleanField)
			.forEach(activeFields::add);
	}

	private <R extends IEmfTableRow<R, ?>> IDbField<R, Boolean> castToBooleanField(IDbField<R, ?> field) {

		return CastUtils.cast(field);
	}
}
