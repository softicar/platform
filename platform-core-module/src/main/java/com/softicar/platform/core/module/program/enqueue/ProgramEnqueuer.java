package com.softicar.platform.core.module.program.enqueue;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.core.transaction.DbTransaction;
import java.util.UUID;

public class ProgramEnqueuer {

	private final UUID programUuid;

	public ProgramEnqueuer(AGProgram program) {

		this(program.getProgramUuid().getUuid());
	}

	public <P extends IProgram> ProgramEnqueuer(Class<P> programClass) {

		this(SourceCodeReferencePoints.getUuidOrThrow(programClass));
	}

	private ProgramEnqueuer(UUID programUuid) {

		this.programUuid = programUuid;
	}

	public void enqueueProgram() {

		try (var transaction = new DbTransaction()) {
			var uuid = AGUuid.getOrCreate(programUuid);
			var program = AGProgram.loadOrInsert(uuid);
			program.lockProgramState();
			if (!program.isQueued()) {
				program//
					.getState()
					.setQueuedAt(DayTime.now().truncateSeconds())
					.setQueuedBy(CurrentUser.get())
					.save();
			}
			transaction.commit();
		}
	}
}
