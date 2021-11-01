package com.softicar.platform.common.ui.wiki.element;

public abstract class AbstractWikiElement implements IWikiElement {

	@Override
	public String toString() {

		return getClass().getSimpleName() + "()";
	}
}
