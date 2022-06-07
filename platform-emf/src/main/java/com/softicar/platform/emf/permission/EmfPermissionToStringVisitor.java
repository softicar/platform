package com.softicar.platform.emf.permission;

import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.predicate.EmfPredicateToStringVisitor;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.Collection;

public class EmfPermissionToStringVisitor<E> implements IEmfPermissionVisitor<E> {

	private final StringBuilder title;
	private final IEmfPermission<E> permission;

	public EmfPermissionToStringVisitor(IEmfPermission<E> permission) {

		this.title = new StringBuilder();
		this.permission = permission;
	}

	@Override
	public String toString() {

		permission.accept(this);
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
	public void visitAtom(IEmfPermission<E> permission) {

		title.append(permission.getTitle().toString());
	}

	@Override
	public void visitNot(IEmfPermission<E> permission) {

		title.append(EmfI18n.NOT.concat(" "));
		permission.accept(this);
	}

	@Override
	public void visitAny(Collection<IEmfPermission<E>> permissions) {

		title.append("(");
		boolean first = true;
		for (IEmfPermission<E> permission: permissions) {
			if (first) {
				first = false;
			} else {
				title.append(" " + EmfI18n.OR + " ");
			}
			permission.accept(this);
		}
		title.append(")");
	}

	@Override
	public void visitAll(Collection<IEmfPermission<E>> permissions) {

		title.append("(");
		boolean first = true;
		for (IEmfPermission<E> permission: permissions) {
			if (first) {
				first = false;
			} else {
				this.title.append(" " + EmfI18n.AND + " ");
			}
			permission.accept(this);
		}
		title.append(")");
	}

	@Override
	public void visitConditional(IEmfPermission<E> permission, IEmfPredicate<E> predicate) {

		permission.accept(this);
		title.append(" " + EmfI18n.IF + " ");
		title.append(new EmfPredicateToStringVisitor<>(predicate).toString());
	}

	@Override
	public void visitMapped(IEmfPermission<?> permission, IEmfTableRowMapper<?, ?> mapper) {

		title.append(new EmfPermissionToStringVisitor<>(permission).toString());
		title.append(" " + EmfI18n.OF + " ");
		title.append(mapper.getTitle().toString());
	}
}
