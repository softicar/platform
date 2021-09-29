package com.softicar.platform.core.module.event.panic;

import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.delete.EmfDeleteStrategyBuilder;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGModulePanicReceiverTable extends EmfObjectTable<AGModulePanicReceiver, AGUuid> {

	public AGModulePanicReceiverTable(IDbObjectTableBuilder<AGModulePanicReceiver> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGModulePanicReceiver, Integer, AGUuid> configuration) {

		configuration.setScopeField(AGModulePanicReceiver.MODULE_UUID);
		configuration
			.setDeleteStrategy(
				new EmfDeleteStrategyBuilder<AGModulePanicReceiver>()//
					.setDeleteFromTable()
					.build());
		configuration.setIcon(CoreImages.EVENT_LEVEL_PANIC_RECEIVERS);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGModulePanicReceiver> attributes) {

		attributes//
			.editAttribute(AGModulePanicReceiver.USER)
			.setImmutable(true);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGModulePanicReceiver> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGModulePanicReceiverLog.MODULE_PANIC_RECEIVER, AGModulePanicReceiverLog.TRANSACTION)
			.addMapping(AGModulePanicReceiver.ACTIVE, AGModulePanicReceiverLog.ACTIVE);
	}
}
