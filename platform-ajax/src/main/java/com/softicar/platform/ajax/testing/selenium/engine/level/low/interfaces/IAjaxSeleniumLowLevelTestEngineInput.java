package com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces;

import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;

/**
 * Facilitates sending various kinds of input to a UI-under-test.
 *
 * @see IAjaxSeleniumLowLevelTestEngine
 * @author Alexander Schmidt
 */
public interface IAjaxSeleniumLowLevelTestEngineInput {

	void click(IDomNode node);

	void click(IDomNode node, Modifier...modifiers);

	void clickAt(IDomNode node, int xOffset, int yOffset);

	void clickBodyNode();

	void clickSessionTimeoutDivReturnToLoginButton();

	void doubleClickAt(IDomNode node, int xOffset, int yOffset);

	void mouseDownUnsafe(IDomNode node);

	void mouseUpUnsafe();

	void mouseWheelDown(IDomNode node);

	void mouseWheelUp(IDomNode node);

	void moveCursorBy(int xOffset, int yOffset);

	void dragAndDrop(IDomNode target, int xOffset, int yOffset);

	void send(Key...keys);

	void send(String keys);

	void send(IDomNode target, Key...keys);

	void send(IDomNode target, String keys);

	void clear(IDomTextualInput input);

	/**
	 * Represents a non-modifier key on a keyboard.
	 *
	 * @author Alexander Schmidt
	 */
	enum Key {

		BACK_SPACE,
		DOWN,
		ENTER,
		ESCAPE,
		SPACE,
		TAB,
		UP
	}

	/**
	 * Represents a modifier key on a keyboard.
	 *
	 * @author Alexander Schmidt
	 */
	enum Modifier {

		ALT,
		CONTROL,
		META,
		SHIFT
	}
}
