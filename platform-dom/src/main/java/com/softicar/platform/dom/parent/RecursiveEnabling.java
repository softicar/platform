package com.softicar.platform.dom.parent;

import com.softicar.platform.dom.input.IDomEnableable;
import com.softicar.platform.dom.node.IDomNode;

/**
 * Sets the enabled state of all input elements in the hierarchy.
 * 
 * @author Oliver Richers
 */
class RecursiveEnabling {

	public static void setEnabled(IDomNode node, boolean enabled) {

		if (node instanceof IDomEnableable) {
			((IDomEnableable) node).setEnabled(enabled);
		} else if (node instanceof IDomParentElement) {
			for (IDomNode child: ((IDomParentElement) node).getChildren()) {
				setEnabled(child, enabled);
			}
		}
	}
}
