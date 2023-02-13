package com.softicar.platform.core.module.program;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.common.core.thread.sleeper.DefaultSleeper;
import java.util.UUID;

@TestingOnly
@SourceCodeReferencePointUuid("ef311712-c13a-46f7-abb5-4f593ae8acdd")
public class TestProgram implements IProgram {

	public static final UUID UUID = SourceCodeReferencePoints.getUuidOrThrow(TestProgram.class);

	@Override
	public void executeProgram() {

		// intentionally not using CurrentSleeper
		new DefaultSleeper().sleep(10000);
	}
}
