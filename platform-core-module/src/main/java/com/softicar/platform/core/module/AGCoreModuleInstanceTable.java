package com.softicar.platform.core.module;

import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.module.instance.ModuleInstanceTable;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;
import com.softicar.platform.db.runtime.table.configuration.DbTableConfiguration;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGCoreModuleInstanceTable extends ModuleInstanceTable<AGCoreModuleInstance> {

	public AGCoreModuleInstanceTable(IDbSubObjectTableBuilder<AGCoreModuleInstance, AGModuleInstanceBase, Integer> builder) {

		super(builder, CoreModule.class);
	}

	@Override
	protected void customizeTableConfiguration(DbTableConfiguration<AGCoreModuleInstance, AGModuleInstanceBase> configuration) {

		configuration.setDataInitializer(new CoreModuleInstanceTableDataInitializer());
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGCoreModuleInstance, AGModuleInstanceBase, AGCoreModuleInstance> configuration) {

		configuration.setIcon(CoreImages.MODULE_INSTANCE);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGCoreModuleInstance, AGCoreModuleInstance> authorizer) {

		authorizer//
			.setCreationPermission(EmfPermissions.never())
			.setDeletePermission(EmfPermissions.never())
			.setEditPermission(CorePermissions.ADMINISTRATION)
			.setViewPermission(CorePermissions.ADMINISTRATION);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGCoreModuleInstance> attributes) {

		attributes//
			.editStringAttribute(AGCoreModuleInstance.PORTAL_PROTOCOL)
			.setAutoTrimming(true)
			.setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editStringAttribute(AGCoreModuleInstance.PORTAL_HOST)
			.setAutoTrimming(true)
			.setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editStringAttribute(AGCoreModuleInstance.PORTAL_APPLICATION)
			.setAutoTrimming(true)
			.setPredicateMandatory(EmfPredicates.always());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGCoreModuleInstance> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGCoreModuleInstanceLog.CORE_MODULE_INSTANCE, AGCoreModuleInstanceLog.TRANSACTION)//
			.addMapping(AGCoreModuleInstance.SYSTEM_USER, AGCoreModuleInstanceLog.SYSTEM_USER)
			.addMapping(AGCoreModuleInstance.PRIMARY_FILE_REPOSITORY, AGCoreModuleInstanceLog.PRIMARY_FILE_REPOSITORY)
			.addMapping(AGCoreModuleInstance.EMAIL_SERVER, AGCoreModuleInstanceLog.EMAIL_SERVER)
			.addMapping(AGCoreModuleInstance.SUPPORT_EMAIL_ADDRESS, AGCoreModuleInstanceLog.SUPPORT_EMAIL_ADDRESS)
			.addMapping(AGCoreModuleInstance.NO_REPLY_EMAIL_ADDRESS, AGCoreModuleInstanceLog.NO_REPLY_EMAIL_ADDRESS)
			.addMapping(AGCoreModuleInstance.PORTAL_PROTOCOL, AGCoreModuleInstanceLog.PORTAL_PROTOCOL)
			.addMapping(AGCoreModuleInstance.PORTAL_HOST, AGCoreModuleInstanceLog.PORTAL_HOST)
			.addMapping(AGCoreModuleInstance.PORTAL_APPLICATION, AGCoreModuleInstanceLog.PORTAL_APPLICATION)
			.addMapping(AGCoreModuleInstance.PORTAL_LOGO, AGCoreModuleInstanceLog.PORTAL_LOGO)
			.addMapping(AGCoreModuleInstance.SYSTEM_NAME, AGCoreModuleInstanceLog.SYSTEM_NAME)
			.addMapping(AGCoreModuleInstance.DEFAULT_LOCALIZATION, AGCoreModuleInstanceLog.DEFAULT_LOCALIZATION)
			.addMapping(AGCoreModuleInstance.TEST_SYSTEM, AGCoreModuleInstanceLog.TEST_SYSTEM);
	}
}
