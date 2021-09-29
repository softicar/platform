package com.softicar.platform.emf.predicate;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.emf.authorization.EmfTableRowFieldMapper;
import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import java.util.Collection;

public interface IEmfPredicate<R> {

	IDisplayString getTitle();

	boolean test(R tableRow);

	default IEmfPredicate<R> not() {

		return new EmfPredicateNot<>(this);
	}

	default IEmfPredicate<R> and(IEmfPredicate<R> predicate) {

		return new EmfPredicateAnd<>(this, predicate);
	}

	default IEmfPredicate<R> or(IEmfPredicate<R> predicate) {

		return new EmfPredicateOr<>(this, predicate);
	}

	default <F> IEmfPredicate<F> of(IEmfTableRowMapper<F, R> mapper) {

		return new EmfMappedTableRowPredicate<>(this, mapper);
	}

	default <F> IEmfPredicate<F> of(ISqlForeignRowField<F, R, ?> field) {

		return new EmfMappedTableRowPredicate<>(this, new EmfTableRowFieldMapper<>(field));
	}

	default void accept(IEmfPredicateVisitor<R> visitor) {

		visitor.visitAtom(this);
	}

	default IEmfPredicate<R> prefetchData(Collection<R> entities) {

		DevNull.swallow(entities);
		return this;
	}
}
