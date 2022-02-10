package com.softicar.platform.workflow.module.workflow.user.configuration;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.UserInput;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.attribute.dependency.EmfAttributeDependencyMap;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.trait.table.EmfTraitTable;
import com.softicar.platform.workflow.module.WorkflowImages;

public class AGWorkflowUserSettingsTable extends EmfTraitTable<AGWorkflowUserSettings, AGUser> {

	protected AGWorkflowUserSettingsTable(IDbTableBuilder<AGWorkflowUserSettings, AGUser> builder) {

		super(builder);
	}

	//FIXME for the future (everybody should change it's own but only admin may change it for everyone)
	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowUserSettings, AGUser> authorizer) {

		authorizer//
			.setCreationRole(EmfRoles.anybody())
			.setViewRole(EmfRoles.anybody())
			.setEditRole(EmfRoles.anybody());
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowUserSettings, AGUser, AGUser> configuration) {

		configuration.setScopeField(AGWorkflowUserSettings.USER);
		configuration.addValidator(WorkflowUserSettingsValidator::new);
		configuration.setIcon(WorkflowImages.USER);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowUserSettings> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowUserSettingsLog.WORKFLOW_USER_SETTINGS, AGWorkflowUserSettingsLog.TRANSACTION)//
			.addMapping(AGWorkflowUserSettings.EMAIL_NOTIFICATIONS_FOR_NEW_TASKS, AGWorkflowUserSettingsLog.EMAIL_NOTIFICATIONS_FOR_NEW_TASKS)
			.addMapping(AGWorkflowUserSettings.SUBSTITUTE, AGWorkflowUserSettingsLog.SUBSTITUTE)
			.addMapping(AGWorkflowUserSettings.SUBSTITUTE_FROM, AGWorkflowUserSettingsLog.SUBSTITUTE_FROM)
			.addMapping(AGWorkflowUserSettings.SUBSTITUTE_TO, AGWorkflowUserSettingsLog.SUBSTITUTE_TO);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowUserSettings> attributes) {

		attributes//
			.editAttribute(AGWorkflowUserSettings.SUBSTITUTE)
			.setInputFactory(UserInput::new);
		attributes//
			.editAttribute(AGWorkflowUserSettings.SUBSTITUTE_FROM)
			.setInputFactoryByEntity(WorkflowUserSettingsDayInput::new)
			.setPredicateMandatory(WorkflowUserSettingsPredicates.SUBSTITUTE_SET);
		attributes//
			.editAttribute(AGWorkflowUserSettings.SUBSTITUTE_TO)
			.setInputFactoryByEntity(WorkflowUserSettingsDayInput::new)
			.setPredicateMandatory(WorkflowUserSettingsPredicates.SUBSTITUTE_SET);
	}

	@Override
	public void customizeAttributeDependencies(EmfAttributeDependencyMap<AGWorkflowUserSettings> dependencyMap) {

		dependencyMap//
			.editAttribute(AGWorkflowUserSettings.SUBSTITUTE)
			.addDepender(AGWorkflowUserSettings.SUBSTITUTE_FROM)
			.addDepender(AGWorkflowUserSettings.SUBSTITUTE_TO);
	}
}
