package com.softicar.platform.core.module.test;

import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.core.module.language.AGCoreLanguage;
import com.softicar.platform.core.module.localization.AGLocalization;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestEngine;
import org.junit.Rule;

public abstract class AbstractCoreTest extends AbstractDbTest implements IDomTestEngineMethods, CoreModuleTestFixtureMethods {

	@Rule public final IDomTestEngine engine = new DomDocumentTestEngine();

	public AbstractCoreTest() {

		AGLocalization localization = insertLocalizationWithoutLogging();
		AGUser user = insertUserWithoutLogging(localization);
		CurrentUser.set(user);
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}

	// Bootstrapping Problem
	// ---------------------
	// We need to insert the user without triggering the logging,
	// since the logging depends on the current user.
	private AGUser insertUserWithoutLogging(AGLocalization localization) {

		int userId = AGUser.TABLE//
			.createInsert()
			.set(AGUser.LOGIN_NAME, "current.user")
			.set(AGUser.FIRST_NAME, "Current")
			.set(AGUser.LAST_NAME, "User")
			.set(AGUser.LOCALIZATION, localization)
			.execute();
		return AGUser.get(userId);
	}

	private AGLocalization insertLocalizationWithoutLogging() {

		int localizationId = AGLocalization.TABLE//
			.createInsert()
			.set(AGLocalization.NAME, "current-user-localization")
			.set(AGLocalization.LANGUAGE, AGCoreLanguage.getByLanguageEnum(LanguageEnum.ENGLISH).get())
			.set(AGLocalization.DECIMAL_SEPARATOR, ".")
			.set(AGLocalization.DIGIT_GROUP_SEPARATOR, "")
			.execute();
		return AGLocalization.get(localizationId);
	}
}
