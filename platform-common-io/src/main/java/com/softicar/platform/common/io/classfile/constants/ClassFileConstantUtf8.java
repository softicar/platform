package com.softicar.platform.common.io.classfile.constants;

import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constant.ClassFileConstantType;
import com.softicar.platform.common.string.unicode.Utf8Convering;

public class ClassFileConstantUtf8 extends ClassFileConstant {

	private final int length;
	private final byte[] bytes;
	private String text;

	public ClassFileConstantUtf8(ClassFileReader reader) {

		super(ClassFileConstantType.UTF8);

		this.length = reader.readInt16();
		this.bytes = reader.readBytes(length);
		this.text = null;
	}

	public int getLength() {

		return length;
	}

	public byte[] getBytes() {

		return bytes;
	}

	public String getText() {

		if (text == null) {
			this.text = Utf8Convering.fromUtf8(bytes);
		}
		return text;
	}
}
