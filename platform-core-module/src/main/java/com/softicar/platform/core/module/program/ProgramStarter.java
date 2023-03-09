package com.softicar.platform.core.module.program;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.date.ISOCalendar;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.log.LogDb;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUserScope;
import java.util.UUID;

/**
 * Main entry point for {@link IProgram} execution.
 * <p>
 * WARNING: DO NOT RENAME OR MOVE THIS FILE. The canonical name of this class is
 * referenced from outside the source code.
 *
 * @author Oliver Richers
 */
public class ProgramStarter {

	private final IProgram program;

	public ProgramStarter(UUID programUuid) {

		this(SourceCodeReferencePoints.getReferencePointOrThrow(programUuid, IProgram.class));
	}

	public ProgramStarter(IProgram program) {

		this.program = program;
	}

	public static void main(String[] arguments) throws Throwable {

		if (arguments.length >= 1) {
			new ProgramStarter(UUID.fromString(arguments[0])).start();
		} else {
			throw new SofticarUserException(CoreI18n.MISSING_PROGRAM_UUID);
		}
	}

	public void start() {

		try (var scope = new CurrentUserScope(AGUser.getSystemUser())) {
			prepareExecution();
			program.executeProgram();
		}
	}

	private void prepareExecution() {

		ISOCalendar.setDefaultTimeZone();
		LogDb.setProcessClass(program.getClass());
	}
}
