package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class EmfConditionalRole<R> extends AbstractEmfRole<R> {

	private final IEmfRole<R> role;
	private final IEmfPredicate<R> predicate;

	public EmfConditionalRole(IEmfRole<R> role, IEmfPredicate<R> predicate) {

		this.role = role;
		this.predicate = predicate;
	}

	public IEmfRole<R> getRole() {

		return role;
	}

	public IEmfPredicate<R> getPredicate() {

		return predicate;
	}

	@Override
	public IDisplayString getTitle() {

		return role.getTitle();
	}

	@Override
	public boolean test(R tableRow, IBasicUser user) {

		try {
			return predicate.test(tableRow) && role.test(tableRow, user);
		} catch (Exception exception) {
			logEvaluationException(exception);
			return false;
		}
	}

	@Override
	public String toString() {

		return getTitle().toString() + " [" + predicate.getTitle().toString() + "]";
	}

	@Override
	public void accept(IEmfRoleVisitor<R> visitor) {

		visitor.visitConditional(role, predicate);
	}
}
