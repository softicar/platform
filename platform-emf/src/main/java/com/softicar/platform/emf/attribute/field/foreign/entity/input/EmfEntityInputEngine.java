package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.interfaces.Predicates;
import com.softicar.platform.common.core.transaction.ITransaction;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.dom.elements.input.auto.entity.AbstractDomAutoCompleteTransactionalEntityInputEngine;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmfEntityInputEngine<T extends IEmfEntity<T, ?>> extends AbstractDomAutoCompleteTransactionalEntityInputEngine<T> {

	private final IEmfEntityTable<T, ?, ?> targetTable;
	private final Predicate<T> validator;

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

		// order by primary key fields to ensure determinism
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

	private static <T extends IEmfTableRow<T, ?>, S extends IEntity> boolean hasScope(IEmfTable<T, ?, S> targetTable, T targetEntity, S scope) {

		Optional<S> scopeOfEntity = targetTable.getScope(targetEntity);
		return scopeOfEntity.isPresent() && Objects.equals(scopeOfEntity.get().getId(), scope.getId());
	}
}
