package com.softicar.platform.common.core.java.classes.analyzer;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;

class InternalFieldAnalyzer extends FieldVisitor {

	private final AnalyzedJavaClass javaClass;

	public InternalFieldAnalyzer(AnalyzedJavaClass javaClass) {

		super(Opcodes.ASM9);
		this.javaClass = javaClass;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {

		javaClass.addReferencesFromSignature(descriptor);
		return new InternalAnnotationAnalyzer(javaClass);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {

		javaClass.addReferencesFromSignature(descriptor);
		return new InternalAnnotationAnalyzer(javaClass);
	}
}
