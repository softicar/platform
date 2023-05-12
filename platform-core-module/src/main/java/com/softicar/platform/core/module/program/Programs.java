package com.softicar.platform.core.module.program;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.uuid.AGUuidBasedSourceCodeReferencePoint;
import com.softicar.platform.core.module.uuid.AGUuidBasedSourceCodeReferencePoints;
import java.util.Collection;
import java.util.UUID;

public class Programs {

	public static IDisplayString getProgramName(UUID uuid) {

		return SourceCodeReferencePoints.getReferencePointOrThrow(uuid).toDisplay();
	}

	public static Collection<IProgram> getAllPrograms() {

		return SourceCodeReferencePoints.getReferencePoints(IProgram.class);
	}

	public static Collection<AGUuidBasedSourceCodeReferencePoint> getAllProgramsAsIndirectEntities() {

		return AGUuidBasedSourceCodeReferencePoints.getAll(IProgram.class);
	}

	public static <P extends IProgram> void enqueueExecution(Class<P> programClass) {

		new ProgramEnqueuer<>(programClass).enqueue();
	}
}
