package com.softicar.platform.core.module.transaction.data;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.event.AGSystemEvent;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.core.module.maintenance.state.AGMaintenanceStateEnum;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class TransactionDataDeleterTest extends AbstractDbTest implements CoreModuleTestFixtureMethods {

	public TransactionDataDeleterTest() {

		insertDummyServer();
		insertSystemErrorEvent("Test Error");
		insertMaintenanceWindow(DayTime.now(), DayTime.now(), AGMaintenanceStateEnum.IN_PROGRESS);
	}

	@Test
	public void testExecute() {

		assertOne(AGUser.TABLE.loadAll());
		assertOne(AGServer.TABLE.loadAll());
		assertOne(AGSystemEvent.TABLE.loadAll());
		assertOne(AGMaintenanceWindow.TABLE.loadAll());

		new TransactionDataDeleter().execute();

		assertOne(AGUser.TABLE.loadAll());
		assertOne(AGServer.TABLE.loadAll());
		assertEmpty(AGSystemEvent.TABLE.loadAll());
		assertEmpty(AGMaintenanceWindow.TABLE.loadAll());
	}
}
