package com.softicar.platform.core.module.user.password;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGUserPasswordTable extends EmfObjectTable<AGUserPassword, AGCoreModuleInstance> {

	public AGUserPasswordTable(IDbObjectTableBuilder<AGUserPassword> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGUserPassword, Integer, AGCoreModuleInstance> configuration) {

		configuration.setIcon(CoreImages.PASSWORD);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGUserPassword> attributes) {

		attributes//
			.editAttribute(AGUserPassword.ENCRYPTED_PASSWORD)
			.setConcealed(true);
		attributes//
			.editAttribute(AGUserPassword.ALGORITHM)
			.setConcealed(true);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGUserPassword, AGCoreModuleInstance> authorizer) {

		authorizer//
			.setCreationPermission(EmfPermissions.never())
			.setEditPermission(EmfPermissions.never())
			.setDeletePermission(EmfPermissions.never())
			.setViewPermission(CoreModule.getModuleAdministation());
	}
}
