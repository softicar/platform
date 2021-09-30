package com.softicar.platform.workflow.module.workflow.substitute;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.UserInput;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfAttributeDefaultValueSet;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.trait.table.EmfTraitTable;
import com.softicar.platform.workflow.module.WorkflowImages;

public class AGWorkflowSubstituteTable extends EmfTraitTable<AGWorkflowSubstitute, AGUser> {

	public AGWorkflowSubstituteTable(IDbTableBuilder<AGWorkflowSubstitute, AGUser> builder) {

		super(builder);
	}

	@Override
	//FIXME for the future (everybody should change it's own but only admin may change it for everyone)
	public void customizeAuthorizer(EmfAuthorizer<AGWorkflowSubstitute, AGUser> authorizer) {

		authorizer//
			.setCreationRole(EmfRoles.anybody())
			.setViewRole(EmfRoles.anybody())
			.setEditRole(EmfRoles.anybody());
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGWorkflowSubstitute, AGUser, AGUser> configuration) {

		configuration.setScopeField(AGWorkflowSubstitute.USER);
		configuration.addValidator(WorkflowSubstituteValidator::new);
		configuration.setIcon(WorkflowImages.USER);
	}

	@Override
	public void customizeAttributeDefaultValues(EmfAttributeDefaultValueSet<AGWorkflowSubstitute, AGUser> defaultValueSet) {

		super.customizeAttributeDefaultValues(defaultValueSet);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGWorkflowSubstitute> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGWorkflowSubstituteLog.WORKFLOW_SUBSTITUTE, AGWorkflowSubstituteLog.TRANSACTION)//
			.addMapping(AGWorkflowSubstitute.ACTIVE, AGWorkflowSubstituteLog.ACTIVE)
			.addMapping(AGWorkflowSubstitute.SUBSTITUTE, AGWorkflowSubstituteLog.SUBSTITUTE)
			.addMapping(AGWorkflowSubstitute.VALID_FROM, AGWorkflowSubstituteLog.VALID_FROM)
			.addMapping(AGWorkflowSubstitute.VALID_TO, AGWorkflowSubstituteLog.VALID_TO);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGWorkflowSubstitute> attributes) {

		attributes//
			.editAttribute(AGWorkflowSubstitute.SUBSTITUTE)
			.setInputFactory(UserInput::new)
			.setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editAttribute(AGWorkflowSubstitute.VALID_FROM)
			.setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editAttribute(AGWorkflowSubstitute.VALID_TO)
			.setPredicateMandatory(EmfPredicates.always());
	}
}
