package com.softicar.platform.common.io.classfile.constants;

import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constant.ClassFileConstantType;

public class ClassFileConstantMethodType extends ClassFileConstant {

	private final int descriptorIndex;

	public ClassFileConstantMethodType(ClassFileReader reader) {

		super(ClassFileConstantType.METHOD_TYPE);

		this.descriptorIndex = reader.readInt16();
	}

	public int getDescriptorIndex() {

		return descriptorIndex;
	}
}
