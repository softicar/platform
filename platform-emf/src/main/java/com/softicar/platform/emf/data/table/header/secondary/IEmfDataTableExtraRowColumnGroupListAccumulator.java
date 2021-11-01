package com.softicar.platform.emf.data.table.header.secondary;

import java.util.List;

/**
 * Creates and tracks {@link IEmfDataTableExtraRowColumnGroupList} instances,
 * and provides access to them.
 *
 * @author Alexander Schmidt
 */
public interface IEmfDataTableExtraRowColumnGroupListAccumulator<R> {

	/**
	 * Creates a new {@link IEmfDataTableExtraRowColumnGroupList}, and registers
	 * it internally.
	 *
	 * @return a new {@link IEmfDataTableExtraRowColumnGroupList} (never null)
	 */
	IEmfDataTableExtraRowColumnGroupList<R> addNewColumnGroupList();

	/**
	 * Returns the {@link IEmfDataTableExtraRowColumnGroupList} that were
	 * created so far.
	 *
	 * @return the {@link IEmfDataTableExtraRowColumnGroupList} that were
	 *         created so far (never null)
	 */
	List<IEmfDataTableExtraRowColumnGroupList<R>> getAllGroupLists();
}
