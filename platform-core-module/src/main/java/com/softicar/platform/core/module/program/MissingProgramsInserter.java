package com.softicar.platform.core.module.program;

import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.core.transaction.DbTransaction;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Ensures that every existing {@link IProgram} class has a persistent
 * {@link AGProgram} record.
 *
 * @author Oliver Richers
 */
public class MissingProgramsInserter {

	private Set<UUID> existingPrograms;

	public void insertMissingPrograms() {

		try (var transaction = new DbTransaction()) {
			this.existingPrograms = loadExistingPrograms();
			Programs.getAllPrograms().forEach(this::insertProgramRecordIfMissing);
			transaction.commit();
		}
	}

	private void insertProgramRecordIfMissing(IProgram program) {

		var programUuid = program.getAnnotatedUuid();
		if (!existingPrograms.contains(programUuid)) {
			AGProgram.loadOrInsert(AGUuid.getOrCreate(programUuid));
		}
	}

	private Set<UUID> loadExistingPrograms() {

		return AGProgram.TABLE//
			.loadAll()
			.stream()
			.map(AGProgram::getProgramUuid)
			.map(AGUuid::getUuid)
			.collect(Collectors.toSet());
	}
}
