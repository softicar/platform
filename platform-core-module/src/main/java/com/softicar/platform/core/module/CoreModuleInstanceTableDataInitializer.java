package com.softicar.platform.core.module;

import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.language.AGCoreLanguage;
import com.softicar.platform.core.module.localization.AGLocalization;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.table.configuration.IDbTableDataInitializer;

/**
 * Initializes the singleton instance of the {@link AGCoreModuleInstance}.
 * <p>
 * A system user is also created and configured.
 *
 * @author Oliver Richers
 */
public class CoreModuleInstanceTableDataInitializer implements IDbTableDataInitializer {

	public static final String INTERNATIONAL_LOCALIZATION_PRESET_NAME = "International";

	@Override
	public void initializeData() {

		var localizationPreset = insertInternationalLocalizationPreset();
		var systemUser = insertSystemUser(localizationPreset);

		AGModuleInstanceBase.TABLE//
			.createInsert()
			.set(AGModuleInstanceBase.ID, AGCoreModuleInstance.SINGLETON_INSTANCE_ID)
			.set(AGModuleInstanceBase.TRANSACTION, insertTransaction(systemUser))
			.set(AGModuleInstanceBase.ACTIVE, true)
			.set(AGModuleInstanceBase.MODULE_UUID, AGUuid.getOrCreate(CoreModule.class))
			.executeWithoutIdGeneration();
		AGCoreModuleInstance.TABLE//
			.createInsert()
			.set(AGCoreModuleInstance.BASE, AGModuleInstanceBase.TABLE.getStub(AGCoreModuleInstance.SINGLETON_INSTANCE_ID))
			.set(AGCoreModuleInstance.DEFAULT_LOCALIZATION, localizationPreset)
			.set(AGCoreModuleInstance.SYSTEM_USER, systemUser)
			.executeWithoutIdGeneration();
	}

	private AGLocalization insertInternationalLocalizationPreset() {

		var defaultLocale = new Locale();
		var id = AGLocalization.TABLE//
			.createInsert()
			.set(AGLocalization.NAME, INTERNATIONAL_LOCALIZATION_PRESET_NAME)
			.set(AGLocalization.LANGUAGE, AGCoreLanguage.getByLanguageEnum(defaultLocale.getLanguage()).get())
			.set(AGLocalization.DECIMAL_SEPARATOR, defaultLocale.getDecimalSeparator())
			.set(AGLocalization.DIGIT_GROUP_SEPARATOR, defaultLocale.getDigitGroupSeparator())
			.set(AGLocalization.DATE_FORMAT, defaultLocale.getDateFormat())
			.set(AGLocalization.LOCALIZED_DATE_FORMAT, defaultLocale.getLocalizedDateFormat())
			.set(AGLocalization.LOCALIZED_TIME_FORMAT, defaultLocale.getLocalizedTimeFormat())
			.execute();
		return AGLocalization.TABLE.getStub(id);
	}

	private AGUser insertSystemUser(AGLocalization localizationPreset) {

		var id = AGUser.TABLE//
			.createInsert()
			.set(AGUser.LOGIN_NAME, "system.user")
			.set(AGUser.FIRST_NAME, "System")
			.set(AGUser.LAST_NAME, "User")
			.set(AGUser.EMAIL_ADDRESS, "system.user@example.com")
			.set(AGUser.LOCALIZATION, localizationPreset)
			.set(AGUser.SYSTEM_USER, true)
			.execute();
		return AGUser.TABLE.getStub(id);
	}

	private AGTransaction insertTransaction(AGUser user) {

		var id = AGTransaction.TABLE//
			.createInsert()
			.set(AGTransaction.AT, DayTime.now())
			.set(AGTransaction.BY, user)
			.execute();
		return AGTransaction.TABLE.getStub(id);
	}
}
