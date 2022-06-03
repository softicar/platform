package com.softicar.platform.core.module.access.module.instance;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.access.module.instance.actions.ModuleInstanceDetailsPopupAction;
import com.softicar.platform.core.module.access.module.instance.actions.ModuleInstanceInitializationAction;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
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

public class AGModuleInstanceTable extends EmfObjectTable<AGModuleInstance, SystemModuleInstance> {

	public AGModuleInstanceTable(IDbObjectTableBuilder<AGModuleInstance> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGModuleInstance, Integer, SystemModuleInstance> configuration) {

		configuration.setIcon(CoreImages.MODULE_INSTANCE);
		configuration.addValidator(ModuleInstanceValidator::new);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGModuleInstance, SystemModuleInstance> authorizer) {

		authorizer.setCreationPermission(CorePermissions.ACCESS_MANAGER);
		authorizer.setEditPermission(CorePermissions.SUPER_USER.toOtherEntityPermission());
		authorizer.setViewPermission(CorePermissions.SUPER_USER.toOtherEntityPermission());
	}

	@Override
	public void customizeActionSet(EmfActionSet<AGModuleInstance, SystemModuleInstance> actionSet) {

		actionSet.addManagementAction(new ModuleInstanceInitializationAction());
		actionSet.addManagementAction(new ModuleInstanceDetailsPopupAction());
		actionSet.addPrimaryAction(new ModuleInstanceInitializationAction());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGModuleInstance> attributes) {

		attributes//
			.editIndirectEntityAttribute(AGModuleInstance.MODULE_UUID)
			.setEntityLoader(() -> AGUuidBasedSourceCodeReferencePoints.getAll(IEmfModule.class))
			.setTitle(CoreI18n.MODULE_CLASS)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always())
			.setColumnHandlerFactory(EmfDataTableNonSortableColumnHandler::new);
		attributes//
			.addTransientAttribute(AGModuleInstance.TITLE_FIELD);
	}

	@Override
	public void customizeAttributeDefaultValues(EmfAttributeDefaultValueSet<AGModuleInstance, SystemModuleInstance> defaultValueSet) {

		defaultValueSet.setValue(AGModuleInstance.ACTIVE, true);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGModuleInstance> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGModuleInstanceLog.MODULE_INSTANCE, AGModuleInstanceLog.TRANSACTION)
			.addMapping(AGModuleInstance.ACTIVE, AGModuleInstanceLog.ACTIVE);
	}
}
