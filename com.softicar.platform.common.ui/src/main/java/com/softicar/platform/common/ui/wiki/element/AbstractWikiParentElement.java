package com.softicar.platform.common.ui.wiki.element;

import com.softicar.platform.common.ui.wiki.element.block.WikiList;
import com.softicar.platform.common.ui.wiki.element.block.WikiListItem;
import com.softicar.platform.common.ui.wiki.element.block.WikiParagraph;
import com.softicar.platform.common.ui.wiki.element.block.WikiTable;
import com.softicar.platform.common.ui.wiki.element.block.WikiTableRow;
import com.softicar.platform.common.ui.wiki.element.inline.WikiText;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractWikiParentElement extends AbstractWikiElement implements IWikiParentElement {

	private final List<IWikiElement> children;

	public AbstractWikiParentElement() {

		this.children = new ArrayList<>();
	}

	@Override
	public String toString() {

		return new StringBuilder()//
			.append(getClass().getSimpleName())
			.append("(")
			.append(children.stream().map(Object::toString).collect(Collectors.joining(" ")))
			.append(")")
			.toString();
	}

	@Override
	public int size() {

		return children.size();
	}

	@Override
	public IWikiElement getChild(int index) {

		return children.get(index);
	}

	@Override
	public Collection<IWikiElement> getChildren() {

		return children;
	}

	@Override
	public void addChild(IWikiElement child) {

		// TODO this could be done better
		if (child instanceof WikiListItem && !(this instanceof WikiList)) {
			WikiListItem listItem = (WikiListItem) child;
			getOrAddChildOfType(//
				WikiList.class,
				() -> new WikiList(listItem),
				list -> list.isCompatible(listItem)).addChild(child);
		} else if (child instanceof WikiTableRow && !(this instanceof WikiTable)) {
			getOrAddChildOfType(//
				WikiTable.class,
				() -> new WikiTable()).addChild(child);
		} else {
			children.add(child);
		}
	}

	@Override
	public void cleanup() {

		// call cleanup on children
		getChildren()//
			.stream()
			.filter(IWikiParentElement.class::isInstance)
			.map(IWikiParentElement.class::cast)
			.forEach(IWikiParentElement::cleanup);

		// cleanup direct children
		if (isBlockElement()) {
			trimWhitespace();
			dropEmtpyElements();
		}
	}

	protected <E extends IWikiElement> E getOrAddChildOfType(Class<E> elementClass, Supplier<E> factory) {

		return getOrAddChildOfType(elementClass, factory, element -> true);
	}

	protected <E extends IWikiElement> E getOrAddChildOfType(Class<E> elementClass, Supplier<E> factory, Predicate<E> predicate) {

		if (size() > 0) {
			IWikiElement child = getChild(size() - 1);
			if (elementClass.isInstance(child)) {
				E element = elementClass.cast(child);
				if (predicate.test(element)) {
					return element;
				}
			}
		}
		E child = factory.get();
		children.add(child);
		return child;
	}

	private void trimWhitespace() {

		int n = children.size();
		for (int i = 0; i < n; i++) {
			IWikiElement child = children.get(i);
			if (child instanceof WikiText) {
				if (i == 0 || children.get(i - 1).isBlockElement()) {
					((WikiText) child).trimLeft();
				}
				if (i == n - 1 || children.get(i + 1).isBlockElement()) {
					((WikiText) child).trimRight();
				}
			}
		}
	}

	private void dropEmtpyElements() {

		int n = children.size();
		for (int i = 0; i < n; i++) {
			if (isEmpty(children.get(i))) {
				children.remove(i);
				n--;
			}
		}
	}

	private boolean isEmpty(IWikiElement element) {

		if (element instanceof WikiText) {
			return ((WikiText) element).getText().isEmpty();
		} else if (element instanceof WikiParagraph) {
			return ((WikiParagraph) element).size() == 0;
		} else {
			return false;
		}
	}
}
