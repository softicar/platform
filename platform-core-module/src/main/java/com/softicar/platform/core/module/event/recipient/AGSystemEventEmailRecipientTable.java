package com.softicar.platform.core.module.event.recipient;

import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;

public class AGSystemEventEmailRecipientTable extends EmfObjectTable<AGSystemEventEmailRecipient, SystemModuleInstance> {

	public AGSystemEventEmailRecipientTable(IDbObjectTableBuilder<AGSystemEventEmailRecipient> builder) {

		super(builder);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGSystemEventEmailRecipient> attributes) {

		attributes//
			.editAttribute(AGSystemEventEmailRecipient.RECIPIENT)
			.setImmutable(true);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGSystemEventEmailRecipient> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGSystemEventEmailRecipientLog.RECIPIENT, AGSystemEventEmailRecipientLog.TRANSACTION)
			.addMapping(AGSystemEventEmailRecipient.ACTIVE, AGSystemEventEmailRecipientLog.ACTIVE);
	}
}
