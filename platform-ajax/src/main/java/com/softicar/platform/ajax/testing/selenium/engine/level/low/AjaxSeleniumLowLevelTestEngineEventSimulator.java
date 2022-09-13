package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.dom.node.IDomNode;

/**
 * Facilitates the simulation of events in a UI-under-test.
 *
 * @author Alexander Schmidt
 */
public class AjaxSeleniumLowLevelTestEngineEventSimulator {

	private final AjaxSeleniumLowLevelTestEngine engine;
	private final AjaxSeleniumLowLevelTestJavascriptExecutor javascriptExecutor;

	AjaxSeleniumLowLevelTestEngineEventSimulator(AjaxSeleniumLowLevelTestEngineParameters parameters) {

		this.engine = parameters.getEngine();
		this.javascriptExecutor = new AjaxSeleniumLowLevelTestJavascriptExecutor(parameters.getWebDriverSupplier());
	}

	public void simulateKeyDown(IDomNode node, String key) {

		simulateKeyboardEvent(node, "keydown", key);
	}

	public void simulateKeyUp(IDomNode node, String key) {

		simulateKeyboardEvent(node, "keyup", key);
	}

	public void simulateChange(IDomNode node) {

		String javascript = new StringBuilder()//
			.append("var event = new Event('change');")
			.append("document.getElementById('%s').dispatchEvent(event);")
			.toString();
		javascriptExecutor.execute(javascript, engine.getOutput().getAttributeValue(node, "id"));
	}

	public void simulateInput(IDomNode node) {

		String javascript = new StringBuilder()//
			.append("var event = new InputEvent('input');")
			.append("document.getElementById('%s').dispatchEvent(event);")
			.toString();
		javascriptExecutor.execute(javascript, engine.getOutput().getAttributeValue(node, "id"));
	}

	public void simulateWheel(IDomNode node, int deltaY) {

		// inspired by https://stackoverflow.com/a/47287595
		String javascript = """
				var element = document.getElementById('%s');
				var rect = element.getBoundingClientRect();
				var clientX = rect.left + rect.width / 2;
				var clientY = rect.top + rect.height / 2;
				element.dispatchEvent(new MouseEvent('mouseover', {clientX: clientX, clientY: clientY}));
				element.dispatchEvent(new MouseEvent('mousemove', {clientX: clientX, clientY: clientY}));
				element.dispatchEvent(new WheelEvent('wheel',     {clientX: clientX, clientY: clientY, deltaY: %s}));
				""";
		String nodeId = engine.getOutput().getAttributeValue(node, "id");
		javascriptExecutor.execute(javascript, nodeId, deltaY);
	}

	private void simulateKeyboardEvent(IDomNode node, String type, String key) {

		String javascript = new StringBuilder()//
			.append("var event = new KeyboardEvent('%s', {")
			.append("key: '%s',")
			.append("bubbles: true")
			.append("});")
			.append("document.getElementById('%s').dispatchEvent(event);")
			.toString();
		String nodeId = engine.getOutput().getAttributeValue(node, "id");
		javascriptExecutor.execute(javascript, type, key, nodeId);
	}
}
