package com.softicar.platform.emf.predicate;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import java.util.Collection;

public class EmfPredicateToStringVisitor<E> implements IEmfPredicateVisitor<E> {

	private final DisplayString title;
	private final IEmfPredicate<E> predicate;

	public EmfPredicateToStringVisitor(IEmfPredicate<E> predicate) {

		this.title = new DisplayString();
		this.predicate = predicate;
	}

	@Override
	public String toString() {

		predicate.accept(this);
		return title.toString();
	}

	@Override
	public void visitAtom(IEmfPredicate<E> predicate) {

		title.append(predicate.getTitle().toString());
	}

	@Override
	public void visitNever() {

		title.append(EmfI18n.NEVER);
	}

	@Override
	public void visitAlways() {

		title.append(EmfI18n.ALWAYS);
	}

	@Override
	public void visitAnd(Collection<IEmfPredicate<E>> predicates) {

		title.append("(");
		boolean first = true;
		for (IEmfPredicate<E> predicate: predicates) {
			if (first) {
				first = false;
			} else {
				title.append(EmfI18n.AND.enclose(" "));
			}
			predicate.accept(this);
		}
		title.append(")");
	}

	@Override
	public void visitOr(Collection<IEmfPredicate<E>> predicates) {

		title.append("(");
		boolean first = true;
		for (IEmfPredicate<E> predicate: predicates) {
			if (first) {
				first = false;
			} else {
				title.append(EmfI18n.OR.enclose(" "));
			}
			predicate.accept(this);
		}
		title.append(")");
	}

	@Override
	public void visitNot(IEmfPredicate<E> predicate) {

		title.append(EmfI18n.NOT).append(" ");
		predicate.accept(this);
	}

	@Override
	public <F> void visitMapped(IEmfPredicate<F> predicate, IEmfTableRowMapper<E, F> mapper) {

		title.append(new EmfPredicateToStringVisitor<>(predicate).toString());
		title.append(EmfI18n.OF.enclose(" "));
		title.append(mapper.getTitle().toString());
	}
}
