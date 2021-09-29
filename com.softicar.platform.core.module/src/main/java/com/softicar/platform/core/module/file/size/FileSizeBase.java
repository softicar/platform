package com.softicar.platform.core.module.file.size;

public enum FileSizeBase {

	BINARY(1024),
	DECIMAL(1000);

	private final int baseValue;

	private FileSizeBase(int baseValue) {

		this.baseValue = baseValue;
	}

	public int getBaseValue() {

		return baseValue;
	}
}
