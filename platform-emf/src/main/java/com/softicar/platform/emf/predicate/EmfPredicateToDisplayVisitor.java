package com.softicar.platform.emf.predicate;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import java.util.Collection;

public class EmfPredicateToDisplayVisitor<E> implements IEmfPredicateVisitor<E> {

	private final DomBar display;
	private final IEmfPredicate<E> predicate;

	public EmfPredicateToDisplayVisitor(IEmfPredicate<E> predicate) {

		this.display = new DomBar();
		this.predicate = predicate;
	}

	public DomBar toDisplay() {

		predicate.accept(this);
		return display;
	}

	@Override
	public void visitAtom(IEmfPredicate<E> predicate) {

		display.appendText(predicate.getTitle());
	}

	@Override
	public void visitNever() {

		display.appendText(EmfI18n.NEVER);
	}

	@Override
	public void visitAlways() {

		display.appendText(EmfI18n.ALWAYS);
	}

	@Override
	public void visitAnd(Collection<IEmfPredicate<E>> predicates) {

		display.appendText("(");
		boolean first = true;
		for (IEmfPredicate<E> predicate: predicates) {
			if (first) {
				first = false;
			} else {
				display.appendNewChild(DomElementTag.BR);
				display.appendNewChild(DomElementTag.B).appendText(EmfI18n.AND.enclose(" "));
			}
			predicate.accept(this);
		}
		display.appendText(")");
	}

	@Override
	public void visitOr(Collection<IEmfPredicate<E>> predicates) {

		display.appendText("(");
		boolean first = true;
		for (IEmfPredicate<E> predicate: predicates) {
			if (first) {
				first = false;
			} else {
				display.appendNewChild(DomElementTag.BR);
				display.appendNewChild(DomElementTag.B).appendText(EmfI18n.OR.enclose(" "));
			}
			predicate.accept(this);
		}
		display.appendText(")");
	}

	@Override
	public void visitNot(IEmfPredicate<E> predicate) {

		display//
			.appendNewChild(DomElementTag.B)
			.appendText(EmfI18n.NOT.concat(" "));
		predicate.accept(this);
	}

	@Override
	public <F> void visitMapped(IEmfPredicate<F> predicate, IEmfTableRowMapper<E, F> mapper) {

		display.appendChild(new EmfPredicateToDisplayVisitor<>(predicate).toDisplay());
		display.appendNewChild(DomElementTag.B).appendText(EmfI18n.OF.enclose(" "));
		display.appendText(mapper.getTitle());
	}
}
