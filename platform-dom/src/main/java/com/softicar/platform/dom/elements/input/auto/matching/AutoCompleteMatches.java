package com.softicar.platform.dom.elements.input.auto.matching;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class AutoCompleteMatches<V> implements IAutoCompleteMatches<V> {

	private final List<AutoCompleteMatch<V>> matches;
	private V perfectMatchValue;

	public AutoCompleteMatches() {

		this.matches = new ArrayList<>();
		this.perfectMatchValue = null;
	}

	@Override
	public List<AutoCompleteMatch<V>> getAll() {

		return matches;
	}

	@Override
	public Optional<V> getPerfectMatchValue() {

		return Optional.ofNullable(perfectMatchValue);
	}

	@Override
	public boolean isEmpty() {

		return matches.isEmpty();
	}

	@Override
	public int size() {

		return matches.size();
	}

	public AutoCompleteMatches<V> add(AutoCompleteMatch<V> match) {

		matches.add(match);
		return this;
	}

	public void setPerfectMatchValue(V perfectMatchValue) {

		this.perfectMatchValue = perfectMatchValue;
	}
}
