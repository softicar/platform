package com.softicar.platform.core.module.user.password;

import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGUserPasswordTable extends EmfObjectTable<AGUserPassword, SystemModuleInstance> {

	public AGUserPasswordTable(IDbObjectTableBuilder<AGUserPassword> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGUserPassword, Integer, SystemModuleInstance> configuration) {

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
	public void customizeAuthorizer(EmfAuthorizer<AGUserPassword, SystemModuleInstance> authorizer) {

		authorizer//
			.setCreationPermission(EmfPermissions.never())
			.setEditPermission(EmfPermissions.never())
			.setDeletePermission(EmfPermissions.never())
			.setViewPermission(CorePermissions.SUPER_USER.toOtherEntityPermission());
	}
}
