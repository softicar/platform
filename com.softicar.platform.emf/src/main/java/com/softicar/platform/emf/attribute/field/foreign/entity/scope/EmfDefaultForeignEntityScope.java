package com.softicar.platform.emf.attribute.field.foreign.entity.scope;

import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

public class EmfDefaultForeignEntityScope<S extends IEmfTableRow<S, ?>, T extends IEmfTableRow<T, ?>> implements IEmfForeignEntityScope<S, T> {

	@Override
	public boolean isVisible(S sourceEntity, T targetEntity) {

		// 1. case: target entity has no scope field, thus is reference data
		Optional<? extends ISqlForeignRowField<T, ?, ?>> targetScopeField = targetEntity.table().getScopeField();
		if (!targetScopeField.isPresent()) {
			return true;
		}

		// 2. case: source entity is scope of target entity
		IBasicItem scopeOfTargetEntity = getBasicItemOrNull(targetScopeField.get(), targetEntity);
		if (sourceEntity instanceof IBasicItem && IEmfForeignEntityScope.isSame((IBasicItem) sourceEntity, scopeOfTargetEntity)) {
			return true;
		}

		// 3. case: source entity is reference data but target entity is not
		Optional<? extends ISqlForeignRowField<S, ?, ?>> sourceScopeField = sourceEntity.table().getScopeField();
		if (!sourceScopeField.isPresent()) {
			return false;
		}

		// 4. case: scope of source entity is scope of target entity
		IBasicItem scopeOfSourceEntity = getBasicItemOrNull(sourceScopeField.get(), sourceEntity);
		if (IEmfForeignEntityScope.isSame(scopeOfSourceEntity, scopeOfTargetEntity)) {
			return true;
		}

		// otherwise, be conservative
		return false;
	}

	private <R> IBasicItem getBasicItemOrNull(ISqlForeignRowField<R, ?, ?> scopeField, R tableRow) {

		Object scope = scopeField.getValue(tableRow);
		return scope instanceof IBasicItem? (IBasicItem) scope : null;
	}
}
