package com.softicar.platform.emf.data.table.header.secondary;

import com.softicar.platform.emf.data.table.IEmfDataTable;
import java.util.List;

/**
 * Represents a list of {@link IEmfDataTableExtraRowColumnGroup} instances, for
 * use in a single extra row (secondary header, or footer) of an
 * {@link IEmfDataTable}.
 *
 * @author Alexander Schmidt
 */
public interface IEmfDataTableExtraRowColumnGroupList<R> {

	/**
	 * Adds the given {@link IEmfDataTableExtraRowColumnGroup}.
	 *
	 * @param columnGroup
	 *            the {@link IEmfDataTableExtraRowColumnGroup} to add (never
	 *            null)
	 */
	void add(IEmfDataTableExtraRowColumnGroup<R> columnGroup);

	/**
	 * Returns all {@link IEmfDataTableExtraRowColumnGroup} instances in this
	 * list.
	 *
	 * @return all {@link IEmfDataTableExtraRowColumnGroup} instances in this
	 *         list (never null)
	 */
	List<IEmfDataTableExtraRowColumnGroup<R>> getColumnGroups();
}
