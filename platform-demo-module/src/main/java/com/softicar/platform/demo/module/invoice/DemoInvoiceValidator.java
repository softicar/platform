package com.softicar.platform.demo.module.invoice;

import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.emf.validation.AbstractEmfValidator;

public class DemoInvoiceValidator extends AbstractEmfValidator<AGDemoInvoice> {

	@Override
	protected void validate() {

		if (!isAlphaNumeric(tableRow.getInvoiceNumber())) {
			addError(AGDemoInvoice.INVOICE_NUMBER, DemoI18n.INVOICE_NUMBERS_MAY_ONLY_CONTAIN_LETTERS_AND_DIGITS);
		}
	}

	private static boolean isAlphaNumeric(String text) {

		for (int i = 0; i < text.length(); i++) {
			if (!Character.isLetterOrDigit(text.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
