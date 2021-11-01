package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import com.softicar.platform.emf.predicate.EmfPredicateToStringVisitor;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.Collection;

public class EmfRoleToStringVisitor<E> implements IEmfRoleVisitor<E> {

	private final StringBuilder title;
	private final IEmfRole<E> role;

	public EmfRoleToStringVisitor(IEmfRole<E> role) {

		this.title = new StringBuilder();
		this.role = role;
	}

	@Override
	public String toString() {

		role.accept(this);
		return title.toString();
	}

	@Override
	public void visitNobody() {

		title.append(EmfI18n.NOBODY);
	}

	@Override
	public void visitAnybody() {

		title.append(EmfI18n.ANYBODY);

	}

	@Override
	public void visitAtom(IEmfRole<E> role) {

		title.append(role.getTitle().toString());
	}

	@Override
	public void visitNot(IEmfRole<E> role) {

		title.append(EmfI18n.NOT.concat(" "));
		role.accept(this);
	}

	@Override
	public void visitAny(Collection<IEmfRole<E>> roles) {

		title.append("(");
		boolean first = true;
		for (IEmfRole<E> role: roles) {
			if (first) {
				first = false;
			} else {
				title.append(" " + EmfI18n.OR + " ");
			}
			role.accept(this);
		}
		title.append(")");
	}

	@Override
	public void visitAll(Collection<IEmfRole<E>> roles) {

		title.append("(");
		boolean first = true;
		for (IEmfRole<E> role: roles) {
			if (first) {
				first = false;
			} else {
				this.title.append(" " + EmfI18n.AND + " ");
			}
			role.accept(this);
		}
		title.append(")");
	}

	@Override
	public void visitConditional(IEmfRole<E> role, IEmfPredicate<E> predicate) {

		role.accept(this);
		title.append(" " + EmfI18n.IF + " ");
		title.append(new EmfPredicateToStringVisitor<>(predicate).toString());
	}

	@Override
	public void visitMapped(IEmfRole<?> role, IEmfTableRowMapper<?, ?> mapper) {

		title.append(new EmfRoleToStringVisitor<>(role).toString());
		title.append(" " + EmfI18n.OF + " ");
		title.append(mapper.getTitle().toString());
	}
}
