package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.item.BasicItemComparator;
import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import java.util.Optional;

public class EmfEnumTableRowEntityWrapper<R extends IDbEnumTableRow<R, V>, V extends IDbEnumTableRowEnum<V, R>> implements IEntity {

	private final R row;

	private EmfEnumTableRowEntityWrapper(R row) {

		this.row = row;
	}

	@Override
	public Integer getId() {

		return row.getId();
	}

	public String getName() {

		return row.getEnum().name();
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		if (row instanceof IEntity) {
			return ((IEntity) row).toDisplayWithoutId();
		} else if (row instanceof IDisplayable) {
			return ((IDisplayable) row).toDisplay();
		} else {
			return IDisplayString.create(getName());
		}
	}

	@Override
	public IDisplayString toDisplay() {

		if (row instanceof IEntity) {
			// respect the entity to decide if IDs shall be shown
			return ((IEntity) row).toDisplay();
		} else {
			return IEntity.super.toDisplay();
		}
	}

	@Override
	public int compareTo(IBasicItem other) {

		return BasicItemComparator.get().compare(row, other);
	}

	public R getRow() {

		return row;
	}

	public static <R extends IDbEnumTableRow<R, V>, V extends IDbEnumTableRowEnum<V, R>> EmfEnumTableRowEntityWrapper<R, V> wrap(R row) {

		return Optional.ofNullable(row).map(EmfEnumTableRowEntityWrapper::new).orElse(null);
	}
}
