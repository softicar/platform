package com.softicar.platform.core.module.file.size;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FileSizeScale {

	B("B", null),
	KB("kB", FileSizeBase.DECIMAL),
	KIB("KiB", FileSizeBase.BINARY),
	MB("MB", FileSizeBase.DECIMAL),
	MIB("MiB", FileSizeBase.BINARY),
	GB("GB", FileSizeBase.DECIMAL),
	GIB("GiB", FileSizeBase.BINARY),
	TB("TB", FileSizeBase.DECIMAL),
	TIB("TiB", FileSizeBase.BINARY),
	PB("PB", FileSizeBase.DECIMAL),
	PIB("PiB", FileSizeBase.BINARY),
	EB("EB", FileSizeBase.DECIMAL),
	EIB("EiB", FileSizeBase.BINARY),
	ZB("ZB", FileSizeBase.DECIMAL),
	ZIB("ZiB", FileSizeBase.BINARY),
	YB("YB", FileSizeBase.DECIMAL),
	YIB("YiB", FileSizeBase.BINARY);

	private final String label;
	private final FileSizeBase base;

	private FileSizeScale(String label, FileSizeBase base) {

		this.label = label;
		this.base = base;
	}

	public String getLabel() {

		return label;
	}

	public FileSizeBase getBase() {

		return base;
	}

	@Override
	public String toString() {

		return label;
	}

	// ----

	private static final List<FileSizeScale> BINARY_SCALES = createScaleList(FileSizeBase.BINARY);
	private static final List<FileSizeScale> DECIMAL_SCALES = createScaleList(FileSizeBase.DECIMAL);

	private static List<FileSizeScale> createScaleList(FileSizeBase base) {

		return Arrays
			.asList(values())//
			.stream()
			.filter(it -> (it.getBase() == null || it.getBase() == base))
			.collect(Collectors.toList());
	}

	public static List<FileSizeScale> getAllByBase(FileSizeBase base) {

		switch (base) {
		case BINARY:
			return BINARY_SCALES;
		case DECIMAL:
			return DECIMAL_SCALES;
		default:
			throw new SofticarUnknownEnumConstantException(base);
		}
	}
}
