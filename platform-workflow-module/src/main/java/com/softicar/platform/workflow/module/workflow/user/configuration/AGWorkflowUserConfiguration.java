package com.softicar.platform.workflow.module.workflow.user.configuration;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.emf.trait.IEmfTrait;
import java.util.List;

public class AGWorkflowUserConfiguration extends AGWorkflowUserConfigurationGenerated implements IEmfTrait<AGWorkflowUserConfiguration, AGUser> {

	public static List<AGUser> loadAllActiveUsersWithSubstitute(AGUser user) {

		return loadAllActiveUsersWithSubstituteAndDay(user, Day.today());
	}

	public static List<AGUser> loadAllActiveUsersWithSubstituteAndDay(AGUser substitute, Day day) {

		return Sql
			.from(AGWorkflowUserConfiguration.TABLE)
			.select(AGWorkflowUserConfiguration.USER)
			.where(AGWorkflowUserConfiguration.SUBSTITUTE.equal(substitute))
			.where(AGWorkflowUserConfiguration.VALID_FROM.lessEqual(day))
			.where(AGWorkflowUserConfiguration.VALID_TO.greaterEqual(day))
			.list();
	}
}
