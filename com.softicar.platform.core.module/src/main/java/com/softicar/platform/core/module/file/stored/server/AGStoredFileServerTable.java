package com.softicar.platform.core.module.file.stored.server;

import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGStoredFileServerTable extends EmfObjectTable<AGStoredFileServer, SystemModuleInstance> {

	public AGStoredFileServerTable(IDbObjectTableBuilder<AGStoredFileServer> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGStoredFileServer, Integer, SystemModuleInstance> configuration) {

		configuration.setIcon(CoreImages.STORED_FILE_SERVER);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGStoredFileServer, SystemModuleInstance> authorizer) {

		authorizer.setEditRole(CoreRoles.SUPER_USER.toOtherEntityRole());
		authorizer.setCreationRole(CoreRoles.SUPER_USER.toOtherEntityRole());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGStoredFileServer> attributes) {

		attributes.editAttribute(AGStoredFileServer.URL).setPredicateMandatory(EmfPredicates.always());
		attributes.editAttribute(AGStoredFileServer.DOMAIN).setPredicateMandatory(EmfPredicates.always());
		attributes.editAttribute(AGStoredFileServer.USERNAME).setPredicateMandatory(EmfPredicates.always());
		attributes.editAttribute(AGStoredFileServer.PASSWORD).setPredicateMandatory(EmfPredicates.always());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGStoredFileServer> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGStoredFileServerLog.STORED_FILE_SERVER, AGStoredFileServerLog.TRANSACTION)
			.addMapping(AGStoredFileServer.ACTIVE, AGStoredFileServerLog.ACTIVE)
			.addMapping(AGStoredFileServer.URL, AGStoredFileServerLog.URL)
			.addMapping(AGStoredFileServer.DOMAIN, AGStoredFileServerLog.DOMAIN)
			.addMapping(AGStoredFileServer.USERNAME, AGStoredFileServerLog.USERNAME)
			.addMapping(AGStoredFileServer.PASSWORD, AGStoredFileServerLog.PASSWORD);
	}
}
