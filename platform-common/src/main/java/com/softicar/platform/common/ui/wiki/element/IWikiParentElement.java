package com.softicar.platform.common.ui.wiki.element;

import java.util.Collection;

public interface IWikiParentElement extends IWikiElement {

	int size();

	IWikiElement getChild(int index);

	Collection<IWikiElement> getChildren();

	void addChild(IWikiElement child);

	void cleanup();
}
