package com.softicar.platform.core.module.tinymce;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.core.module.web.library.WebLibrary;

@SourceCodeReferencePointUuid("12ddc505-76c1-4e7f-957d-5f5480e22414")
public class TinyMceLibrary extends WebLibrary {

	private static final String ROOT = "/META-INF/resources/webjars/tinymce/6.3.1";

	public TinyMceLibrary() {

		super(ROOT);
	}
}
