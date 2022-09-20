package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Rule;

/**
 * Base class of low-level Selenium UI tests.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public abstract class AbstractAjaxSeleniumLowLevelTest extends AbstractTest implements IAjaxSeleniumLowLevelTestEngineMethods {

	@Rule public final AjaxSeleniumLowLevelTestEngine testEngine;

	public AbstractAjaxSeleniumLowLevelTest() {

		this.testEngine = new AjaxSeleniumLowLevelTestEngine();
	}

	@Override
	public AjaxSeleniumLowLevelTestEngine getTestEngine() {

		return testEngine;
	}
}
