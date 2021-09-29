package com.softicar.platform.common.ui.wiki;

import com.softicar.platform.common.ui.color.IColor;

public interface ISimpleWikiParserCallback {

	void addText(String text);

	void addWhitespace(String whitespace);

	void addNewLine();

	void beginParagraph();

	void endParagraph();

	void beginBold();

	void endBold();

	void beginItalic();

	void endItalic();

	void beginColor(IColor color);

	void endColor();

	void beginHeadLine(int level);

	void endHeadLine(int level);

	void beginUnorderedList();

	void endUnorderedList();

	void beginOrderedList();

	void endOrderedList();

	void beginListItem();

	void endListItem();

	void beginSection(int level);

	void endSection();
}
