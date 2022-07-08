package com.softicar.platform.core.module.program;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.common.core.thread.sleeper.DefaultSleeper;

@TestingOnly
@SourceCodeReferencePointUuid("ef311712-c13a-46f7-abb5-4f593ae8acdd")
public class TestProgram implements IProgram {

	@Override
	public void executeProgram() {

		// intentionally not using CurrentSleeper
		new DefaultSleeper().sleep(10000);
	}
}
