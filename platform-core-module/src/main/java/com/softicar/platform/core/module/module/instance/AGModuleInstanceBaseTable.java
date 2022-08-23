package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.actions.ModuleInstanceDetailsPopupAction;
import com.softicar.platform.core.module.module.instance.actions.ModuleInstanceInitializationAction;
import com.softicar.platform.core.module.uuid.AGUuidBasedSourceCodeReferencePoints;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableNonSortableColumnHandler;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfAttributeDefaultValueSet;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGModuleInstanceBaseTable extends EmfObjectTable<AGModuleInstanceBase, AGCoreModuleInstance> {

	public AGModuleInstanceBaseTable(IDbObjectTableBuilder<AGModuleInstanceBase> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGModuleInstanceBase, Integer, AGCoreModuleInstance> configuration) {

		configuration.setIcon(CoreImages.MODULE_INSTANCE);
		configuration.addValidator(ModuleInstanceBaseValidator::new);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGModuleInstanceBase, AGCoreModuleInstance> authorizer) {

		authorizer.setCreationPermission(CorePermissions.ADMINISTRATION);
		authorizer.setEditPermission(CoreModule.getModuleAdministation());
		authorizer.setViewPermission(CoreModule.getModuleAdministation());
	}

	@Override
	public void customizeActionSet(EmfActionSet<AGModuleInstanceBase, AGCoreModuleInstance> actionSet) {

		actionSet.addManagementAction(new ModuleInstanceInitializationAction());
		actionSet.addManagementAction(new ModuleInstanceDetailsPopupAction());
		actionSet.addPrimaryAction(new ModuleInstanceInitializationAction());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGModuleInstanceBase> attributes) {

		attributes//
			.editIndirectEntityAttribute(AGModuleInstanceBase.MODULE_UUID)
			.setEntityLoader(() -> AGUuidBasedSourceCodeReferencePoints.getAll(IEmfModule.class))
			.setTitle(CoreI18n.MODULE_CLASS)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always())
			.setColumnHandlerFactory(EmfDataTableNonSortableColumnHandler::new);
		attributes//
			.addTransientAttribute(AGModuleInstanceBase.TITLE_FIELD);
	}

	@Override
	public void customizeAttributeDefaultValues(EmfAttributeDefaultValueSet<AGModuleInstanceBase, AGCoreModuleInstance> defaultValueSet) {

		defaultValueSet.setValue(AGModuleInstanceBase.ACTIVE, true);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGModuleInstanceBase> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGModuleInstanceBaseLog.MODULE_INSTANCE_BASE, AGModuleInstanceBaseLog.TRANSACTION)
			.addMapping(AGModuleInstanceBase.ACTIVE, AGModuleInstanceBaseLog.ACTIVE);
	}
}
