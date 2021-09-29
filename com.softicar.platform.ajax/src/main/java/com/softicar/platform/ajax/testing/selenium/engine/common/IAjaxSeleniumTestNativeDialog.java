package com.softicar.platform.ajax.testing.selenium.engine.common;

/**
 * A native dialog spawned by a browser.
 * <p>
 * That is, either an <i>"Alert"</i>, a <i>"Confirm"</i>, or a <i>"Prompt"</i>
 * dialog.
 */
public interface IAjaxSeleniumTestNativeDialog {

	/**
	 * Returns the textual message displayed in the dialog.
	 *
	 * @return the dialog text (may be <i>null</i>)
	 */
	String getText();

	/**
	 * Accepts the dialog. Corresponds to clicking <i>"Okay"</i>.
	 */
	void accept();

	/**
	 * Dismisses the dialog. Corresponds to clicking <i>"Cancel"</i>.
	 */
	void dismiss();

	/**
	 * Sends the given test to the dialog.
	 * <p>
	 * Only viable for <i>"Prompt"</i> dialogs (which expect user input).
	 *
	 * @param text
	 *            the text to type into the dialog (never <i>null</i>)
	 */
	void sendKeys(String text);
}
