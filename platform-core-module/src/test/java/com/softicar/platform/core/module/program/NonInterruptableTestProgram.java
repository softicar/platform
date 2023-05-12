package com.softicar.platform.core.module.program;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.common.core.utils.DevNull;
import java.util.UUID;

@TestingOnly
@SourceCodeReferencePointUuid("b54e2bbb-00a8-4a07-a538-d9aa1dc43d47")
public class NonInterruptableTestProgram implements IProgram {

	public static final UUID UUID = SourceCodeReferencePoints.getUuidOrThrow(NonInterruptableTestProgram.class);

	@Override
	public void executeProgram() {

		while (true) {
			try {
				// intentionally not using CurrentSleeper
				Thread.sleep(60 * 1000);
				break;
			} catch (InterruptedException exception) {
				// ignore any exception (e.g. InterrupedException) and start again
				DevNull.swallow(exception);
			}
		}
	}
}
