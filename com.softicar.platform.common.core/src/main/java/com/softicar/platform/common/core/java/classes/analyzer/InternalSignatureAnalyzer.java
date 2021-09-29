package com.softicar.platform.common.core.java.classes.analyzer;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureVisitor;

class InternalSignatureAnalyzer extends SignatureVisitor {

	private final AnalyzedJavaClass javaClass;

	public InternalSignatureAnalyzer(AnalyzedJavaClass javaClass) {

		super(Opcodes.ASM9);
		this.javaClass = javaClass;
	}

	@Override
	public void visitClassType(String name) {

		javaClass.addReferencedClass(name);
	}
}
