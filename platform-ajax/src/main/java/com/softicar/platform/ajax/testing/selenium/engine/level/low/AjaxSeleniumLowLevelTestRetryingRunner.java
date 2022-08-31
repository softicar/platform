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
 * Retries failed low-level Selenium test executions.
 * <p>
 * <b>DO NOT USE THIS BEYOND THE AJAX SUB-PROJECT!</b> Retrying test execution
 * should not be necessary for <b>any</b> project.
 * <p>
 * If a unit test causes an undefined state in a Selenium Web Driver instance,
 * any subsequent test that uses the same driver instance might fail. While it's
 * easy to identify a failing test, it is hard to identify the culprit test
 * which caused the undefined state in the Selenium Web Driver.
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
