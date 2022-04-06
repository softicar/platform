package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.common.core.transaction.ITransaction;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.enums.IDbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.dom.elements.input.auto.entity.AbstractDomAutoCompleteTransactionalEntityInputEngine;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmfEnumTableRowInputEngine<R extends IDbEnumTableRow<R, E>, E extends IDbEnumTableRowEnum<E, R>>
		extends AbstractDomAutoCompleteTransactionalEntityInputEngine<EmfEnumTableRowEntityWrapper<R, E>> {

	private final IDbEnumTable<R, E> targetTable;

	public EmfEnumTableRowInputEngine(IDbEnumTable<R, E> targetTable) {

		this.targetTable = targetTable;
	}

	@Override
	protected ITransaction createTransaction() {

		return new DbTransaction();
	}

	@Override
	protected EmfEnumTableRowEntityWrapper<R, E> getById(Integer id) {

		return EmfEnumTableRowEntityWrapper.wrap(targetTable.get(id));
	}

	/**
	 * Note: Ignores the pattern, so far. It is only considered in
	 * {@link #filterMatchingItems(String, Collection)}.
	 */
	@Override
	public Collection<EmfEnumTableRowEntityWrapper<R, E>> findMatchingItems(String pattern, int fetchOffset, int fetchSize) {

		ISqlSelect<R> select = targetTable.createSelect();

		// enforce ordering by all primary key fields
		targetTable.getPrimaryKey().getFields().forEach(select::orderBy);

		return select.stream(fetchOffset, fetchSize).map(EmfEnumTableRowEntityWrapper::wrap).collect(Collectors.toList());
	}

	@Override
	public Collection<EmfEnumTableRowEntityWrapper<R, E>> filterMatchingItems(String pattern, Collection<EmfEnumTableRowEntityWrapper<R, E>> matchingItems) {

		return matchingItems//
			.stream()
			.filter(item -> matches(item, pattern))
			.collect(Collectors.toList());
	}

	private boolean matches(EmfEnumTableRowEntityWrapper<R, E> item, String pattern) {

		return Optional//
			.ofNullable(getDisplayStringWithoutId(item))
			.map(Object::toString)
			.orElse("")
			.toLowerCase()
			.contains(pattern.toLowerCase());
	}
}
