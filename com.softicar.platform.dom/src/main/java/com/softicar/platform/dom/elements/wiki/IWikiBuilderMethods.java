package com.softicar.platform.dom.elements.wiki;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.Padding;
import com.softicar.platform.common.ui.wiki.element.tag.WikiBoxType;

public interface IWikiBuilderMethods<T extends IWikiBuilderMethods<T>> {

	// -------------------- text -------------------- //

	T addText(IDisplayString text);

	default T addText(String text) {

		return addText(IDisplayString.create(text));
	}

	// -------------------- line -------------------- //

	default T addLine(IDisplayString line) {

		return addText(line.toString()).addText("\n");
	}

	// -------------------- headline -------------------- //

	default T addHeadline(String level, IDisplayString headline) {

		return addLine(
			new DisplayString()//
				.append(level)
				.append(headline)
				.append(level));
	}

	// -------------------- list item -------------------- //

	default T addUnorderedListItem(IDisplayString line) {

		return addUnorderedListItem(0, line);
	}

	default T addUnorderedListItem(int index, IDisplayString line) {

		addText(Padding.padLeft("", ' ', 2 + index * 2));
		addText("*");
		return addLine(line);
	}

	// -------------------- code -------------------- //

	default T addInlineCode(String code) {

		return addText(
			new DisplayString()//
				.append("<code>")
				.append(code)
				.append("</code>"));
	}

	default T beginCode() {

		return addLine(IDisplayString.create("<code>"));
	}

	default T endCode() {

		return addLine(IDisplayString.create("</code>"));

	}

	default T beginBox(WikiBoxType type) {

		return addLine(IDisplayString.create(type.name()).enclose("<", ">"));
	}

	default T endBox(WikiBoxType type) {

		return addLine(IDisplayString.create(type.name()).enclose("</", ">"));
	}
}
