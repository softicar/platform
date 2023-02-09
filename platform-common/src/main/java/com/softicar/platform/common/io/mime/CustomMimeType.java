package com.softicar.platform.common.io.mime;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class CustomMimeType implements IMimeType {

	private final String identifier;

	public CustomMimeType(String identifier) {

		this.identifier = identifier;
	}

	@Override
	public String getIdentifier() {

		return identifier;
	}

	@Override
	public Collection<String> getParameters() {

		return Collections.emptyList();
	}

	@Override
	public Optional<String> getParameter(String parameter) {

		return Optional.empty();
	}
}
