package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGStoredFileLogTable extends EmfObjectTable<AGStoredFileLog, AGCoreModuleInstance> {

	public AGStoredFileLogTable(IDbObjectTableBuilder<AGStoredFileLog> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGStoredFileLog, Integer, AGCoreModuleInstance> configuration) {

		configuration.setIcon(CoreImages.STORED_FILE);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGStoredFileLog, AGCoreModuleInstance> authorizer) {

		authorizer//
			.setCreationPermission(EmfPermissions.never())
			.setEditPermission(EmfPermissions.never())
			.setDeletePermission(EmfPermissions.never())
			.setViewPermission(CoreModule.getModuleAdministation());
	}
}
