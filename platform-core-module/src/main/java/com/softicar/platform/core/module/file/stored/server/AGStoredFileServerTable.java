package com.softicar.platform.core.module.file.stored.server;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGStoredFileServerTable extends EmfObjectTable<AGStoredFileServer, AGCoreModuleInstance> {

	public AGStoredFileServerTable(IDbObjectTableBuilder<AGStoredFileServer> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGStoredFileServer, Integer, AGCoreModuleInstance> configuration) {

		configuration.setIcon(CoreImages.STORED_FILE_SERVER);
		configuration.addValidator(StoredFileServerValidator::new);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGStoredFileServer, AGCoreModuleInstance> authorizer) {

		authorizer.setEditPermission(CorePermissions.SUPER_USER.toOtherEntityPermission());
		authorizer.setCreationPermission(CorePermissions.SUPER_USER.toOtherEntityPermission());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGStoredFileServer> attributes) {

		attributes.editAttribute(AGStoredFileServer.URL).setPredicateMandatory(EmfPredicates.always());
		attributes.editAttribute(AGStoredFileServer.DOMAIN).setPredicateMandatory(EmfPredicates.always());
		attributes.editAttribute(AGStoredFileServer.USERNAME).setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editStringAttribute(AGStoredFileServer.PASSWORD)
			.setPasswordMode(true)
			.setPredicateMandatory(EmfPredicates.always());
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
