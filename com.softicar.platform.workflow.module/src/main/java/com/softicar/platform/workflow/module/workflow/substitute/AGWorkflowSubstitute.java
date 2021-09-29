package com.softicar.platform.workflow.module.workflow.substitute;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.trait.IEmfTrait;
import java.util.List;

public class AGWorkflowSubstitute extends AGWorkflowSubstituteGenerated implements IEmfTrait<AGWorkflowSubstitute, AGUser> {

	public boolean validFromBeforeEqualValidToOrNull() {

		if (getValidFrom() == null || getValidTo() == null) {
			return true;
		}
		return getValidFrom().compareTo(getValidTo()) <= 0;
	}

	public static List<AGWorkflowSubstitute> loadAllActiveForUser(AGUser user) {

		return loadAllActiveForUserAndDay(user, Day.today());
	}

	public static List<AGWorkflowSubstitute> loadAllActiveForUserAndDay(AGUser user, Day day) {

		return AGWorkflowSubstitute.TABLE
			.createSelect()
			.where(AGWorkflowSubstitute.ACTIVE)
			.where(AGWorkflowSubstitute.SUBSTITUTE.equal(user))
			.where(AGWorkflowSubstitute.VALID_FROM.lessEqual(day))
			.where(AGWorkflowSubstitute.VALID_TO.greaterEqual(day))
			.list();
	}
}
