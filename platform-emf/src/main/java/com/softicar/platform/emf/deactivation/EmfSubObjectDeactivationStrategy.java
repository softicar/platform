package com.softicar.platform.emf.deactivation;

import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.sql.statement.ISqlSelectOrJoin;
import com.softicar.platform.emf.attribute.EmfInheritedAttribute;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.sub.object.IEmfSubObject;
import com.softicar.platform.emf.sub.object.table.IEmfSubObjectTable;

public class EmfSubObjectDeactivationStrategy<R extends IEmfSubObject<R, B>, B extends IEmfEntity<B, ?>> extends EmfTableRowDeactivationStrategy<R> {

	private final IEmfTableRowDeactivationStrategy<B> baseDeactivationStrategy;
	private final IDbForeignRowField<R, B, ?> baseField;

	public EmfSubObjectDeactivationStrategy(IEmfSubObjectTable<R, B, ?, ?> table) {

		super(table);
		this.baseDeactivationStrategy = table.getBaseTable().getEmfTableConfiguration().getDeactivationStrategy();
		this.baseField = table.getPrimaryKeyField();
	}

	@Override
	public boolean isDeactivationSupported() {

		return activeField != null || baseDeactivationStrategy.isDeactivationSupported();
	}

	@Override
	public boolean isDeactivationAvailable(R tableRow) {

		return isDeactivationAllowed(tableRow) && isDeactivationAllowed(baseField.getValue(tableRow));
	}

	@Override
	public boolean isActive(R tableRow) {

		return activeField != null? activeField.getValue(tableRow) : baseDeactivationStrategy.isActive(baseField.getValue(tableRow));
	}

	@Override
	public void addWhereActive(ISqlSelectOrJoin<?, R, ?> select) {

		if (activeField != null) {
			select.where(activeField.isEqual(true));
		} else if (baseDeactivationStrategy.isDeactivationSupported()) {
			baseDeactivationStrategy.addWhereActive(select.join(baseField));
		}
	}

	@Override
	public <V> boolean isActiveAttribute(IEmfAttribute<R, V> attribute) {

		if (isActiveFieldAttribute(attribute)) {
			return true;
		} else if (attribute instanceof EmfInheritedAttribute) {
			// FIXME this cast in unfortunate (can be fixed with i49510)
			return isInheritedActiveAttribute((EmfInheritedAttribute<?, ?, ?>) attribute);
		} else {
			return false;
		}
	}

	@Override
	public void setActive(R tableRow, boolean active) {

		if (activeField != null) {
			activeField.setValue(tableRow, active);
		} else {
			baseDeactivationStrategy.setActive(baseField.getValue(tableRow), active);
		}
	}

	private <V> boolean isActiveFieldAttribute(IEmfAttribute<R, V> attribute) {

		return attribute//
			.getField()
			.map(field -> field.equals(activeField))
			.orElse(false);
	}

	private <EE extends IEmfSubObject<EE, BB>, BB extends IEmfEntity<BB, ?>, V> boolean isInheritedActiveAttribute(EmfInheritedAttribute<EE, BB, V> attribute) {

		IEmfAttribute<BB, V> originalAttribute = attribute.getOriginalAttribute();
		return originalAttribute.getTable().getEmfTableConfiguration().getDeactivationStrategy().isActiveAttribute(originalAttribute);
	}
}
