package com.softicar.platform.dom.elements.input.auto.matching;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class DomAutoCompleteMatches<V> implements IDomAutoCompleteMatches<V> {

	private final List<DomAutoCompleteMatch<V>> matches;
	private V perfectMatchValue;

	public DomAutoCompleteMatches() {

		this.matches = new ArrayList<>();
		this.perfectMatchValue = null;
	}

	@Override
	public List<DomAutoCompleteMatch<V>> getAll() {

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

	public DomAutoCompleteMatches<V> add(DomAutoCompleteMatch<V> match) {

		matches.add(match);
		return this;
	}

	public void setPerfectMatchValue(V perfectMatchValue) {

		this.perfectMatchValue = perfectMatchValue;
	}
}
