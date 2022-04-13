package com.softicar.platform.demo.module.tmp;

import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;

public class EmfTestModuleInstanceTable extends EmfObjectTable<EmfTestModuleInstance, SystemModuleInstance> {

	public EmfTestModuleInstanceTable(IDbObjectTableBuilder<EmfTestModuleInstance> builder) {

		super(builder);
	}

//	@Override
//	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGModuleInstance, Integer, SystemModuleInstance> configuration) {
//
//		configuration.setIcon(CoreImages.MODULE_INSTANCE);
//		configuration.addValidator(ModuleInstanceValidator::new);
//	}
//
//	@Override
//	public void customizeAuthorizer(EmfAuthorizer<AGModuleInstance, SystemModuleInstance> authorizer) {
//
//		authorizer.setCreationRole(CoreRoles.ACCESS_MANAGER);
//		authorizer.setEditRole(CoreRoles.SUPER_USER.toOtherEntityRole());
//		authorizer.setViewRole(CoreRoles.SUPER_USER.toOtherEntityRole());
//	}
//
//	@Override
//	public void customizeActionSet(EmfActionSet<AGModuleInstance, SystemModuleInstance> actionSet) {
//
//		actionSet.addManagementAction(new ModuleInstanceInitializationAction());
//		actionSet.addManagementAction(new ModuleInstanceDetailsPopupAction());
//		actionSet.addPrimaryAction(new ModuleInstanceInitializationAction());
//	}
//
//	@Override
//	public void customizeAttributeProperties(IEmfAttributeList<AGModuleInstance> attributes) {
//
//		attributes//
//			.editIndirectEntityAttribute(AGModuleInstance.MODULE_UUID)
//			.setEntityLoader(() -> AGUuidBasedSourceCodeReferencePoints.getAll(IEmfModule.class))
//			.setTitle(CoreI18n.MODULE_CLASS)
//			.setImmutable(true)
//			.setPredicateMandatory(EmfPredicates.always())
//			.setColumnHandlerFactory(EmfDataTableNonSortableColumnHandler::new);
//		attributes//
//			.addTransientAttribute(AGModuleInstance.TITLE_FIELD);
//	}
//
//	@Override
//	public void customizeAttributeDefaultValues(EmfAttributeDefaultValueSet<AGModuleInstance, SystemModuleInstance> defaultValueSet) {
//
//		defaultValueSet.setValue(AGModuleInstance.ACTIVE, true);
//	}
//
//	@Override
//	public void customizeLoggers(EmfChangeLoggerSet<AGModuleInstance> loggerSet) {
//
//		loggerSet//
//			.addPlainChangeLogger(AGModuleInstanceLog.MODULE_INSTANCE, AGModuleInstanceLog.TRANSACTION)
//			.addMapping(AGModuleInstance.ACTIVE, AGModuleInstanceLog.ACTIVE);
//	}
}
