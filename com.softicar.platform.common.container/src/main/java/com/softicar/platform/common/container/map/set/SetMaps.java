package com.softicar.platform.common.container.map.set;

import com.softicar.platform.common.container.map.list.ListTreeMap;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class SetMaps {

	public static <S extends Comparable<?>, T extends Comparable<T>> Map<T, S> toInvertedMap(SetMap<S, T> input, boolean allowCollisions) {

		Map<T, S> output = new TreeMap<>();

		for (Entry<S, TreeSet<T>> entry: input.entrySet()) {
			S inputKey = entry.getKey();

			for (T inputValue: entry.getValue()) {
				if (allowCollisions || !output.containsKey(inputValue)) {
					output.put(inputValue, inputKey);
				} else {
					throw new SofticarDeveloperException("Collision detected while inverting a %s (in collision-free mode).", SetMap.class.getSimpleName());
				}
			}
		}

		return output;
	}

	public static <S extends Comparable<? super S>, T extends Comparable<T>> SetMap<T, S> toInvertedSetMap(SetMap<S, T> input) {

		SetMap<T, S> output = new SetMap<>();

		for (Entry<S, TreeSet<T>> entry: input.entrySet()) {
			S inputKey = entry.getKey();

			for (T inputValue: entry.getValue()) {
				output.addToSet(inputValue, inputKey);
			}
		}

		return output;
	}

	public static <S extends Comparable<?>, T extends Comparable<T>> ListTreeMap<T, S> toListMap(SetMap<S, T> input) {

		ListTreeMap<T, S> output = new ListTreeMap<>();

		for (Entry<S, TreeSet<T>> entry: input.entrySet()) {
			S inputKey = entry.getKey();

			for (T inputValue: entry.getValue()) {
				output.addToList(inputValue, inputKey);
			}
		}

		return output;
	}
}
