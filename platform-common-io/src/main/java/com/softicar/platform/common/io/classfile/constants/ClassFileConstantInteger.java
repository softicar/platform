package com.softicar.platform.common.io.classfile.constants;

import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constant.ClassFileConstantType;

public class ClassFileConstantInteger extends ClassFileConstant {

	private final int value;

	public ClassFileConstantInteger(ClassFileReader reader) {

		super(ClassFileConstantType.INTEGER);

		this.value = reader.readInt32();
	}

	public int getValue() {

		return value;
	}
}
