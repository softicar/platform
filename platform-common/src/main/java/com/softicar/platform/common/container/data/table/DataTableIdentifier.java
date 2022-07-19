package com.softicar.platform.common.container.data.table;

import java.util.Objects;

public class DataTableIdentifier {

	private final String text;

	public DataTableIdentifier(String text) {

		this.text = Objects.requireNonNull(text).trim();
	}

	public boolean isPresent() {

		return !text.isEmpty();
	}

	@Override
	public String toString() {

		return text;
	}
}
