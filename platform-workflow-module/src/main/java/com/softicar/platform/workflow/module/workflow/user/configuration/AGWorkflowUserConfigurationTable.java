package com.softicar.platform.workflow.module.workflow.user.configuration;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.UserInput;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.trait.table.EmfTraitTable;
import com.softicar.platform.workflow.module.WorkflowImages;

public class AGWorkflowUserConfigurationTable extends EmfTraitTable<AGWorkflowUserConfiguration, AGUser> {

	protected AGWorkflowUserConfigurationTable(IDbTableBuilder<AGWorkflowUserConfiguration, AGUser> builder) {

		super(builder);
	}

	// FIXME for the future (everybody should change their own configuration but only admins may change it for others)
	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowUserConfiguration, AGUser> authorizer) {

		authorizer//
			.setCreationPermission(EmfPermissions.always())
			.setViewPermission(EmfPermissions.always())
			.setEditPermission(EmfPermissions.always());
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowUserConfiguration, AGUser, AGUser> configuration) {

		configuration.setScopeField(AGWorkflowUserConfiguration.USER);
		configuration.addValidator(WorkflowUserConfigurationValidator::new);
		configuration.setIcon(WorkflowImages.USER);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowUserConfiguration> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowUserConfigurationLog.WORKFLOW_USER_CONFIGURATION, AGWorkflowUserConfigurationLog.TRANSACTION)//
			.addMapping(AGWorkflowUserConfiguration.EMAIL_NOTIFICATIONS_FOR_NEW_TASKS, AGWorkflowUserConfigurationLog.EMAIL_NOTIFICATIONS_FOR_NEW_TASKS)
			.addMapping(AGWorkflowUserConfiguration.SUBSTITUTE, AGWorkflowUserConfigurationLog.SUBSTITUTE)
			.addMapping(AGWorkflowUserConfiguration.SUBSTITUTE_FROM, AGWorkflowUserConfigurationLog.SUBSTITUTE_FROM)
			.addMapping(AGWorkflowUserConfiguration.SUBSTITUTE_TO, AGWorkflowUserConfigurationLog.SUBSTITUTE_TO);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowUserConfiguration> attributes) {

		attributes//
			.editAttribute(AGWorkflowUserConfiguration.SUBSTITUTE)
			.setInputFactory(UserInput::new);
		attributes//
			.editAttribute(AGWorkflowUserConfiguration.SUBSTITUTE_FROM)
			.setInputFactoryByEntity(WorkflowUserConfigurationDayInput::new)
			.setPredicateMandatory(WorkflowUserConfigurationPredicates.SUBSTITUTE_DEFINED);
		attributes//
			.editAttribute(AGWorkflowUserConfiguration.SUBSTITUTE_TO)
			.setInputFactoryByEntity(WorkflowUserConfigurationDayInput::new)
			.setPredicateMandatory(WorkflowUserConfigurationPredicates.SUBSTITUTE_DEFINED);
	}
}
