package com.softicar.platform.core.module.user;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public interface UserPredicates {

	IEmfPredicate<AGUser> ACTIVE = new EmfPredicate<>(//
		CoreI18n.ACTIVE,
		AGUser::isActive);

	IEmfPredicate<AGUser> NOT_ACTIVE = ACTIVE.not();
}
