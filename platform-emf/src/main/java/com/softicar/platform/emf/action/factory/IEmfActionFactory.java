package com.softicar.platform.emf.action.factory;

import com.softicar.platform.emf.action.IEmfAction;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.function.Function;

@FunctionalInterface
public interface IEmfActionFactory<R extends IEmfTableRow<R, ?>, A extends IEmfAction<R>> extends Function<R, Collection<A>> {

	// nothing
}
