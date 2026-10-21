package com.softicar.platform.core.module.tinymce;

import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class TinyMceLibraryTest extends Asserts {

	@Test
	public void testScriptIsAvailable() {

		assertNotNull(//
			"TinyMCE script not found in class path. Does TinyMceLibrary.ROOT match the TinyMCE version in build.gradle?",
			TinyMceLibrary.class.getResource(TinyMceLibrary.ROOT + "/tinymce.min.js"));
	}
}
