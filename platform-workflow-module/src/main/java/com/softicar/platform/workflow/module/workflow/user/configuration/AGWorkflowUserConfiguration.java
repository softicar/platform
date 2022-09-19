package com.softicar.platform.workflow.module.workflow.user.configuration;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.emf.trait.IEmfTrait;
import java.util.List;

public class AGWorkflowUserConfiguration extends AGWorkflowUserConfigurationGenerated implements IEmfTrait<AGWorkflowUserConfiguration, AGUser> {

	public static List<AGUser> loadAllUsersWithSubstitute(AGUser substitute) {

		return loadAllUsersWithSubstituteAndDay(substitute, Day.today());
	}

	public static List<AGUser> loadAllUsersWithSubstituteAndDay(AGUser substitute, Day day) {

		return Sql
			.from(AGWorkflowUserConfiguration.TABLE)
			.select(AGWorkflowUserConfiguration.USER)
			.where(AGWorkflowUserConfiguration.SUBSTITUTE.isEqual(substitute))
			.where(AGWorkflowUserConfiguration.SUBSTITUTE_FROM.isLessEqual(day))
			.where(AGWorkflowUserConfiguration.SUBSTITUTE_TO.isGreaterEqual(day))
			.list();
	}
}
