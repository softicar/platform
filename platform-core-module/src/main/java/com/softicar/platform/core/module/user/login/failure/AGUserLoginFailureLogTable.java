package com.softicar.platform.core.module.user.login.failure;

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

public class AGUserLoginFailureLogTable extends EmfObjectTable<AGUserLoginFailureLog, SystemModuleInstance> {

	public AGUserLoginFailureLogTable(IDbObjectTableBuilder<AGUserLoginFailureLog> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGUserLoginFailureLog, Integer, SystemModuleInstance> configuration) {

		configuration.setIcon(CoreImages.LOGIN_FAILURE);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGUserLoginFailureLog> attributes) {

		attributes.editAttribute(AGUserLoginFailureLog.ID).setConcealed(true);
	}

	@Override
	public void customizeManagementConfiguraton(EmfManagementConfiguration<AGUserLoginFailureLog> configuration) {

		configuration.addOrderBy(AGUserLoginFailureLog.LOGIN_AT, OrderDirection.DESCENDING);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGUserLoginFailureLog, SystemModuleInstance> authorizer) {

		authorizer.setViewPermission(CorePermissions.SUPER_USER.toOtherEntityPermission());
		authorizer.setEditPermission(EmfPermissions.nobody());
		authorizer.setCreationPermission(EmfPermissions.nobody());
	}
}
