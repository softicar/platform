package com.softicar.platform.common.io.mime;

public class CustomMimeType implements IMimeType {

	private final String identifier;

	public CustomMimeType(String identifier) {

		this.identifier = identifier;
	}

	@Override
	public String getIdentifier() {

		return identifier;
	}

}
