package com.softicar.platform.emf.predicate;

import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import java.util.Collection;

public interface IEmfPredicateVisitor<E> {

	void visitNever();

	void visitAlways();

	void visitAnd(Collection<IEmfPredicate<E>> predicates);

	void visitOr(Collection<IEmfPredicate<E>> predicates);

	void visitNot(IEmfPredicate<E> predicate);

	void visitAtom(IEmfPredicate<E> predicate);

	<F> void visitMapped(IEmfPredicate<F> predicate, IEmfTableRowMapper<E, F> mapper);
}
