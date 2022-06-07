package com.softicar.platform.emf.permission;

import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.Collection;

public interface IEmfPermissionVisitor<R> {

	void visitNobody();

	void visitAnybody();

	void visitAny(Collection<IEmfPermission<R>> permissions);

	void visitAll(Collection<IEmfPermission<R>> permissions);

	void visitNot(IEmfPermission<R> permission);

	void visitAtom(IEmfPermission<R> permission);

	void visitConditional(IEmfPermission<R> permission, IEmfPredicate<R> predicate);

	void visitMapped(IEmfPermission<?> permission, IEmfTableRowMapper<?, ?> mapper);
}
