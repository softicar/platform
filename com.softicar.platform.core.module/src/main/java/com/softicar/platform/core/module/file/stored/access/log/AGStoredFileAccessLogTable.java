package com.softicar.platform.core.module.file.stored.access.log;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGStoredFileAccessLogTable extends EmfObjectTable<AGStoredFileAccessLog, AGStoredFile> {

	public AGStoredFileAccessLogTable(IDbObjectTableBuilder<AGStoredFileAccessLog> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGStoredFileAccessLog, Integer, AGStoredFile> configuration) {

		configuration.setScopeField(AGStoredFileAccessLog.FILE);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGStoredFileAccessLog, AGStoredFile> authorizer) {

		authorizer.setCreationRole(EmfRoles.nobody());
		authorizer.setDeleteRole(EmfRoles.nobody());
		authorizer.setEditRole(EmfRoles.nobody());
	}
}
