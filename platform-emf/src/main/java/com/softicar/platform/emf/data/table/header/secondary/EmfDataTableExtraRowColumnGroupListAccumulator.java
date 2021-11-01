package com.softicar.platform.emf.data.table.header.secondary;

import java.util.ArrayList;
import java.util.List;

public class EmfDataTableExtraRowColumnGroupListAccumulator<R> implements IEmfDataTableExtraRowColumnGroupListAccumulator<R> {

	private final List<IEmfDataTableExtraRowColumnGroupList<R>> allGroupLists;
	private IEmfDataTableExtraRowColumnGroupList<R> currentGroupList;

	public EmfDataTableExtraRowColumnGroupListAccumulator() {

		this.allGroupLists = new ArrayList<>();
		this.currentGroupList = null;
	}

	@Override
	public IEmfDataTableExtraRowColumnGroupList<R> addNewColumnGroupList() {

		this.currentGroupList = new EmfDataTableExtraRowColumnGroupList<>();
		this.allGroupLists.add(this.currentGroupList);
		return this.currentGroupList;
	}

	@Override
	public List<IEmfDataTableExtraRowColumnGroupList<R>> getAllGroupLists() {

		return allGroupLists;
	}
}
