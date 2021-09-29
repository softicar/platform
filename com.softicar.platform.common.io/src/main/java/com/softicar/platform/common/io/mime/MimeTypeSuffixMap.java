package com.softicar.platform.common.io.mime;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

class MimeTypeSuffixMap {

	private static final MimeTypeSuffixMap INSTANCE = new MimeTypeSuffixMap();
	private final Map<String, MimeType> map;

	private MimeTypeSuffixMap() {

		this.map = new TreeMap<>();

		List.of(MimeType.values()).forEach(this::put);
	}

	public static MimeTypeSuffixMap getInstance() {

		return INSTANCE;
	}

	public Optional<MimeType> getMimeTypeForSuffix(String suffix) {

		return Optional.ofNullable(map.get(normalize(suffix)));
	}

	private void put(MimeType mimeType) {

		mimeType.getFilenameSuffixes().forEach(suffix -> put(suffix, mimeType));
	}

	private void put(String suffix, MimeType mimeType) {

		suffix = normalize(suffix);

		if (suffix.isEmpty()) {
			throw new SofticarDeveloperException(//
				"Blank filename suffixes are not allowed for MIME types. Offending enumerator: %s",
				mimeType.name());
		}

		MimeType otherType = map.put(suffix, mimeType);

		if (otherType != null) {
			throw new SofticarDeveloperException(//
				"Both MIME types declare the same filename suffix '%s'. Offending enumerator: %s and %s",
				suffix,
				otherType,
				mimeType);
		}
	}

	private static final String normalize(String suffix) {

		return suffix.trim().toLowerCase();
	}
}
