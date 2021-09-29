package com.softicar.platform.common.ui.wiki;

import com.softicar.platform.common.string.scanning.ISimpleTextScannerCallback;
import java.util.Stack;

public class SimpleWikiParserEngine implements ISimpleTextScannerCallback {

	private final ISimpleWikiParserCallback callback;
	private final Stack<String> listStack;
	private boolean inListItem;
	private boolean inParagraph;
	private boolean inHeadline;
	private boolean inSection;

	public SimpleWikiParserEngine(ISimpleWikiParserCallback callback) {

		this.callback = callback;
		this.listStack = new Stack<>();
		this.inListItem = false;
		this.inParagraph = false;
		this.inHeadline = false;
		this.inSection = false;
	}

	@Override
	public void consumeNormalText(String text) {

		endAllListsIfNecessary();
		beginParagraphIfNecessary();
		callback.addText(text);
	}

	@Override
	public void consumeWhitespace(String whitespace) {

		endAllListsIfNecessary();
		beginParagraphIfNecessary();
		callback.addWhitespace(whitespace);
	}

	public void flush() {

		endListItemAndAllLists();
		endParagraphIfNecessary();
		endSectionIfNecessary();
	}

	public void endParagraph() {

		endParagraphIfNecessary();
	}

	private void beginParagraphIfNecessary() {

		if (!inParagraph && !inHeadline && !inListItem) {
			callback.beginParagraph();
			this.inParagraph = true;
		}
	}

	private void endAllListsIfNecessary() {

		if (isInList() && !inListItem) {
			endAllLists();
		}
	}

	private void endParagraphIfNecessary() {

		if (inParagraph) {
			callback.endParagraph();
			this.inParagraph = false;
		}
	}

	private void endSectionIfNecessary() {

		if (inSection) {
			callback.endSection();
			this.inSection = false;
		}
	}

	public void beginHeadLine(int level) {

		endListItemAndAllLists();
		endParagraphIfNecessary();
		endSectionIfNecessary();
		callback.beginSection(level);
		this.inSection = true;
		callback.beginHeadLine(level);
		this.inHeadline = true;
	}

	public void endHeadLine(int level) {

		callback.endHeadLine(level);
		this.inHeadline = false;
	}

	// ---------- text formatting ---------- //

	public void beginBold() {

		endAllListsIfNecessary();
		beginParagraphIfNecessary();
		callback.beginBold();
	}

	public void endBold() {

		callback.endBold();
	}

	public void beginItalic() {

		endAllListsIfNecessary();
		beginParagraphIfNecessary();
		callback.beginItalic();
	}

	public void endItalic() {

		callback.endItalic();
	}

	// ---------- list handling ---------- //

	public String getCurrentList() {

		return listStack.isEmpty()? null : listStack.peek();
	}

	public boolean isInList() {

		return !listStack.isEmpty();
	}

	public void beginList(String text) {

		listStack.push(text);
		if (text.trim().equals("*")) {
			callback.beginUnorderedList();
		} else {
			callback.beginOrderedList();
		}
	}

	public void endCurrentList() {

		if (listStack.peek().trim().equals("*")) {
			callback.endUnorderedList();
		} else {
			callback.endOrderedList();
		}
		listStack.pop();
	}

	public void endListItemAndAllLists() {

		endListItem();
		endAllLists();
	}

	private void endAllLists() {

		while (!listStack.isEmpty()) {
			endCurrentList();
		}
	}

	public void beginListItem() {

		if (!inListItem) {
			callback.beginListItem();
			this.inListItem = true;
		}
	}

	public void endListItem() {

		if (inListItem) {
			callback.endListItem();
			this.inListItem = false;
		}
	}
}
