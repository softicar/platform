package com.softicar.platform.core.module.localization;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGLocalizationTable extends EmfObjectTable<AGLocalization, AGCoreModuleInstance> {

	public AGLocalizationTable(IDbObjectTableBuilder<AGLocalization> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGLocalization, Integer, AGCoreModuleInstance> configuration) {

		configuration.addValidator(LocalizationValidator::new);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGLocalization> attributes) {

		attributes.editAttribute(AGLocalization.DATE_FORMAT).setHelpDisplay(CoreI18n.DD_FOR_DAYS_MM_FOR_MONTHS_AND_YYYY_FOR_YEARS);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGLocalization> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGLocalizationLog.USER, AGLocalizationLog.TRANSACTION)
			.addMapping(AGLocalization.NAME, AGLocalizationLog.NAME)
			.addMapping(AGLocalization.LANGUAGE, AGLocalizationLog.LANGUAGE)
			.addMapping(AGLocalization.DECIMAL_SEPARATOR, AGLocalizationLog.DECIMAL_SEPARATOR)
			.addMapping(AGLocalization.DIGIT_GROUP_SEPARATOR, AGLocalizationLog.DIGIT_GROUP_SEPARATOR)
			.addMapping(AGLocalization.DATE_FORMAT, AGLocalizationLog.DATE_FORMAT)
			.addMapping(AGLocalization.LOCALIZED_DATE_FORMAT, AGLocalizationLog.LOCALIZED_DATE_FORMAT)
			.addMapping(AGLocalization.LOCALIZED_TIME_FORMAT, AGLocalizationLog.LOCALIZED_TIME_FORMAT);
	}
}
