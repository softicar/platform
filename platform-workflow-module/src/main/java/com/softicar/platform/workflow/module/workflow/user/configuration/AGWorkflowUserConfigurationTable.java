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

public class AGWorkflowUserConfigurationTable extends EmfTraitTable<AGWorkflowUserConfiguration, AGUser> {

	protected AGWorkflowUserConfigurationTable(IDbTableBuilder<AGWorkflowUserConfiguration, AGUser> builder) {

		super(builder);
	}

	//FIXME for the future (everybody should change it's own but only admin may change it for everyone)
	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowUserConfiguration, AGUser> authorizer) {

		authorizer//
			.setCreationRole(EmfRoles.anybody())
			.setViewRole(EmfRoles.anybody())
			.setEditRole(EmfRoles.anybody());
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
			.addMapping(AGWorkflowUserConfiguration.NOTIFY, AGWorkflowUserConfigurationLog.NOTIFY)
			.addMapping(AGWorkflowUserConfiguration.SUBSTITUTE, AGWorkflowUserConfigurationLog.SUBSTITUTE)
			.addMapping(AGWorkflowUserConfiguration.VALID_FROM, AGWorkflowUserConfigurationLog.VALID_FROM)
			.addMapping(AGWorkflowUserConfiguration.VALID_TO, AGWorkflowUserConfigurationLog.VALID_TO);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowUserConfiguration> attributes) {

		attributes//
			.editAttribute(AGWorkflowUserConfiguration.SUBSTITUTE)
			.setInputFactory(UserInput::new);
		attributes//
			.editAttribute(AGWorkflowUserConfiguration.VALID_FROM)
			.setInputFactoryByEntity(WorkflowUserConfigurationDayInput::new)
			.setPredicateMandatory(WorkflowUserConfigurationPredicates.SUBSTITUTE_SET);
		attributes//
			.editAttribute(AGWorkflowUserConfiguration.VALID_TO)
			.setInputFactoryByEntity(WorkflowUserConfigurationDayInput::new)
			.setPredicateMandatory(WorkflowUserConfigurationPredicates.SUBSTITUTE_SET);
	}

	@Override
	public void customizeAttributeDependencies(EmfAttributeDependencyMap<AGWorkflowUserConfiguration> dependencyMap) {

		dependencyMap//
			.editAttribute(AGWorkflowUserConfiguration.SUBSTITUTE)
			.addDepender(AGWorkflowUserConfiguration.VALID_FROM)
			.addDepender(AGWorkflowUserConfiguration.VALID_TO);
	}
}
