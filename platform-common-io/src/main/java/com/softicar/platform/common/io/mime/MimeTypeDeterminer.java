package com.softicar.platform.common.io.mime;

import java.util.Optional;

class MimeTypeDeterminer {

	private final String filename;

	public MimeTypeDeterminer(String filename) {

		this.filename = filename;
	}

	public Optional<MimeType> getMimeType() {

		return MimeType.getByFilenameSuffix(getFilenameSuffix());
	}

	private String getFilenameSuffix() {

		int pointIndex = filename.lastIndexOf('.');
		if (pointIndex >= 0 && pointIndex + 1 < filename.length()) {
			return filename.substring(pointIndex + 1);
		} else {
			return "";
		}
	}
}
