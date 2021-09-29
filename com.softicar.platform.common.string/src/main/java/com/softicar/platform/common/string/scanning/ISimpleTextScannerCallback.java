package com.softicar.platform.common.string.scanning;

public interface ISimpleTextScannerCallback {

	void consumeNormalText(String text);

	void consumeWhitespace(String whitespace);
}
