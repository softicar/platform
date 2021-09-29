package com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces;

import com.softicar.platform.dom.node.IDomNode;

/**
 * Facilitates the simulation of events in a UI-under-test.
 *
 * @see IAjaxSeleniumLowLevelTestEngine
 * @author Alexander Schmidt
 */
public interface IAjaxSeleniumLowLevelTestEngineEventSimulator {

	void simulateKeyDown(IDomNode node, int keyCode);

	void simulateKeyUp(IDomNode node, int keyCode);

	void simulateChange(IDomNode node);

	void simulateInput(IDomNode node);
}
