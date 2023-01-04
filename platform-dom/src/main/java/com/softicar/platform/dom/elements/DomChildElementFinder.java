package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.DomParentElement;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class DomChildElementFinder {

	public static <T extends IDomNode> List<T> getChildrenWithClass(IDomParentElement parent, Class<T> childClass) {

		return getChildrenWithClass(parent, childClass, null, null);
	}

	public static <T extends IDomNode> List<T> getChildrenWithClass(IDomParentElement parent, Class<T> childClass, Collection<Integer> skipChildIndexes,
			List<Class<? extends T>> ignoreChildSubClasses) {

		Objects.requireNonNull(parent);
		Objects.requireNonNull(childClass);

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
