package com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.testing.selenium.engine.common.IAjaxSeleniumTestEngineWaitMethods;
import com.softicar.platform.dom.node.IDomNode;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.rules.TestRule;

/**
 * Common interface of UI test engine implementations.
 * <p>
 * Consists of distinct parts which provide functionality to interact with the
 * UI-under-test, and examine its output.
 */
public interface IAjaxSeleniumLowLevelTestEngine extends TestRule, IAjaxSeleniumTestEngineWaitMethods {

	/**
	 * Creates an {@link IDomNode} using the given factory, and initializes it
	 * as the UI-under-test.
	 *
	 * @param <T>
	 *            the exact type of the {@link IDomNode}
	 * @param factory
	 *            the factory that creates the {@link IDomNode}
	 * @return the initialized {@link IDomNode} (never <i>null</i>)
	 */
	<T extends IDomNode> T openTestNode(Supplier<T> factory);

	/**
	 * Creates an {@link IDomNode} using the given factory, and initializes it
	 * as the UI-under-test.
	 *
	 * @param <T>
	 *            the exact type of the {@link IDomNode}
	 * @param factory
	 *            the factory that creates the {@link IDomNode}
	 * @return the initialized {@link IDomNode} (never <i>null</i>)
	 */
	<T extends IDomNode> T openTestNode(Function<IAjaxDocument, T> factory);

	/**
	 * Adds a new screenshot to the internal screenshot queue.
	 *
	 * @param fileName
	 *            the screenshot file name (never <i>null</i>)
	 */
	void takeScreenshot(String fileName);

	/**
	 * Returns the {@link IAjaxSeleniumLowLevelTestEngineInput} of this
	 * {@link IAjaxSeleniumLowLevelTestEngine}.
	 *
	 * @return the {@link IAjaxSeleniumLowLevelTestEngineInput} (never
	 *         <i>null</i>)
	 */
	IAjaxSeleniumLowLevelTestEngineInput getInput();

	/**
	 * Returns the {@link IAjaxSeleniumLowLevelTestEngineOutput} of this
	 * {@link IAjaxSeleniumLowLevelTestEngine}.
	 *
	 * @return the {@link IAjaxSeleniumLowLevelTestEngineOutput} (never
	 *         <i>null</i>)
	 */
	IAjaxSeleniumLowLevelTestEngineOutput getOutput();

	/**
	 * Returns the {@link IAjaxSeleniumLowLevelTestEngineViewport} of this
	 * {@link IAjaxSeleniumLowLevelTestEngine}.
	 *
	 * @return the {@link IAjaxSeleniumLowLevelTestEngineViewport} (never
	 *         <i>null</i>)
	 */
	IAjaxSeleniumLowLevelTestEngineViewport getViewport();

	/**
	 * Returns the {@link IAjaxSeleniumLowLevelTestEngineEventSimulator} of this
	 * {@link IAjaxSeleniumLowLevelTestEngine}.
	 *
	 * @return the {@link IAjaxSeleniumLowLevelTestEngineEventSimulator} (never
	 *         <i>null</i>)
	 */
	IAjaxSeleniumLowLevelTestEngineEventSimulator getEventSimulator();

	/**
	 * Returns the {@link IAjaxSeleniumLowLevelTestEngineAutoCompleteExtension}
	 * of this {@link IAjaxSeleniumLowLevelTestEngine}.
	 *
	 * @return the {@link IAjaxSeleniumLowLevelTestEngineAutoCompleteExtension}
	 *         (never <i>null</i>)
	 */
	IAjaxSeleniumLowLevelTestEngineAutoCompleteExtension getAutoCompleteExtension();
}
