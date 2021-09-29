package com.softicar.platform.emf.action.factory;

import com.softicar.platform.emf.action.IEmfAction;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractEmfActionGenerator<R extends IEmfTableRow<R, ?>, A extends IEmfAction<R>> {

	private final Function<IEmfTable<R, ?, ?>, Collection<? extends IEmfActionFactory<R, A>>> factoriesFetcher;

	public AbstractEmfActionGenerator(Function<IEmfTable<R, ?, ?>, Collection<? extends IEmfActionFactory<R, A>>> factoriesFetcher) {

		this.factoriesFetcher = Objects.requireNonNull(factoriesFetcher);
	}

	public List<A> generateActions(R tableRow) {

		return factoriesFetcher//
			.apply(tableRow.table())
			.stream()
			.map(generator -> generator.apply(tableRow))
			.flatMap(Collection::stream)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}
}
