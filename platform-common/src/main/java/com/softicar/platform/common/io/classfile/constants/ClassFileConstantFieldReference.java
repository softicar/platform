package com.softicar.platform.common.io.classfile.constants;

import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constant.ClassFileConstantType;

public class ClassFileConstantFieldReference extends ClassFileConstant {

	private final int classIndex;
	private final int nameAndTypeIndex;

	public ClassFileConstantFieldReference(ClassFileReader reader) {

		super(ClassFileConstantType.FIELD_REFERENCE);

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
