package com.softicar.platform.common.io.classfile.constants;

import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constant.ClassFileConstantType;

public class ClassFileConstantNameAndType extends ClassFileConstant {

	private final int nameIndex;
	private final int descriptorIndex;

	public ClassFileConstantNameAndType(ClassFileReader reader) {

		super(ClassFileConstantType.NAME_AND_TYPE);

		this.nameIndex = reader.readInt16();
		this.descriptorIndex = reader.readInt16();
	}

	public int getNameIndex() {

		return nameIndex;
	}

	public int getDescriptorIndex() {

		return descriptorIndex;
	}
}
