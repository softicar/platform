package com.softicar.platform.emf.predicate;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import java.util.Optional;

public class EmfMappedTableRowPredicate<R, F> implements IEmfPredicate<R> {

	private final IEmfPredicate<F> predicate;
	private final IEmfTableRowMapper<R, F> mapper;

	public EmfMappedTableRowPredicate(IEmfPredicate<F> predicate, IEmfTableRowMapper<R, F> mapper) {

		this.predicate = predicate;
		this.mapper = mapper;
	}

	@Override
	public IDisplayString getTitle() {

		return EmfI18n.ARG1_OF_ARG2.toDisplay(predicate.getTitle(), mapper.getTitle());
	}

	@Override
	public boolean test(R tableRow) {

		return Optional//
			.ofNullable(tableRow)
			.flatMap(mapper::apply)
			.map(targetEntity -> predicate.test(targetEntity))
			.orElse(false);
	}

	@Override
	public String toString() {

		return getTitle().toString();
	}

	@Override
	public void accept(IEmfPredicateVisitor<R> visitor) {

		visitor.visitMapped(predicate, mapper);
	}
}
