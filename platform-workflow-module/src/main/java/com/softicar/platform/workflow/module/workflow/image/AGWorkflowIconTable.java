package com.softicar.platform.workflow.module.workflow.image;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.WorkflowImages;
import com.softicar.platform.workflow.module.WorkflowPermissions;

public class AGWorkflowIconTable extends EmfObjectTable<AGWorkflowIcon, AGWorkflowModuleInstance> {

	public AGWorkflowIconTable(IDbObjectTableBuilder<AGWorkflowIcon> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowIcon, Integer, AGWorkflowModuleInstance> configuration) {

		configuration.setScopeField(AGWorkflowIcon.MODULE_INSTANCE);
		configuration.setIcon(WorkflowImages.WORKFLOW_IMAGES);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowIcon> attributes) {

		attributes//
			.editAttribute(AGWorkflowIcon.ICON)
			.setDisplayFactoryByEntity(AGWorkflowIcon::getImage);
		attributes//
			.editAttribute(AGWorkflowIcon.NAME)
			.setPredicateMandatory(EmfPredicates.always());
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowIcon, AGWorkflowModuleInstance> authorizer) {

		authorizer//
			.setCreationPermission(WorkflowPermissions.ADMINISTRATION)
			.setEditPermission(WorkflowPermissions.ADMINISTRATION.of(AGWorkflowIcon.MODULE_INSTANCE))
			.setViewPermission(WorkflowPermissions.VIEW.of(AGWorkflowIcon.MODULE_INSTANCE));
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowIcon> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGWorkflowIconLog.WORKFLOW_ICON, AGWorkflowIconLog.TRANSACTION)
			.addMapping(AGWorkflowIcon.NAME, AGWorkflowIconLog.NAME)
			.addMapping(AGWorkflowIcon.ICON, AGWorkflowIconLog.ICON);
	}
}
