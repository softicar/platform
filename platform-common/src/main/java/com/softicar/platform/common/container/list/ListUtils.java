package com.softicar.platform.common.container.list;

import java.util.Collection;
import java.util.List;

public class ListUtils {

	public static List<String> convertToString(Collection<?> collection) {

		List<String> strings = ListFactory.createArrayList();

		for (Object object: collection) {
			strings.add(object != null? object.toString() : "null");
		}

		return strings;
	}
}
