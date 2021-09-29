package com.softicar.platform.core.module.ajax.event;

import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGAjaxEventTable extends EmfObjectTable<AGAjaxEvent, SystemModuleInstance> {

	public AGAjaxEventTable(IDbObjectTableBuilder<AGAjaxEvent> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGAjaxEvent, Integer, SystemModuleInstance> configuration) {

		configuration.setIcon(CoreImages.EVENT_HISTORY);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGAjaxEvent, SystemModuleInstance> authorizer) {

		authorizer.setCreationRole(EmfRoles.nobody());
		authorizer.setEditRole(EmfRoles.nobody());
		authorizer.setViewRole(CoreRoles.SUPER_USER.toOtherEntityRole());
	}
}
