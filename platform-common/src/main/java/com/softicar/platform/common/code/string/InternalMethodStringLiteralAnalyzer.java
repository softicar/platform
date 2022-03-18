package com.softicar.platform.common.code.string;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class InternalMethodStringLiteralAnalyzer extends MethodVisitor {

	private final ClassStringLiteralAnalyzerResult result;

	public InternalMethodStringLiteralAnalyzer(ClassStringLiteralAnalyzerResult result) {

		super(Opcodes.ASM9);
		this.result = result;
	}

	@Override
	public void visitLdcInsn(Object value) {

		if (value instanceof String) {
			result.addStringLiteral((String) value);
		}
		super.visitLdcInsn(value);
	}
}
