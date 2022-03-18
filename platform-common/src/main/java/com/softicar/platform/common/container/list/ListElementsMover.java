package com.softicar.platform.common.container.list;

import com.softicar.platform.common.container.pair.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class reorders elements of a list in-place.
 */
public class ListElementsMover<T> {

	private final List<T> list;
	private final Set<T> movingElementsSet;
	private final Collection<T> movingElements;
	private final Collection<T> staticElements;

	/**
	 * Constructs this mover.
	 * <p>
	 * The given elements to move may be part of the list or not. If they are
	 * already part of the list, they will be moved. If they are not part of the
	 * list, they will be inserted.
	 *
	 * @param list
	 *            the list to reorder (never null)
	 * @param elements
	 *            the list elements to move (never null)
	 */
	public ListElementsMover(List<T> list, Collection<T> elements) {

		this.list = list;
		this.movingElementsSet = new HashSet<>(elements);
		this.movingElements = elements;
		this.staticElements = getStaticElements();
	}

	/**
	 * Moves the elements to the front of the list.
	 */
	public void moveToFront() {

		list.clear();
		list.addAll(movingElements);
		list.addAll(staticElements);
	}

	/**
	 * Moves the elements to the back of the list.
	 */
	public void moveToBack() {

		list.clear();
		list.addAll(staticElements);
		list.addAll(movingElements);
	}

	/**
	 * Moves the elements in front of the given anchor element.
	 * <p>
	 * If the anchor element is not part of the list, the elements will be moved
	 * to the back of the list, with the anchor at the end.
	 * <p>
	 * If the anchor element is part of the moving elements, the list of moving
	 * elements, excluding the anchor element, will be moved in front of the
	 * anchor element.
	 */
	public void moveInFrontOf(T anchor) {

		Pair<List<T>, List<T>> partitions = getPartitions(anchor);
		list.clear();
		list.addAll(partitions.getFirst());
		addMovingElementsExepectAnchor(anchor);
		list.add(anchor);
		list.addAll(partitions.getSecond());
	}

	/**
	 * Moves the elements behind the given anchor element.
	 * <p>
	 * If the anchor element is not part of the list, the elements will be moved
	 * to the back of the list, with the anchor in front.
	 * <p>
	 * If the anchor element is part of the moving elements, the list of moving
	 * elements, excluding the anchor element, will be moved behind the anchor
	 * element.
	 */
	public void moveBehind(T anchor) {

		Pair<List<T>, List<T>> partitions = getPartitions(anchor);
		list.clear();
		list.addAll(partitions.getFirst());
		list.add(anchor);
		addMovingElementsExepectAnchor(anchor);
		list.addAll(partitions.getSecond());
	}

	// ------------------------------ private ------------------------------ //

	private void addMovingElementsExepectAnchor(T anchor) {

		if (movingElementsSet.contains(anchor)) {
			movingElements.stream().filter(it -> !it.equals(anchor)).forEach(list::add);
		} else {
			list.addAll(movingElements);
		}
	}

	private List<T> getStaticElements() {

		return list//
			.stream()
			.filter(it -> !movingElementsSet.contains(it))
			.collect(Collectors.toList());
	}

	private Pair<List<T>, List<T>> getPartitions(T anchor) {

		List<T> inFront = new ArrayList<>(list.size());
		List<T> behind = new ArrayList<>(list.size());
		List<T> currentParition = inFront;
		for (T element: list) {
			if (element.equals(anchor)) {
				currentParition = behind;
			} else {
				if (!movingElementsSet.contains(element)) {
					currentParition.add(element);
				}
			}
		}
		return new Pair<>(inFront, behind);
	}
}
