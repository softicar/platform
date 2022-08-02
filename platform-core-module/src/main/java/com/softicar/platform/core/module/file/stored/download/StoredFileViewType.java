package com.softicar.platform.core.module.file.stored.download;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreImages;
import java.util.regex.Pattern;

enum StoredFileViewType {

	// IMPORTANT: sort from most specific to least specific
	PDF(CoreImages.FILE_TYPE_PDF.getResource(), "application/pdf"),
	IMAGE(CoreImages.FILE_TYPE_IMAGE.getResource(), "image/.*"),
	TEXT(CoreImages.FILE_TYPE_TEXT.getResource(), "text/.*"),
	UNKNOWN(CoreImages.FILE_TYPE_UNKNOWN.getResource(), ".*");

	private final IResource icon;
	private final Pattern pattern;

	private StoredFileViewType(IResource icon, String regex) {

		this.icon = icon;
		this.pattern = Pattern.compile(regex);
	}

	public IResource getIcon() {

		return icon;
	}

	public static StoredFileViewType getByIdentifier(String mimeTypeIdentifier) {

		mimeTypeIdentifier = mimeTypeIdentifier.toLowerCase();

		for (StoredFileViewType type: values()) {
			if (type.pattern.matcher(mimeTypeIdentifier).matches()) {
				return type;
			}
		}

		return UNKNOWN;
	}
}
