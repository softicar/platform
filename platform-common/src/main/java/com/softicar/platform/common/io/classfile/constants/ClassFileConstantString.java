package com.softicar.platform.common.io.classfile.constants;

import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constant.ClassFileConstantType;

public class ClassFileConstantString extends ClassFileConstant {

	private final int stringIndex;

	public ClassFileConstantString(ClassFileReader reader) {

		super(ClassFileConstantType.STRING);

		this.stringIndex = reader.readInt16();
	}

	public int getStringIndex() {

		return stringIndex;
	}
}
