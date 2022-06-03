package com.softicar.platform.core.module.user.login;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.management.EmfManagementConfiguration;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGUserLoginLogTable extends EmfObjectTable<AGUserLoginLog, SystemModuleInstance> {

	public AGUserLoginLogTable(IDbObjectTableBuilder<AGUserLoginLog> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGUserLoginLog, Integer, SystemModuleInstance> configuration) {

		configuration.setIcon(CoreImages.LOGIN);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGUserLoginLog> attributes) {

		attributes.editAttribute(AGUserLoginLog.PASSWORD).setConcealed(true);
		attributes.editAttribute(AGUserLoginLog.ID).setConcealed(true);
	}

	@Override
	public void customizeManagementConfiguraton(EmfManagementConfiguration<AGUserLoginLog> configuration) {

		configuration.addOrderBy(AGUserLoginLog.LOGIN_AT, OrderDirection.DESCENDING);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGUserLoginLog, SystemModuleInstance> authorizer) {

		authorizer.setViewPermission(CorePermissions.SUPER_USER.toOtherEntityPermission());
		authorizer.setEditPermission(EmfPermissions.nobody());
		authorizer.setCreationPermission(EmfPermissions.nobody());
	}
}
