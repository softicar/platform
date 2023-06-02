package com.softicar.platform.workflow.module.workflow.user.configuration.specific;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import java.util.Collection;

public class AGWorkflowSpecificUserConfigurationTable extends EmfObjectTable<AGWorkflowSpecificUserConfiguration, AGUser> {

	public AGWorkflowSpecificUserConfigurationTable(IDbObjectTableBuilder<AGWorkflowSpecificUserConfiguration> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowSpecificUserConfiguration, Integer, AGUser> configuration) {

		configuration.setScopeField(AGWorkflowSpecificUserConfiguration.USER);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowSpecificUserConfiguration> attributes) {

		attributes//
			.editEntityAttribute(AGWorkflowSpecificUserConfiguration.WORKFLOW)
			.setValueLoader(this::getVisibleWorkflows)
			.setImmutable(true);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowSpecificUserConfiguration> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGWorkflowSpecificUserConfigurationLog.CONFIGURATION, AGWorkflowSpecificUserConfigurationLog.TRANSACTION)
			.addMapping(AGWorkflowSpecificUserConfiguration.SUBSCRIBED, AGWorkflowSpecificUserConfigurationLog.SUBSCRIBED);
	}

	private Collection<AGWorkflow> getVisibleWorkflows(AGWorkflowSpecificUserConfiguration configuration) {

		var visibleWorkflowModuleInstances = AGWorkflowModuleInstance.loadAllActiveAndVisible(configuration.getUser());

		return AGWorkflow.TABLE//
			.createSelect()
			.where(AGWorkflow.ACTIVE)
			.where(AGWorkflow.MODULE_INSTANCE.isIn(visibleWorkflowModuleInstances))
			.list();
	}
}
