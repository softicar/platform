package com.softicar.platform.workflow.module.workflow.user.configuration;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.emf.trait.IEmfTrait;
import java.util.List;

public class AGWorkflowUserSettings extends AGWorkflowUserSettingsGenerated implements IEmfTrait<AGWorkflowUserSettings, AGUser> {

	public static List<AGUser> loadAllUsersWithSubstitute(AGUser substitute) {

		return loadAllUsersWithSubstituteAndDay(substitute, Day.today());
	}

	public static List<AGUser> loadAllUsersWithSubstituteAndDay(AGUser substitute, Day day) {

		return Sql
			.from(AGWorkflowUserSettings.TABLE)
			.select(AGWorkflowUserSettings.USER)
			.where(AGWorkflowUserSettings.SUBSTITUTE.equal(substitute))
			.where(AGWorkflowUserSettings.SUBSTITUTE_FROM.lessEqual(day))
			.where(AGWorkflowUserSettings.SUBSTITUTE_TO.greaterEqual(day))
			.list();
	}
}
