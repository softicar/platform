package com.softicar.platform.dom.elements;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.DomParentElement;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DomChildElementFinder {

	public static List<AbstractDomCell> getCells(DomRow row) {

		return DomChildElementFinder.getChildrenByClass(row, AbstractDomCell.class, null, null);
	}

	public static <T extends IDomNode> List<T> getChildrenByClass(IDomParentElement parent, Class<T> childClass, Collection<Integer> skipChildIndexes,
			List<Class<? extends T>> ignoreChildSubClasses) {

		if (childClass != null) {
			if (parent != null) {
				List<T> filteredChildren = new ArrayList<>();
				List<IDomNode> children = parent.getChildren();

				if (ignoreChildSubClasses == null) {
					ignoreChildSubClasses = new ArrayList<>();
				}

				for (int i = 0; i < children.size(); i++) {
					IDomNode child = children.get(i);

					if (child != null && childClass.isInstance(child) && !isIndexSkipped(skipChildIndexes, i)) {
						if (!ignoreChildSubClasses.contains(child.getClass())) {
							filteredChildren.add(childClass.cast(child));
						}
					}
				}

				return filteredChildren;
			}

			else {
				throw new SofticarDeveloperException("The given parent element must not be null.");
			}
		}

		else {
			throw new SofticarDeveloperException("The given child class must not be null.");
		}
	}

	public static List<IDomNode> fetchChildrenAtIndexPath(IDomNode parent, Integer...indexPath) {

		List<IDomNode> children = ((DomParentElement) parent).getChildren();

		if (indexPath.length > 0) {
			Integer firstChildIndex = indexPath[0];
			IDomNode firstChild = children.get(firstChildIndex);
			return fetchChildrenAtIndexPath(firstChild, Arrays.copyOfRange(indexPath, 1, indexPath.length));
		} else {
			return children;
		}
	}

	private static boolean isIndexSkipped(Collection<Integer> skipElementIndexes, int index) {

		return skipElementIndexes != null && skipElementIndexes.contains(index);
	}
}
