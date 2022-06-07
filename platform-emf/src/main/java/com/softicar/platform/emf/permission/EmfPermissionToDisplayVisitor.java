package com.softicar.platform.emf.permission;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.predicate.EmfPredicateToDisplayVisitor;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.Collection;

public class EmfPermissionToDisplayVisitor<E> implements IEmfPermissionVisitor<E> {

	private final DomBar display;
	private final IEmfPermission<E> permission;

	public EmfPermissionToDisplayVisitor(IEmfPermission<E> permission) {

		this.display = new DomBar();
		this.permission = permission;
	}

	public DomBar toDisplay() {

		permission.accept(this);
		return display;
	}

	@Override
	public void visitNobody() {

		display.appendText(EmfI18n.NOBODY);
	}

	@Override
	public void visitAnybody() {

		display.appendText(EmfI18n.ANYBODY);
	}

	@Override
	public void visitAtom(IEmfPermission<E> permission) {

		display.appendText(permission.getTitle());
	}

	@Override
	public void visitNot(IEmfPermission<E> permission) {

		display.appendNewChild(DomElementTag.B).appendText(EmfI18n.NOT.concat(" "));
		permission.accept(this);
	}

	@Override
	public void visitAny(Collection<IEmfPermission<E>> permissions) {

		display.appendText("(");
		boolean first = true;
		for (IEmfPermission<E> permission: permissions) {
			if (first) {
				first = false;
			} else {
				display.appendNewChild(DomElementTag.BR);
				display.appendNewChild(DomElementTag.B).appendText(EmfI18n.OR.enclose(" "));
			}
			permission.accept(this);
		}
		display.appendText(")");
	}

	@Override
	public void visitAll(Collection<IEmfPermission<E>> permissions) {

		display.appendText("(");
		boolean first = true;
		for (IEmfPermission<E> permission: permissions) {
			if (first) {
				first = false;
			} else {
				display.appendNewChild(DomElementTag.BR);
				display.appendNewChild(DomElementTag.B).appendText(EmfI18n.AND.enclose(" "));
			}
			permission.accept(this);
		}
		display.appendText(")");
	}

	@Override
	public void visitConditional(IEmfPermission<E> permission, IEmfPredicate<E> predicate) {

		permission.accept(this);
		display.appendNewChild(DomElementTag.B).appendText(EmfI18n.IF.enclose(" "));
		display.appendChild(new EmfPredicateToDisplayVisitor<>(predicate).toDisplay());
	}

	@Override
	public void visitMapped(IEmfPermission<?> permission, IEmfTableRowMapper<?, ?> mapper) {

		display.appendChild(new EmfPermissionToDisplayVisitor<>(permission).toDisplay());
		display.appendNewChild(DomElementTag.B).appendText(EmfI18n.OF.enclose(" "));
		display.appendText(mapper.getTitle());
	}
}
