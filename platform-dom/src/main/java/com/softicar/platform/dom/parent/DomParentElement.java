package com.softicar.platform.dom.parent;

import com.softicar.platform.common.container.list.ListFactory;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.element.DomElement;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.element.DomSimpleElement;
import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.text.DomTextNode;
import com.softicar.platform.dom.text.IDomTextNode;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a tree node which can have a list of child nodes.
 *
 * @author Oliver Richers
 */
public abstract class DomParentElement extends DomElement implements IDomParentElement {

	private Object children;

	protected DomParentElement() {

		super(true);
	}

	protected DomParentElement(boolean doCreate) {

		super(doCreate);
	}

	protected DomParentElement(IDomDocument document, boolean doCreate) {

		super(document, doCreate);
	}

	// -------------------------------- BASIC TREE METHODS -------------------------------- //

	@Override
	public <T extends IDomNode> T insertAt(T child, int index) {

		int n = getChildCount();

		// check
		if (child.getParent() != null) {
			throw new SofticarDeveloperException("Trying to add a child that already has a parent.");
		}
		if (index < 0 || index > n) {
			throw new SofticarDeveloperException("Index %s out of range [0, %s] while inserting child node.", index, n);
		}

		// register child
		List<IDomNode> list = null;
		if (children == null) {
			children = child;
		} else {
			if (children instanceof IDomNode) {
				list = ListFactory.createArrayList(2);
				list.add((IDomNode) children);
				children = list;
			} else {
				list = castChildrenToList();
			}

			list.add(index, child);
		}

		// set new parent of child
		child.getAccessor().setParent(this);

		// change in JavaScript
		if (index == n) {
			getDomEngine().appendChild(this, child);
		} else if (list != null) {
			getDomEngine().insertBefore(this, child, list.get(index + 1));
		} else {
			throw new SofticarDeveloperException("Internal program error while inserting child.");
		}
		return child;
	}

	@Override
	public <T extends IDomNode> T replaceChild(T newChild, IDomNode oldChild) {

		// check
		if (newChild.getParent() != null) {
			throw new SofticarDeveloperException("Tried to append child node that already has a parent.");
		}
		if (oldChild.getParent() != this) {
			throw new SofticarDeveloperException("Tried to replace child node (ID %d) that is not a child of this node", oldChild.getNodeId());
		}

		// change in vector
		int index = getChildIndex(oldChild);
		if (children instanceof IDomNode) {
			children = newChild;
		} else {
			castChildrenToList().set(index, newChild);
		}

		// set/clear parent
		oldChild.getAccessor().setParent(null);
		newChild.getAccessor().setParent(this);

		// change in JavaScript
		getDomEngine().replaceChild(this, newChild, oldChild);
		return newChild;
	}

	@Override
	public void removeChild(IDomNode child) {

		if (child.getParent() != this) {
			throw new SofticarDeveloperException("Tried to remove child node that is not a child of this node.");
		}

		// remove from list
		if (children == child) {
			children = null;
			child.getAccessor().setParent(null);
		} else {
			Iterator<IDomNode> it = castChildrenToList().iterator();
			while (it.hasNext()) {
				IDomNode node = it.next();
				if (node == child) {
					child.getAccessor().setParent(null);
					it.remove();
					break;
				}
			}

			// check if child was found
			if (child.getParent() != null) {
				throw new SofticarDeveloperException("Failed to find child in dom element.");
			}
		}

		// change in JavaScript
		getDomEngine().removeChild(this, child);
	}

	@Override
	public void removeChildren() {

		if (children != null) {
			IDomEngine engine = getDomEngine();
			if (children instanceof IDomNode) {
				IDomNode child = (IDomNode) children;
				child.getAccessor().setParent(null);
				engine.removeChild(this, child);
			} else {
				for (IDomNode child: castChildrenToList()) {
					child.getAccessor().setParent(null);
					engine.removeChild(this, child);
				}
			}

			// reset children
			children = null;
		}
	}

	// -------------------------------- CONVENIENCE TREE METHODS -------------------------------- //

	@Override
	public <T extends IDomNode> T appendChild(T child) {

		return insertAt(child, getChildCount());
	}

	@Override
	public void appendChild(Object child) {

		if (child instanceof IDomNode) {
			appendChild((IDomNode) child);
		} else if (child instanceof String) {
			appendText((String) child);
		} else {
			appendText("" + child);
		}
	}

	@Override
	public void appendChildren(Object...children) {

		for (Object child: children) {
			appendChild(child);
		}
	}

	@Override
	public <T extends IDomNode> T prependChild(T child) {

		return insertAt(child, 0);
	}

	@Override
	public <T extends IDomNode> T insertBefore(T child, IDomNode otherChild) {

		return insertAt(child, getChildIndex(otherChild));
	}

	@Override
	public <T extends IDomNode> T insertAfter(T child, IDomNode otherChild) {

		return insertAt(child, getChildIndex(otherChild) + 1);
	}

	@Override
	public void removeChildrenAfter(IDomNode child) {

		int index = getChildIndex(child);
		for (int i = getChildCount() - 1; i > index; --i) {
			removeChild(getChild(i));
		}
	}

	@Override
	public boolean hasChild(IDomNode child) {

		return child.getParent() == this;
	}

	@Override
	public IDomTextNode appendText(String text) {

		return appendChild(DomTextNode.create(text));
	}

	@Override
	public IDomTextNode prependText(String text) {

		return prependChild(DomTextNode.create(text));
	}

	@Override
	public IDomTextNode appendText(String text, Object...args) {

		return appendText(String.format(text, args));
	}

	@Override
	public DomSimpleElement appendNewChild(DomElementTag tag) {

		return appendChild(new DomSimpleElement(tag));
	}

	// -------------------------------- NON-MUTATING TREE METHODS -------------------------------- //

	@Override
	public int getChildCount() {

		if (children == null) {
			return 0;
		} else if (children instanceof IDomNode) {
			return 1;
		} else {
			return castChildrenToList().size();
		}
	}

	@Override
	public int getChildIndex(IDomNode child) {

		int i = 0;
		for (IDomNode node: getChildren()) {
			if (node == child) {
				return i;
			} else {
				++i;
			}
		}
		throw new SofticarDeveloperException("Failed to find child in dom element.");
	}

	@Override
	public IDomNode getChild(int index) {

		return getChildren().get(index);
	}

	@Override
	public List<IDomNode> getChildren() {

		if (children == null) {
			return Collections.emptyList();
		} else if (children instanceof IDomNode) {
			return Collections.singletonList((IDomNode) children);
		} else {
			return castChildrenToList();
		}
	}

	// -------------------------------- HTML -------------------------------- //

	protected void buildChildrenHTML(Appendable out) throws IOException {

		for (IDomNode child: getChildren()) {
			child.buildHtml(out);
		}
	}

	/**
	 * Builds HTML code representing this {@link DomParentElement} and all its
	 * child nodes.
	 *
	 * @param out
	 *            the append-able object for outputting the HTML code
	 */
	@Override
	public void buildHtml(Appendable out) throws IOException {

		out.append("<" + getTag().getName());

		buildAttributesHTML(out);

		if (getTag().getBlockType() == HierarchyType.PARENT) {
			out.append(">");
			buildChildrenHTML(out);
			out.append("</" + getTag().getName() + ">");
		} else {
			out.append("/>");
		}
	}

	// -------------------------------- PRIVATE FUNCTIONS -------------------------------- //

	private List<IDomNode> castChildrenToList() {

		return CastUtils.cast(children);
	}
}
