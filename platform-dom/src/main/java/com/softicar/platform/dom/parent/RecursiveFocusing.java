package com.softicar.platform.dom.parent;

import com.softicar.platform.dom.input.IDomInputNode;
import com.softicar.platform.dom.input.IDomStringInputNode;
import com.softicar.platform.dom.node.IDomNode;

/**
 * Sets the focus to the first input elements in the hierarchy.
 *
 * @author Oliver Richers
 */
class RecursiveFocusing {

	public static boolean focusFirst(IDomNode node) {

		if (node instanceof IDomStringInputNode) {
			((IDomInputNode) node).setFocus(true);
			return true;
		} else if (node instanceof IDomParentElement) {
			for (IDomNode child: ((IDomParentElement) node).getChildren()) {
				if (focusFirst(child)) {
					return true;
				}
			}
		}

		// nothing found to focus
		return false;
	}
}
