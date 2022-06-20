package com.softicar.platform.core.module.environment;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.trait.table.EmfTraitTable;

public class AGLiveSystemConfigurationTable extends EmfTraitTable<AGLiveSystemConfiguration, AGCoreModuleInstance> {

	public AGLiveSystemConfigurationTable(IDbTableBuilder<AGLiveSystemConfiguration, AGCoreModuleInstance> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGLiveSystemConfiguration, AGCoreModuleInstance, AGCoreModuleInstance> configuration) {

		configuration.setIcon(CoreImages.SYSTEM);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGLiveSystemConfiguration> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGLiveSystemConfigurationLog.LIVE_SYSTEM_CONFIGURATION, AGLiveSystemConfigurationLog.TRANSACTION)
			.addMapping(AGLiveSystemConfiguration.SYSTEM_NAME, AGLiveSystemConfigurationLog.SYSTEM_NAME)
			.addMapping(AGLiveSystemConfiguration.DBMS_DOWN_TIME_BEGIN, AGLiveSystemConfigurationLog.DBMS_DOWN_TIME_BEGIN)
			.addMapping(AGLiveSystemConfiguration.DBMS_DOWN_TIME_END, AGLiveSystemConfigurationLog.DBMS_DOWN_TIME_END)
			.addMapping(AGLiveSystemConfiguration.DBMS_DOWN_TIME_WEEKDAY, AGLiveSystemConfigurationLog.DBMS_DOWN_TIME_WEEKDAY);
	}
}
