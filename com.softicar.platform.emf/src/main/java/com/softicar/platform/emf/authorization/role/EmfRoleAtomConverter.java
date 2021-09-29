package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.ArrayList;
import java.util.Collection;

public class EmfRoleAtomConverter<E> implements IEmfRoleVisitor<E> {

	private final Collection<IEmfRole<?>> roles;
	private final IEmfRole<E> role;

	public EmfRoleAtomConverter(IEmfRole<E> role) {

		this.roles = new ArrayList<>();
		this.role = role;
	}

	public Collection<IEmfRole<?>> convert() {

		role.accept(this);
		return roles;
	}

	@Override
	public void visitNobody() {

		roles.add(EmfRoles.nobody());
	}

	@Override
	public void visitAnybody() {

		roles.add(EmfRoles.anybody());
	}

	@Override
	public void visitAtom(IEmfRole<E> role) {

		roles.add(role);
	}

	@Override
	public void visitNot(IEmfRole<E> role) {

		role.accept(this);
	}

	@Override
	public void visitAny(Collection<IEmfRole<E>> roles) {

		for (IEmfRole<E> role: roles) {
			role.accept(this);
		}
	}

	@Override
	public void visitAll(Collection<IEmfRole<E>> roles) {

		for (IEmfRole<E> role: roles) {
			role.accept(this);
		}
	}

	@Override
	public void visitConditional(IEmfRole<E> role, IEmfPredicate<E> predicate) {

		role.accept(this);
	}

	@Override
	public void visitMapped(IEmfRole<?> role, IEmfTableRowMapper<?, ?> mapper) {

		roles.addAll(new EmfRoleAtomConverter<>(role).convert());
	}
}
