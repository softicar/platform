package com.softicar.platform.emf.data.table.filter.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

class FilterElementList<R> {

	private final List<EmfDataTableFilterListElementDiv<R, ?>> elements = new ArrayList<>();

	public void add(EmfDataTableFilterListElementDiv<R, ?> filterElement) {

		elements.add(filterElement);
	}

	public List<EmfDataTableFilterListElementDiv<R, ?>> getSavedElements() {

		return getElementsByStates(FilterElementState.SAVED);
	}

	public List<EmfDataTableFilterListElementDiv<R, ?>> getSavedAndUnsavedElements() {

		return getElementsByStates(FilterElementState.SAVED, FilterElementState.UNSAVED);
	}

	public void removeElements(Collection<EmfDataTableFilterListElementDiv<R, ?>> filterElements) {

		if (filterElements != null) {
			elements.removeAll(filterElements);
		}
	}

	public void removeUnsavedElements() {

		setElements(getElementsByStates(FilterElementState.SAVED, FilterElementState.DELETED));
	}

	public void removeUnsavedAndDeletedElements() {

		setElements(getElementsByStates(FilterElementState.SAVED));
	}

	public void removeUnsavedAndRestoreDeletedElements() {

		removeUnsavedElements();

		for (EmfDataTableFilterListElementDiv<R, ?> filterElement: elements) {
			filterElement.setState(FilterElementState.SAVED);
		}
	}

	public void markUnsavedElementsAsSaved() {

		for (EmfDataTableFilterListElementDiv<R, ?> filterElement: elements) {
			if (filterElement.getState().equals(FilterElementState.UNSAVED)) {
				filterElement.setState(FilterElementState.SAVED);
			}
		}
	}

	private List<EmfDataTableFilterListElementDiv<R, ?>> getElementsByStates(FilterElementState...states) {

		Objects.requireNonNull(states);
		if (states.length == 0) {
			throw new IllegalArgumentException(String.format("Expected at least one %s.", FilterElementState.class.getSimpleName()));
		}
		TreeSet<FilterElementState> elementStates = new TreeSet<>(Arrays.asList(states));
		return elements.stream().filter(it -> elementStates.contains(it.getState())).collect(Collectors.toList());
	}

	private void setElements(List<EmfDataTableFilterListElementDiv<R, ?>> filterElements) {

		elements.clear();
		elements.addAll(filterElements);
	}
}
