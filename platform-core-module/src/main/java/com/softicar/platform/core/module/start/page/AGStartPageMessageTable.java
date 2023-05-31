package com.softicar.platform.core.module.start.page;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.predicate.EmfPredicates;

public class AGStartPageMessageTable extends EmfObjectTable<AGStartPageMessage, AGCoreModuleInstance> {

	public AGStartPageMessageTable(IDbObjectTableBuilder<AGStartPageMessage> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGStartPageMessage, AGCoreModuleInstance> authorizer) {

		authorizer//
			.setCreationPermission(CorePermissions.ADMINISTRATION)
			.setDeletePermission(EmfPermissions.never())
			.setEditPermission(CoreModule.getAdministationPermission())
			.setViewPermission(CoreModule.getOperationPermission());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGStartPageMessage> attributes) {

		attributes//
			.editStringAttribute(AGStartPageMessage.MESSAGE)
			.setMultiline(true);
		attributes//
			.editAttribute(AGStartPageMessage.MESSAGE_DATE)
			.setImmutable(true)
			.setPredicateVisibleEditable(EmfPredicates.never());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGStartPageMessage> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGStartPageMessageLog.START_PAGE_MESSAGE, AGStartPageMessageLog.TRANSACTION)//
			.addMapping(AGStartPageMessage.ACTIVE, AGStartPageMessageLog.ACTIVE)
			.addMapping(AGStartPageMessage.MESSAGE, AGStartPageMessageLog.MESSAGE);
	}
}
