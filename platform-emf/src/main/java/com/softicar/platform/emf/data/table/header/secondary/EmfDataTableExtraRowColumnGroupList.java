package com.softicar.platform.emf.data.table.header.secondary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmfDataTableExtraRowColumnGroupList<R> implements IEmfDataTableExtraRowColumnGroupList<R> {

	private final List<IEmfDataTableExtraRowColumnGroup<R>> list;

	public EmfDataTableExtraRowColumnGroupList() {

		this.list = new ArrayList<>();
	}

	@Override
	public void add(IEmfDataTableExtraRowColumnGroup<R> columnGroup) {

		this.list.add(columnGroup);
	}

	@Override
	public List<IEmfDataTableExtraRowColumnGroup<R>> getColumnGroups() {

		return Collections.unmodifiableList(list);
	}
}
