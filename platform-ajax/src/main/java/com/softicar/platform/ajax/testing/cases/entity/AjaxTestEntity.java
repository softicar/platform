package com.softicar.platform.ajax.testing.cases.entity;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.item.IBasicItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AjaxTestEntity implements IEntity {

	private final int id;
	private final String name;

	public AjaxTestEntity(int id, String name) {

		this.id = id;
		this.name = name;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public int compareTo(IBasicItem other) {

		return getItemId().compareTo(other.getItemId());
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(name);
	}

	@Override
	public String toString() {

		return name + " [" + id + "]";
	}

	public String getName() {

		return toDisplayWithoutId().toString();
	}

	public String toDisplayStringWithId() {

		return toDisplay().toString();
	}

	public String getIdAsString() {

		return getItemId() + "";
	}

	public static Collection<AjaxTestEntity> generate(String...names) {

		List<AjaxTestEntity> list = new ArrayList<>();
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			list.add(new AjaxTestEntity(i + 1, name));
		}
		return list;
	}
}
