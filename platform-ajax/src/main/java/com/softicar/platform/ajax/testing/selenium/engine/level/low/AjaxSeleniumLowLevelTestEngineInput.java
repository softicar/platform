package com.softicar.platform.ajax.testing.selenium.engine.level.low;

import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestSegment;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Facilitates sending various kinds of input to a UI-under-test.
 *
 * @author Alexander Schmidt
 */
public class AjaxSeleniumLowLevelTestEngineInput {

	private final Supplier<WebDriver> webDriverSupplier;
	private final Function<IDomNode, WebElement> webElementResolver;
	private final Supplier<WebElement> sessionTimeoutDivSupplier;

	AjaxSeleniumLowLevelTestEngineInput(AjaxSeleniumLowLevelTestEngineParameters parameters) {

		this.webDriverSupplier = parameters.getWebDriverSupplier();
		this.webElementResolver = parameters.getWebElementResolver();
		this.sessionTimeoutDivSupplier = parameters.getSessionTimeoutDivSupplier();
	}

	public void click(IDomNode node) {

		webElementResolver.apply(node).click();
	}

	/**
	 * TODO Check if this implementation is unsafe: Will it leave the web driver
	 * in an inconsistent state if the test execution is aborted, as a result of
	 * the click?
	 */
	public void click(IDomNode node, Modifier...modifiers) {

		Actions actions = new Actions(webDriverSupplier.get());
		Arrays.asList(modifiers).stream().map(this::getKeysFromModifier).forEach(actions::keyDown);
		actions.click(webElementResolver.apply(node));
		Arrays.asList(modifiers).stream().map(this::getKeysFromModifier).forEach(actions::keyUp);
		actions.perform();
	}

	public void clickAt(IDomNode node, int xOffset, int yOffset) {

		WebElement webElement = webElementResolver.apply(node);
		Point position = getCorrectedClickPosition(webElement, new Point(xOffset, yOffset));
		new Actions(webDriverSupplier.get())//
			.moveToElement(webElement)
			.moveByOffset(position.getX(), position.getY())
			.click()
			.perform();
	}

	public void clickBodyNode() {

		webDriverSupplier.get().findElement(By.tagName("body")).click();
	}

	public void clickSessionTimeoutDivReturnToLoginButton() {

		sessionTimeoutDivSupplier.get().findElement(By.tagName("button")).click();
	}

	public void doubleClickAt(IDomNode node, int xOffset, int yOffset) {

		WebElement webElement = webElementResolver.apply(node);
		Point position = getCorrectedClickPosition(webElement, new Point(xOffset, yOffset));
		new Actions(webDriverSupplier.get())//
			.moveToElement(webElement)
			.moveByOffset(position.getX(), position.getY())
			.doubleClick()
			.perform();
	}

	public void mouseDownUnsafe(IDomNode node) {

		new Actions(webDriverSupplier.get())//
			.moveToElement(webElementResolver.apply(node))
			.clickAndHold()
			.perform();
	}

	public void mouseUpUnsafe() {

		new Actions(webDriverSupplier.get())//
			.release()
			.perform();
	}

	public void moveCursorBy(int xOffset, int yOffset) {

		new Actions(webDriverSupplier.get())//
			.moveByOffset(xOffset, yOffset)
			.perform();
	}

	public void dragAndDrop(IDomNode node, int xOffset, int yOffset) {

		new Actions(webDriverSupplier.get())//
			.clickAndHold(webElementResolver.apply(node))
			.moveByOffset(xOffset, yOffset)
			.release()
			.perform();
	}

	public void send(Key...keys) {

		Arrays//
			.asList(keys)
			.stream()
			.map(this::getKeysFromKey)
			.forEach(webDriverSupplier.get().switchTo().activeElement()::sendKeys);
	}

	public void send(String keys) {

		webDriverSupplier.get().switchTo().activeElement().sendKeys(keys);
	}

	public void send(IDomNode node, Key...keys) {

		Arrays//
			.asList(keys)
			.stream()
			.map(this::getKeysFromKey)
			.forEach(webElementResolver.apply(node)::sendKeys);
	}

	public void send(IDomNode node, String keys) {

		webElementResolver.apply(node).sendKeys(keys);
	}

	public void clear(IDomTextualInput input) {

		webElementResolver.apply(input).clear();
	}

	private Keys getKeysFromModifier(Modifier modifier) {

		switch (modifier) {
		case ALT:
			return Keys.ALT;
		case CONTROL:
			return Keys.CONTROL;
		case META:
			return Keys.META;
		case SHIFT:
			return Keys.SHIFT;
		}
		throw new SofticarUnknownEnumConstantException(modifier);
	}

	private Keys getKeysFromKey(Key key) {

		switch (key) {
		case BACK_SPACE:
			return Keys.BACK_SPACE;
		case DOWN:
			return Keys.DOWN;
		case ESCAPE:
			return Keys.ESCAPE;
		case ENTER:
			return Keys.ENTER;
		case SPACE:
			return Keys.SPACE;
		case TAB:
			return Keys.TAB;
		case UP:
			return Keys.UP;
		}
		throw new SofticarUnknownEnumConstantException(key);
	}

	private Point getCorrectedClickPosition(WebElement webElement, Point position) {

		// This code works around a defect in the moveToElement() method,
		// which should actually move the mouse relative to the top-left
		// corner of the element but it does not. So we move it back from
		// the center to the top-left corner.
		Dimension dimension = webElement.getSize();
		AjaxSeleniumTestSegment size = new AjaxSeleniumTestSegment(dimension.getWidth(), dimension.getHeight());
		int centerX = size.getWidth() / 2;
		int centerY = size.getHeight() / 2;
		return new Point(position.getX() - centerX, position.getY() - centerY);
	}

	/**
	 * Represents a non-modifier key on a keyboard.
	 *
	 * @author Alexander Schmidt
	 */
	public enum Key {

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
	public enum Modifier {

		ALT,
		CONTROL,
		META,
		SHIFT
	}
}
