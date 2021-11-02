package com.softicar.platform.emf.deactivation;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.IDbBasicTable;
import com.softicar.platform.db.sql.statement.ISqlSelectOrJoin;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

/**
 * Default implementation of {@link IEmfTableRowDeactivationStrategy}.
 * <p>
 * This strategy searches the table for a boolean field with name <i>active</i>.
 * If such a field is found, deactivation status is supported.
 *
 * @author Oliver Richers
 */
public class EmfTableRowDeactivationStrategy<R extends IEmfTableRow<R, ?>> implements IEmfTableRowDeactivationStrategy<R> {

	private static final String DEFAULT_ACTIVE_FIELD_NAME = "active";

	protected IDbField<R, Boolean> activeField;

	public EmfTableRowDeactivationStrategy(IEmfTable<R, ?, ?> table) {

		this.activeField = getDefaultActiveField(table);
	}

	@Override
	public boolean isDeactivationSupported() {

		return activeField != null;
	}

	@Override
	public boolean isDeactivationAvailable(R tableRow) {

		return isDeactivationAllowed(tableRow);
	}

	@Override
	public boolean isActive(R tableRow) {

		return activeField != null? activeField.getValue(tableRow) : true;
	}

	@Override
	public void addWhereActive(ISqlSelectOrJoin<?, R, ?> select) {

		if (activeField != null) {
			select.where(activeField.isEqual(true));
		}
	}

	@Override
	public <V> boolean isActiveAttribute(IEmfAttribute<R, V> attribute) {

		Optional<IDbField<R, V>> field = attribute.getField();
		if (field.isPresent()) {
			return field.get().equals(activeField);
		} else {
			return false;
		}
	}

	@Override
	public void setActive(R tableRow, boolean active) {

		if (activeField != null) {
			activeField.setValue(tableRow, active);
		}
	}

	public void setActiveField(IDbField<R, Boolean> activeField) {

		this.activeField = activeField;
	}

	protected <T extends IEmfTableRow<T, ?>> boolean isDeactivationAllowed(T tableRow) {

		return tableRow//
			.table()
			.getEmfTableConfiguration()
			.getDeactivationPredicate()
			.test(tableRow);
	}

	private IDbField<R, Boolean> getDefaultActiveField(IDbBasicTable<R> table) {

		for (IDbField<R, ?> field: table.getAllFields()) {
			if (field.getFieldType() == SqlFieldType.BOOLEAN) {
				if (field.getName().toLowerCase().equals(DEFAULT_ACTIVE_FIELD_NAME.toLowerCase())) {
					return CastUtils.cast(field);
				}
			}
		}
		return null;
	}
}
