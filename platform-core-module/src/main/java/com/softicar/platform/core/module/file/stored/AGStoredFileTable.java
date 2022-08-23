package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.file.stored.attribute.StoredFileAttribute;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGStoredFileTable extends EmfObjectTable<AGStoredFile, AGCoreModuleInstance> {

	public AGStoredFileTable(IDbObjectTableBuilder<AGStoredFile> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGStoredFile, Integer, AGCoreModuleInstance> configuration) {

		configuration.setIcon(CoreImages.STORED_FILE);
		configuration.setAttributeFactory(StoredFileAttribute::new);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGStoredFile, AGCoreModuleInstance> authorizer) {

		authorizer.setCreationPermission(EmfPermissions.never());
		authorizer.setEditPermission(EmfPermissions.never());
		authorizer.setViewPermission(CoreModule.getModuleAdministation());
	}
}
