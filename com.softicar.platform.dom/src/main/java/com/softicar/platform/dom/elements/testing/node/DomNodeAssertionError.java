package com.softicar.platform.dom.elements.testing.node;

public class DomNodeAssertionError extends AssertionError {

	public DomNodeAssertionError(String detailMessage, String mainMessage, Object...args) {

		super(createMessage(detailMessage, mainMessage, args));
	}

	private static String createMessage(String detailMessage, String mainMessage, Object...args) {

		StringBuilder message = new StringBuilder();
		if (detailMessage != null) {
			message.append(detailMessage);
			message.append(". ");
		}
		if (args != null && args.length > 0) {
			message.append(String.format(mainMessage, args));
		} else {
			message.append(mainMessage);
		}
		return message.toString();
	}
}
