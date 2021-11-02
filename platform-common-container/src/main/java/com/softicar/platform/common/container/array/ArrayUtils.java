package com.softicar.platform.common.container.array;

import java.util.Collection;

public class ArrayUtils {

	public static int[] convertToIntArray(Collection<Integer> collection) {

		int[] array = new int[collection.size()];

		int i = 0;
		for (Integer integer: collection) {
			array[i] = integer;
			++i;
		}

		return array;
	}
}
