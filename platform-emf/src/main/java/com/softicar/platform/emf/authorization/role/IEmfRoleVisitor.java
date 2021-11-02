package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.Collection;

public interface IEmfRoleVisitor<R> {

	void visitNobody();

	void visitAnybody();

	void visitAny(Collection<IEmfRole<R>> roles);

	void visitAll(Collection<IEmfRole<R>> roles);

	void visitNot(IEmfRole<R> role);

	void visitAtom(IEmfRole<R> role);

	void visitConditional(IEmfRole<R> role, IEmfPredicate<R> predicate);

	void visitMapped(IEmfRole<?> role, IEmfTableRowMapper<?, ?> mapper);
}
