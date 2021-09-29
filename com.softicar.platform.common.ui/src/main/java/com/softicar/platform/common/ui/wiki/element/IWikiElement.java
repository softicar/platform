package com.softicar.platform.common.ui.wiki.element;

public interface IWikiElement {

	void accept(IWikiVisitor visitor);

	boolean isBlockElement();
}
