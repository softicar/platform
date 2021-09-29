package com.softicar.platform.common.io.classfile.constants;

import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constant.ClassFileConstantType;

public class ClassFileConstantDouble extends ClassFileConstant {

	private final double value;

	public ClassFileConstantDouble(ClassFileReader reader) {

		super(ClassFileConstantType.DOUBLE);

		this.value = reader.readDouble();
	}

	public double getValue() {

		return value;
	}
}
