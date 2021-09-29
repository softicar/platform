package com.softicar.platform.dom.engine;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class DomNullExport extends AbstractDomExport {

	@Override
	public OutputStream openOutputStream() {

		return new ByteArrayOutputStream();
	}
}
