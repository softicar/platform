package com.softicar.platform.common.io.classfile.constants;

import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constant.ClassFileConstantType;

public class ClassFileConstantMethodHandle extends ClassFileConstant {

	private final int referenceKind;
	private final int referenceIndex;

	public ClassFileConstantMethodHandle(ClassFileReader reader) {

		super(ClassFileConstantType.METHOD_HANDLE);

		this.referenceKind = reader.readInt8();
		this.referenceIndex = reader.readInt16();
	}

	public int getReferenceKind() {

		return referenceKind;
	}

	public int getReferenceIndex() {

		return referenceIndex;
	}
}
