package com.softicar.platform.core.module.server;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGServerTable extends EmfObjectTable<AGServer, AGCoreModuleInstance> {

	public AGServerTable(IDbObjectTableBuilder<AGServer> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGServer, Integer, AGCoreModuleInstance> configuration) {

		configuration.setIcon(CoreImages.EMAIL_SERVER);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGServer> attributes) {

		attributes//
			.editAttribute(AGServer.NAME)
			.setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editAttribute(AGServer.ADDRESS)
			.setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editAttribute(AGServer.PORT)
			.setPredicateMandatory(EmfPredicates.always());
		attributes//
			.editStringAttribute(AGServer.PASSWORD)
			.setPasswordMode(true);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGServer> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGServerLog.SERVER, AGServerLog.TRANSACTION)
			.addMapping(AGServer.NAME, AGServerLog.NAME)
			.addMapping(AGServer.ADDRESS, AGServerLog.ADDRESS)
			.addMapping(AGServer.PORT, AGServerLog.PORT)
			.addMapping(AGServer.USERNAME, AGServerLog.USERNAME)
			.addMapping(AGServer.PASSWORD, AGServerLog.PASSWORD)
			.addMapping(AGServer.ACTIVE, AGServerLog.ACTIVE)
			.addMapping(AGServer.DOMAIN, AGServerLog.DOMAIN);
	}
}
