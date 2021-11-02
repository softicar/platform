package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.item.IBasicItem;

class TestEntity implements IEntity {

	private final int id;

	public TestEntity(int id) {

		this.id = id;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public int compareTo(IBasicItem o) {

		return Integer.valueOf(id).compareTo(o.getId());
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create("entity" + id);
	}
}
