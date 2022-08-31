package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.ajax.testing.selenium.AjaxSeleniumTestProperties;
import com.softicar.platform.common.core.utils.DevNull;
import org.junit.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * Retries failed Selenium test executions of the AJAX framework.
 * <p>
 * <b>DO NOT USE THIS BEYOND THE AJAX FRAMEWORK PROJECT!</b> Retrying test
 * execution should not be necessary for <b>any</b> project.
 * <p>
 * In the test suite of the AJAX framework, there is at least one test which
 * causes an undefined state in a Selenium Web Driver, which in turn can make an
 * arbitrary subsequent test fail. While it's easy to identify a failing test
 * (if any), it is hard to identify the culprit test which breaks the Selenium
 * Web Driver. Due to time pressure, the workaround of retrying failed tests was
 * considered a necessary evil to enable successful builds of the AJAX
 * framework, until a proper fix (of the mentioned culprit test as the actual
 * cause) is in place.
 * <p>
 * TODO This runner should be eliminated, because retrying test execution should
 * not be necessary (see above).
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class AjaxSeleniumLowLevelTestRetryingRunner extends BlockJUnit4ClassRunner {

	/**
	 * Constructs a new runner for the given test {@link Class}.
	 * <p>
	 * Throws if the given test class is incompatible (see
	 * {@link #isCompatibleTestClass(Class)}).
	 *
	 * @param testClass
	 *            the {@link Class} under test (never <i>null</i>)
	 * @throws InitializationError
	 */
	public AjaxSeleniumLowLevelTestRetryingRunner(Class<?> testClass) throws InitializationError {

		super(testClass);

		if (!isCompatibleTestClass(testClass)) {
			throw new InitializationError(String.format("This runner must no be used with '%s'.", testClass.getCanonicalName()));
		}
	}

	@Override
	protected void runChild(final FrameworkMethod method, RunNotifier notifier) {

		Description description = describeChild(method);
		if (isIgnored(method)) {
			notifier.fireTestIgnored(description);
		} else {
			runLeafWithRetry(method, description, notifier);
		}
	}

	private void runLeafWithRetry(FrameworkMethod method, Description description, RunNotifier notifier) {

		EachTestNotifier eachNotifier = new EachTestNotifier(notifier, description);
		eachNotifier.fireTestStarted();
		try {
			runLeafWithRetry(method);
		} catch (AssumptionViolatedException e) {
			eachNotifier.addFailedAssumption(e);
		} catch (Throwable throwable) {
			eachNotifier.addFailure(throwable);
		} finally {
			eachNotifier.fireTestFinished();
		}
	}

	private void runLeafWithRetry(FrameworkMethod method) throws Throwable {

		for (int i = 0; true; i++) {
			try {
				methodBlock(method).evaluate();
				return;
			} catch (AssertionError exception) {
				if (i < AjaxSeleniumTestProperties.ENGINE_LEVEL_LOW_RETRY_COUNT.getValue()) {
					DevNull.swallow(exception);
				} else {
					throw exception;
				}
			}
		}
	}

	private boolean isCompatibleTestClass(Class<?> testClass) {

		return AbstractAjaxSeleniumLowLevelTest.class.isAssignableFrom(testClass);
	}
}
