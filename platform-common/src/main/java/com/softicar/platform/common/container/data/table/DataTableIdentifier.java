package com.softicar.platform.common.container.data.table;

import com.softicar.platform.common.string.hash.Hash;
import java.util.Objects;

public class DataTableIdentifier {

	private final String text;

	public DataTableIdentifier(String text) {

		this.text = Objects.requireNonNull(text).trim();
	}

	public boolean isPresent() {

		return !text.isEmpty();
	}

	public String getHash() {

		return Hash.SHA1.getHashStringLC(text);
	}

	@Override
	public String toString() {

		return text;
	}
}
