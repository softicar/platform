package com.softicar.platform.emf.management.prefilter.element;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.sql.statement.ISqlJoin;
import com.softicar.platform.emf.management.prefilter.AbstractEmfPrefilterRow;
import com.softicar.platform.emf.management.prefilter.EmfPrefilterPopup;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.sub.object.IEmfSubObject;
import com.softicar.platform.emf.sub.object.table.IEmfSubObjectTable;
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
 *            the type of {@link IEmfSubObject} you want to return, having <S>
 *            as scope
 * @param <R>
 *            the type of the {@link AbstractEmfPrefilterRow}
 * @author Daniel Klose
 */
// FIXME i60829: Find way to reduce amount of types, there is one more because of SubObject structure,
public class EmfSubObjectPrefilterElement<S, E extends IEmfObject<E>, B extends IEmfObject<B>, T extends IEmfSubObject<T, B>, R extends AbstractEmfPrefilterRow<E>>
		extends AbstractEmfPrefilterElement<S, E, T, R> {

	private final Function<ISqlJoin<T, B>, ISqlJoin<T, E>> reverseJoiner;
	private final IEmfSubObjectTable<T, B, ?, S> targetTable;

	public EmfSubObjectPrefilterElement(IDisplayString caption, S scope, IDbForeignField<E, B> foreignField, IEmfSubObjectTable<T, B, ?, S> targetTable,
			Function<S, R> rowFactory) {

		this(caption, scope, join -> join.joinReverse(foreignField), targetTable, rowFactory);
	}

	public EmfSubObjectPrefilterElement(IDisplayString caption, S scope, Function<ISqlJoin<T, B>, ISqlJoin<T, E>> reverseJoiner,
			IEmfSubObjectTable<T, B, ?, S> targetTable, Function<S, R> rowFactory) {

		super(caption, scope, rowFactory);
		this.reverseJoiner = reverseJoiner;
		this.targetTable = targetTable;
	}

	@Override
	public Collection<T> getFilteredItems() {

		ISqlJoin<T, B> select = targetTable//
			.createSelect()
			.where(targetTable.getScopeField().get().equal(getScope()))
			.join(targetTable.getPrimaryKeyField());
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
