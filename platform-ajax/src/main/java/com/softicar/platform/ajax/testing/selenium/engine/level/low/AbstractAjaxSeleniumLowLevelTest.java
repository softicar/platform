package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineMethods;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Base class of low-level Selenium UI tests.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
@RunWith(AjaxSeleniumLowLevelTestRetryingRunner.class)
public abstract class AbstractAjaxSeleniumLowLevelTest extends Assert implements IAjaxSeleniumLowLevelTestEngineMethods {

	@Rule public final IAjaxSeleniumLowLevelTestEngine testEngine;

	public AbstractAjaxSeleniumLowLevelTest() {

		this.testEngine = new AjaxSeleniumLowLevelTestEngine();
	}

	@Override
	public IAjaxSeleniumLowLevelTestEngine getTestEngine() {

		return testEngine;
	}
}
