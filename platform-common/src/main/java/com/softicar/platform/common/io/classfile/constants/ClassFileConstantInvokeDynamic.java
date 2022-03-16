package com.softicar.platform.common.io.classfile.constants;

import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constant.ClassFileConstantType;

public class ClassFileConstantInvokeDynamic extends ClassFileConstant {

	private final int bootstrapMethodAttributeIndex;
	private final int nameAndTypeIndex;

	public ClassFileConstantInvokeDynamic(ClassFileReader reader) {

		super(ClassFileConstantType.INVOKE_DYNAMIC);

		this.bootstrapMethodAttributeIndex = reader.readInt16();
		this.nameAndTypeIndex = reader.readInt16();
	}

	public int getBootstrapMethodAttributeIndex() {

		return bootstrapMethodAttributeIndex;
	}

	public int getNameAndTypeIndex() {

		return nameAndTypeIndex;
	}
}
