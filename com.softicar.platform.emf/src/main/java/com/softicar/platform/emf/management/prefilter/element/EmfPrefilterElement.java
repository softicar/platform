package com.softicar.platform.emf.management.prefilter.element;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.sql.statement.ISqlJoin;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.emf.management.prefilter.AbstractEmfPrefilterRow;
import com.softicar.platform.emf.management.prefilter.EmfPrefilterPopup;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.object.table.IEmfObjectTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * This class represents a filter element that can be used in an
 * {@link EmfPrefilterPopup}.
 *
 * @param <S>
 *            the type of the scope
 * @param <E>
 *            the type {@link IEmfObject} you want to filter
 * @param <T>
 *            the type of {@link IEmfObject} you want to return, having <S> as
 *            scope
 * @param <R>
 *            the type of the {@link AbstractEmfPrefilterRow}
 * @author Alexander Schmidt
 * @author Daniel Klose
 */
// FIXME i60829: Find way to reduce amount of types, 4 are pretty much
public class EmfPrefilterElement<S, E extends IEmfTableRow<E, ?>, T extends IEmfObject<T>, R extends AbstractEmfPrefilterRow<E>>
		extends AbstractEmfPrefilterElement<S, E, T, R> {

	private final Function<ISqlSelect<T>, ISqlJoin<T, E>> reverseJoiner;
	private final IEmfObjectTable<T, S> targetTable;

	public EmfPrefilterElement(IDisplayString caption, S scope, IDbForeignField<E, T> foreignField, IEmfObjectTable<T, S> targetTable,
			Function<S, R> rowFactory) {

		this(caption, scope, select -> select.joinReverse(foreignField), targetTable, rowFactory);
	}

	public EmfPrefilterElement(IDisplayString caption, S scope, Function<ISqlSelect<T>, ISqlJoin<T, E>> reverseJoiner, IEmfObjectTable<T, S> targetTable,
			Function<S, R> rowFactory) {

		super(caption, scope, rowFactory);
		this.reverseJoiner = reverseJoiner;
		this.targetTable = targetTable;
	}

	@Override
	public Collection<T> getFilteredItems() {

		ISqlSelect<T> select = targetTable.createSelect().where(targetTable.getScopeField().get().equal(getScope()));
		List<R> filterRows = getFilterRows();
		if (filterRows.isEmpty()) {
			return select.list();
		}
		switch (getFilterType()) {
		case AND:
			for (R row: filterRows) {
				reverseJoiner.apply(select).where(row.getFilterExpression());
			}
			return select.list();
		case OR:
			ISqlJoin<T, E> join = reverseJoiner.apply(select);
			boolean firstRow = true;
			for (R row: filterRows) {
				if (firstRow) {
					join.where(row.getFilterExpression());
					firstRow = false;
				} else {
					join.orWhere(row.getFilterExpression());
				}
			}
			return join.list();
		default:
			throw new SofticarUnknownEnumConstantException(getFilterType());
		}
	}
}
