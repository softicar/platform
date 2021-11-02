package com.softicar.platform.common.io.classfile.constants;

import com.softicar.platform.common.io.classfile.constant.ClassFileConstantType;

public class ClassFileConstant {

	private final ClassFileConstantType type;

	public ClassFileConstant(ClassFileConstantType type) {

		this.type = type;
	}

	public ClassFileConstantType getType() {

		return type;
	}
}
