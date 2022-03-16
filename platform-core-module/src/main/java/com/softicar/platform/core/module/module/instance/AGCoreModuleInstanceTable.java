package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.runtime.table.configuration.DbTableConfiguration;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGCoreModuleInstanceTable extends EmfObjectTable<AGCoreModuleInstance, SystemModuleInstance> {

	public AGCoreModuleInstanceTable(IDbObjectTableBuilder<AGCoreModuleInstance> builder) {

		super(builder);
	}

	@Override
	protected void customizeTableConfiguration(DbTableConfiguration<AGCoreModuleInstance, Integer> configuration) {

		configuration.setDataInitializer(new CoreModuleInstanceTableDataInitializer());
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGCoreModuleInstance, Integer, SystemModuleInstance> configuration) {

		configuration.setIcon(CoreImages.MODULE_INSTANCE);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGCoreModuleInstance, SystemModuleInstance> authorizer) {

		authorizer//
			.setCreationRole(EmfRoles.nobody())
			.setDeleteRole(EmfRoles.nobody())
			.setEditRole(CoreRoles.SUPER_USER.toOtherEntityRole())
			.setViewRole(CoreRoles.SUPER_USER.toOtherEntityRole());
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
			.addMapping(AGCoreModuleInstance.PRIMARY_FILE_SERVER, AGCoreModuleInstanceLog.PRIMARY_FILE_SERVER)
			.addMapping(AGCoreModuleInstance.EMAIL_SERVER, AGCoreModuleInstanceLog.EMAIL_SERVER)
			.addMapping(AGCoreModuleInstance.SUPPORT_EMAIL_ADDRESS, AGCoreModuleInstanceLog.SUPPORT_EMAIL_ADDRESS)
			.addMapping(AGCoreModuleInstance.NO_REPLY_EMAIL_ADDRESS, AGCoreModuleInstanceLog.NO_REPLY_EMAIL_ADDRESS)
			.addMapping(AGCoreModuleInstance.PORTAL_PROTOCOL, AGCoreModuleInstanceLog.PORTAL_PROTOCOL)
			.addMapping(AGCoreModuleInstance.PORTAL_HOST, AGCoreModuleInstanceLog.PORTAL_HOST)
			.addMapping(AGCoreModuleInstance.PORTAL_APPLICATION, AGCoreModuleInstanceLog.PORTAL_APPLICATION)
			.addMapping(AGCoreModuleInstance.PORTAL_LOGO, AGCoreModuleInstanceLog.PORTAL_LOGO)
			.addMapping(AGCoreModuleInstance.DEFAULT_LOCALIZATION, AGCoreModuleInstanceLog.DEFAULT_LOCALIZATION)
			.addMapping(AGCoreModuleInstance.TEST_SYSTEM, AGCoreModuleInstanceLog.TEST_SYSTEM);
	}
}
