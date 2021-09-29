package com.softicar.platform.common.ui.wiki;

import com.softicar.platform.common.ui.color.IColor;

public class SimpleWikiParserHtmlCallback implements ISimpleWikiParserCallback {

	private final StringBuilder html;

	public SimpleWikiParserHtmlCallback() {

		this.html = new StringBuilder();
	}

	public String getHtml() {

		return html.toString();
	}

	public void clear() {

		html.setLength(0);
	}

	@Override
	public void addText(String text) {

		html.append(text);
	}

	@Override
	public void addWhitespace(String whitespace) {

		html.append(" ");
	}

	@Override
	public void addNewLine() {

		html.append("<br>");
	}

	@Override
	public void beginParagraph() {

		html.append("<p>");
	}

	@Override
	public void endParagraph() {

		html.append("</p>");
	}

	@Override
	public void beginBold() {

		html.append("<b>");
	}

	@Override
	public void endBold() {

		html.append("</b>");
	}

	@Override
	public void beginItalic() {

		html.append("<i>");
	}

	@Override
	public void endItalic() {

		html.append("</i>");
	}

	@Override
	public void beginColor(IColor color) {

		html.append(String.format("<span style='color: %s'>", color.toHtml()));
	}

	@Override
	public void endColor() {

		html.append("</span>");
	}

	@Override
	public void beginHeadLine(int level) {

		html.append(String.format("<h%s>", level));
	}

	@Override
	public void endHeadLine(int level) {

		html.append(String.format("</h%s>", level));
	}

	@Override
	public void beginUnorderedList() {

		html.append("<ul>");
	}

	@Override
	public void endUnorderedList() {

		html.append("</ul>");
	}

	@Override
	public void beginOrderedList() {

		html.append("<ol>");
	}

	@Override
	public void endOrderedList() {

		html.append("</ol>");
	}

	@Override
	public void beginListItem() {

		html.append("<li>");
	}

	@Override
	public void endListItem() {

		html.append("</li>");
	}

	@Override
	public void beginSection(int level) {

		html.append("<div>");
	}

	@Override
	public void endSection() {

		html.append("</div>");
	}
}
