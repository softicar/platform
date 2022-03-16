package com.softicar.platform.common.io.classfile.constants;

import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constant.ClassFileConstantType;

public class ClassFileConstantFloat extends ClassFileConstant {

	private final float value;

	public ClassFileConstantFloat(ClassFileReader reader) {

		super(ClassFileConstantType.FLOAT);

		this.value = reader.readFloat();
	}

	public float getValue() {

		return value;
	}
}
