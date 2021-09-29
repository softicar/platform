package com.softicar.platform.common.io.classfile.constants;

import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constant.ClassFileConstantType;

public class ClassFileConstantClass extends ClassFileConstant {

	private final int nameIndex;

	public ClassFileConstantClass(ClassFileReader reader) {

		super(ClassFileConstantType.CLASS);

		this.nameIndex = reader.readInt16();
	}

	public int getNameIndex() {

		return nameIndex;
	}
}
