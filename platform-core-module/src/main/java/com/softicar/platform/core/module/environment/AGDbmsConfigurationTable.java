package com.softicar.platform.core.module.environment;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.trait.table.EmfTraitTable;

public class AGDbmsConfigurationTable extends EmfTraitTable<AGDbmsConfiguration, AGCoreModuleInstance> {

	public AGDbmsConfigurationTable(IDbTableBuilder<AGDbmsConfiguration, AGCoreModuleInstance> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDbmsConfiguration, AGCoreModuleInstance, AGCoreModuleInstance> configuration) {

		configuration.setIcon(CoreImages.SYSTEM);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGDbmsConfiguration> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGDbmsConfigurationLog.DBMS_CONFIGURATION, AGDbmsConfigurationLog.TRANSACTION)
			.addMapping(AGDbmsConfiguration.DBMS_DOWN_TIME_BEGIN, AGDbmsConfigurationLog.DBMS_DOWN_TIME_BEGIN)
			.addMapping(AGDbmsConfiguration.DBMS_DOWN_TIME_END, AGDbmsConfigurationLog.DBMS_DOWN_TIME_END)
			.addMapping(AGDbmsConfiguration.DBMS_DOWN_TIME_WEEKDAY, AGDbmsConfigurationLog.DBMS_DOWN_TIME_WEEKDAY);
	}
}
