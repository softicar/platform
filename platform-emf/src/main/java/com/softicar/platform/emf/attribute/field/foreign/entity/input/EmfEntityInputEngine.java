package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.interfaces.Predicates;
import com.softicar.platform.common.core.transaction.ITransaction;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.dom.elements.input.auto.entity.AbstractDomAutoCompleteTransactionalEntityInputEngine;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmfEntityInputEngine<T extends IEmfEntity<T, ?>> extends AbstractDomAutoCompleteTransactionalEntityInputEngine<T> {

	private final IEmfEntityTable<T, ?, ?> targetTable;
	private final Predicate<T> validator;
	private final List<Ordering> orderingList;

	public <S extends IEmfTableRow<S, ?>> EmfEntityInputEngine(S sourceEntity, IEmfEntityTable<T, ?, ?> targetTable, BiPredicate<S, T> validator) {

		this(targetTable, (Predicate<T>) targetEntity -> validator.test(sourceEntity, targetEntity));
	}

	public <S extends IEntity> EmfEntityInputEngine(IEmfEntityTable<T, ?, S> targetTable, S scope) {

		this(targetTable, (Predicate<T>) targetEntity -> hasScope(targetTable, targetEntity, scope));
	}

	public EmfEntityInputEngine(IEmfEntityTable<T, ?, ?> targetTable) {

		this(targetTable, Predicates.always());
	}

	private EmfEntityInputEngine(IEmfEntityTable<T, ?, ?> targetTable, Predicate<T> validator) {

		this.targetTable = targetTable;
		this.validator = validator;
		this.orderingList = new ArrayList<>();
	}

	@Override
	public T getById(Integer id) {

		return Optional//
			.ofNullable(targetTable.get(id))
			.filter(this::isValid)
			.orElse(null);
	}

	/**
	 * Note: Ignores the pattern, so far. It is only considered in
	 * {@link #filterMatchingItems(String, Collection)}.
	 */
	@Override
	public Collection<T> findMatchingItems(String pattern, int fetchOffset, int fetchSize) {

		ISqlSelect<T> select = targetTable.createSelect();

		// apply explicit ordering
		for (Ordering ordering: orderingList) {
			if (ordering.isAscending()) {
				select.orderBy(ordering.getField());
			} else {
				select.orderDescendingBy(ordering.getField());
			}
		}

		// enforce supplemented ordering by all primary key fields (though any unique field would do)
		targetTable.getPrimaryKey().getFields().forEach(select::orderBy);

		return select.list(fetchOffset, fetchSize);
	}

	@Override
	public Collection<T> filterMatchingItems(String pattern, Collection<T> matchingItems) {

		return matchingItems//
			.stream()
			.filter(item -> matches(item, pattern))
			.filter(this::isValid)
			.collect(Collectors.toList());
	}

	@Override
	protected ITransaction createTransaction() {

		return new DbTransaction();
	}

	@SuppressWarnings("unchecked")
	public EmfEntityInputEngine<T> addOrderBy(IDbField<T, ?>...fields) {

		return addOrderBy(Arrays.asList(fields));
	}

	public EmfEntityInputEngine<T> addOrderBy(Collection<IDbField<T, ?>> fields) {

		for (IDbField<T, ?> field: fields) {
			addOrderBy(field);
		}
		return this;
	}

	public EmfEntityInputEngine<T> addOrderBy(IDbField<T, ?> field) {

		return addOrderBy(field, true);
	}

	@SuppressWarnings("unchecked")
	public EmfEntityInputEngine<T> addOrderDescendingBy(IDbField<T, ?>...fields) {

		return addOrderDescendingBy(Arrays.asList(fields));
	}

	public EmfEntityInputEngine<T> addOrderDescendingBy(Collection<IDbField<T, ?>> fields) {

		for (IDbField<T, ?> field: fields) {
			addOrderDescendingBy(field);
		}
		return this;
	}

	public EmfEntityInputEngine<T> addOrderDescendingBy(IDbField<T, ?> field) {

		return addOrderBy(field, false);
	}

	private EmfEntityInputEngine<T> addOrderBy(IDbField<T, ?> field, boolean ascending) {

		Objects.requireNonNull(field);
		this.orderingList.add(new Ordering(field, ascending));
		return this;
	}

	private boolean matches(T item, String pattern) {

		return Optional//
			.ofNullable(getDisplayStringWithoutId(item))
			.map(Object::toString)
			.orElse("")
			.toLowerCase()
			.contains(pattern.toLowerCase());
	}

	private boolean isValid(T targetEntity) {

		return validator.test(targetEntity);
	}

	private class Ordering {

		private final IDbField<T, ?> field;
		private final boolean ascending;

		public Ordering(IDbField<T, ?> field, boolean ascending) {

			this.field = field;
			this.ascending = ascending;
		}

		public IDbField<T, ?> getField() {

			return field;
		}

		public boolean isAscending() {

			return ascending;
		}
	}

	private static <T extends IEmfTableRow<T, ?>, S extends IEntity> boolean hasScope(IEmfTable<T, ?, S> targetTable, T targetEntity, S scope) {

		Optional<S> scopeOfEntity = targetTable.getScope(targetEntity);
		return scopeOfEntity.isPresent() && Objects.equals(scopeOfEntity.get().getId(), scope.getId());
	}
}
