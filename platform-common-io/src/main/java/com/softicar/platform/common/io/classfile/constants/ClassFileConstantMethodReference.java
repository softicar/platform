package com.softicar.platform.common.io.classfile.constants;

import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constant.ClassFileConstantType;

public class ClassFileConstantMethodReference extends ClassFileConstant {

	private final int classIndex;
	private final int nameAndTypeIndex;

	public ClassFileConstantMethodReference(ClassFileReader reader) {

		super(ClassFileConstantType.METHOD_REFERENCE);

		this.classIndex = reader.readInt16();
		this.nameAndTypeIndex = reader.readInt16();
	}

	public int getClassIndex() {

		return classIndex;
	}

	public int getNameAndTypeIndex() {

		return nameAndTypeIndex;
	}
}
