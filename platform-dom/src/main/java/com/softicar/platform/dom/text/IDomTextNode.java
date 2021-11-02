package com.softicar.platform.dom.text;

import com.softicar.platform.dom.node.IDomNode;

/**
 * Interface of all text nodes.
 * 
 * @author Oliver Richers
 */
public interface IDomTextNode extends IDomNode {

	/**
	 * Returns the complete text of this text node.
	 * 
	 * @return the text of this node, never null
	 */
	String getText();
}
