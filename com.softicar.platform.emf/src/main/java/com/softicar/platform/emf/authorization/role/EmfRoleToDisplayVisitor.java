package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import com.softicar.platform.emf.predicate.EmfPredicateToDisplayVisitor;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.Collection;

public class EmfRoleToDisplayVisitor<E> implements IEmfRoleVisitor<E> {

	private final DomBar display;
	private final IEmfRole<E> role;

	public EmfRoleToDisplayVisitor(IEmfRole<E> role) {

		this.display = new DomBar();
		this.role = role;
	}

	public DomBar toDisplay() {

		role.accept(this);
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
	public void visitAtom(IEmfRole<E> role) {

		display.appendText(role.getTitle());
	}

	@Override
	public void visitNot(IEmfRole<E> role) {

		display.appendNewChild(DomElementTag.B).appendText(EmfI18n.NOT.concat(" "));
		role.accept(this);
	}

	@Override
	public void visitAny(Collection<IEmfRole<E>> roles) {

		display.appendText("(");
		boolean first = true;
		for (IEmfRole<E> role: roles) {
			if (first) {
				first = false;
			} else {
				display.appendNewChild(DomElementTag.BR);
				display.appendNewChild(DomElementTag.B).appendText(EmfI18n.OR.enclose(" "));
			}
			role.accept(this);
		}
		display.appendText(")");
	}

	@Override
	public void visitAll(Collection<IEmfRole<E>> roles) {

		display.appendText("(");
		boolean first = true;
		for (IEmfRole<E> role: roles) {
			if (first) {
				first = false;
			} else {
				display.appendNewChild(DomElementTag.BR);
				display.appendNewChild(DomElementTag.B).appendText(EmfI18n.AND.enclose(" "));
			}
			role.accept(this);
		}
		display.appendText(")");
	}

	@Override
	public void visitConditional(IEmfRole<E> role, IEmfPredicate<E> predicate) {

		role.accept(this);
		display.appendNewChild(DomElementTag.B).appendText(EmfI18n.IF.enclose(" "));
		display.appendChild(new EmfPredicateToDisplayVisitor<>(predicate).toDisplay());
	}

	@Override
	public void visitMapped(IEmfRole<?> role, IEmfTableRowMapper<?, ?> mapper) {

		display.appendChild(new EmfRoleToDisplayVisitor<>(role).toDisplay());
		display.appendNewChild(DomElementTag.B).appendText(EmfI18n.OF.enclose(" "));
		display.appendText(mapper.getTitle());
	}
}
