package com.softicar.platform.common.container.matrix;

import com.softicar.platform.common.container.matrix.AsciiTable.Alignment;

public class AsciiTableCell {

	private final String text;
	private final Alignment alignment;

	public AsciiTableCell(String text, Alignment alignment) {

		this.text = text;
		this.alignment = alignment;
	}

	public int getWidth() {

		return text.length();
	}

	public void append(StringBuilder sb, int width) {

		switch (alignment) {
		case ALIGN_LEFT:
			sb.append(text);
			generate(sb, ' ', width - text.length());
			break;
		case ALIGN_RIGHT:
			generate(sb, ' ', width - text.length());
			sb.append(text);
			break;
		case ALIGN_CENTER:
			int left = (width - text.length()) / 2;
			int right = width - left - text.length();
			generate(sb, ' ', left);
			sb.append(text);
			generate(sb, ' ', right);
			break;
		}
	}

	// TODO: use Padding.generate, etc.
	private static void generate(StringBuilder sb, char c, int width) {

		for (int i = 0; i != width; ++i) {
			sb.append(c);
		}
	}
}
