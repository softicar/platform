package com.softicar.platform.emf.attribute.transients;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.transients.ITransientField;
import com.softicar.platform.emf.attribute.AbstractEmfAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class EmfTransientAttribute<R extends IEmfTableRow<R, ?>, V> extends AbstractEmfAttribute<R, V> {

	private final IEmfTable<R, ?, ?> table;
	private final ITransientField<R, V> field;

	public EmfTransientAttribute(IEmfTable<R, ?, ?> table, ITransientField<R, V> field) {

		this.table = Objects.requireNonNull(table);
		this.field = Objects.requireNonNull(field);

		setDisplayFactory(EmfTransientAttributeDisplay::new);
	}

	@Override
	public IEmfTable<R, ?, ?> getTable() {

		return table;
	}

	@Override
	public Optional<IDbField<R, V>> getField() {

		return Optional.empty();
	}

	@Override
	public Class<V> getValueClass() {

		return field.getValueType().getValueClass();
	}

	@Override
	public Optional<Comparator<V>> getValueComparator() {

		return field.getValueType().getComparator();
	}

	@Override
	public void prefetchValues(Collection<R> entities) {

		field.prefetchAll(entities);
	}

	@Override
	public V getValue(R tableRow) {

		return field.getValue(tableRow);
	}

	@Override
	public void setValue(R tableRow, V value) {

		throw new SofticarDeveloperException("Transient attributes cannot be written.");
	}

	@Override
	public IDisplayString getTitle() {

		return field.getTitle();
	}

	@Override
	public IStaticObject getTestMarker() {

		return field;
	}

	@Override
	public boolean isScope() {

		return false;
	}

	@Override
	public boolean isConcealed() {

		return false;
	}

	@Override
	public boolean isEditable() {

		return false;
	}

	@Override
	public boolean isImmutable() {

		return false;
	}

	@Override
	public boolean isTransient() {

		return true;
	}

	@Override
	public void validate(R tableRow, IEmfValidationResult result) {

		// nothing to do
	}
}
