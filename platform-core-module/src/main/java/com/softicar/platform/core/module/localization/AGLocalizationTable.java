package com.softicar.platform.core.module.localization;

import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGLocalizationTable extends EmfObjectTable<AGLocalization, SystemModuleInstance> {

	public AGLocalizationTable(IDbObjectTableBuilder<AGLocalization> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGLocalization, Integer, SystemModuleInstance> configuration) {

		configuration.addValidator(LocalizationValidator::new);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGLocalization> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGLocalizationLog.USER, AGLocalizationLog.TRANSACTION)
			.addMapping(AGLocalization.NAME, AGLocalizationLog.NAME)
			.addMapping(AGLocalization.LANGUAGE, AGLocalizationLog.LANGUAGE)
			.addMapping(AGLocalization.DECIMAL_SEPARATOR, AGLocalizationLog.DECIMAL_SEPARATOR)
			.addMapping(AGLocalization.DIGIT_GROUP_SEPARATOR, AGLocalizationLog.DIGIT_GROUP_SEPARATOR);
	}
}
