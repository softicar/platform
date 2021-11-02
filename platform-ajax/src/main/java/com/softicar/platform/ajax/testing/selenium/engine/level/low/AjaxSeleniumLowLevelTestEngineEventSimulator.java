package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineEventSimulator;
import com.softicar.platform.dom.node.IDomNode;

class AjaxSeleniumLowLevelTestEngineEventSimulator implements IAjaxSeleniumLowLevelTestEngineEventSimulator {

	private final IAjaxSeleniumLowLevelTestEngine engine;
	private final AjaxSeleniumLowLevelTestJavascriptExecutor javascriptExecutor;

	public AjaxSeleniumLowLevelTestEngineEventSimulator(AjaxSeleniumLowLevelTestEngineParameters parameters) {

		this.engine = parameters.getEngine();
		this.javascriptExecutor = new AjaxSeleniumLowLevelTestJavascriptExecutor(parameters.getWebDriverSupplier());
	}

	@Override
	public void simulateKeyDown(IDomNode node, int keyCode) {

		simulateKeyboardEvent(node, "keydown", keyCode);
	}

	@Override
	public void simulateKeyUp(IDomNode node, int keyCode) {

		simulateKeyboardEvent(node, "keyup", keyCode);
	}

	@Override
	public void simulateChange(IDomNode node) {

		String javascript = new StringBuilder()//
			.append("var event = new Event('change');")
			.append("document.getElementById('%s').dispatchEvent(event);")
			.toString();
		javascriptExecutor.execute(javascript, engine.getOutput().getAttributeValue(node, "id"));
	}

	@Override
	public void simulateInput(IDomNode node) {

		String javascript = new StringBuilder()//
			.append("var event = new InputEvent('input');")
			.append("document.getElementById('%s').dispatchEvent(event);")
			.toString();
		javascriptExecutor.execute(javascript, engine.getOutput().getAttributeValue(node, "id"));
	}

	private void simulateKeyboardEvent(IDomNode node, String type, int keyCode) {

		String javascript = new StringBuilder()//
			.append("var event = new KeyboardEvent('%s', {")
			.append("keyCode: %s,")
			.append("bubbles: true")
			.append("});")
			.append("document.getElementById('%s').dispatchEvent(event);")
			.toString();
		javascriptExecutor.execute(javascript, type, keyCode, engine.getOutput().getAttributeValue(node, "id"));
	}
}
