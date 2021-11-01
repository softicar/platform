package com.softicar.platform.core.module.log.cleanup;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.common.core.thread.sleeper.CurrentSleeper;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.login.AGUserLoginLog;
import com.softicar.platform.core.module.user.password.AGUserPassword;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.profiler.DbConnectionTestProfiler;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import org.junit.Test;

public class CoreLogRecordDeleterTest extends AbstractCoreTest {

	private final AGUser user;
	private final AGUserPassword password;
	private final DbConnectionTestProfiler profiler;

	public CoreLogRecordDeleterTest() {

		this.user = insertTestUser();
		this.password = insertPassword(user, "xxx");
		this.profiler = new DbConnectionTestProfiler();

		// suppress lower-level log output under test
		LogLevel.ERROR.set();
	}

	@Test
	public void testDelete() {

		Collection<AGUserLoginLog> retainableLogs = new TreeSet<>();
		retainableLogs.add(insertLog(Day.fromYMD(2020, 2, 29)));
		retainableLogs.add(insertLog(Day.fromYMD(2020, 2, 28)));
		retainableLogs.add(insertLog(Day.fromYMD(2020, 2, 27)));

		Collection<AGUserLoginLog> deletableLogs = new TreeSet<>();
		deletableLogs.add(insertLog(Day.fromYMD(2020, 2, 26)));
		deletableLogs.add(insertLog(Day.fromYMD(2020, 2, 25)));
		deletableLogs.add(insertLog(Day.fromYMD(2020, 2, 24)));

		DbConnections.setProfiler(profiler);

		new CoreLogRecordDeleter(Day.fromYMD(2020, 2, 27))//
			.delete(AGUserLoginLog.LOGIN_AT);

		assertDeleteStatement(1, Day.fromYMD(2020, 2, 25));
		assertDeleteStatement(2, Day.fromYMD(2020, 2, 26));
		assertDeleteStatement(3, Day.fromYMD(2020, 2, 27));

		List<AGUserLoginLog> logsFromDb = AGUserLoginLog.TABLE.loadAll();
		assertTrue(logsFromDb.containsAll(retainableLogs));
		assertTrue(Collections.disjoint(logsFromDb, deletableLogs));
	}

	private void assertDeleteStatement(int index, Day thresholdDay) {

		profiler.assertStatementMatches(index, "DELETE FROM.*WHERE.*loginAt.*<.*\\?.*", thresholdDay.toDayTime().toString());
	}

	@Test
	public void testDeleteThrottling() {

		// FIXME change test to work with TestClock and TestSleeper
		CurrentClock.reset();
		CurrentSleeper.reset();

		Collection<AGUserLoginLog> retainableLogs = new TreeSet<>();
		retainableLogs.add(insertLog(Day.fromYMD(2020, 2, 29)));
		retainableLogs.add(insertLog(Day.fromYMD(2020, 2, 28)));
		retainableLogs.add(insertLog(Day.fromYMD(2020, 2, 27)));

		Collection<AGUserLoginLog> deletableLogs = new TreeSet<>();
		deletableLogs.add(insertLog(Day.fromYMD(2020, 2, 26)));
		deletableLogs.add(insertLog(Day.fromYMD(2020, 2, 25)));
		deletableLogs.add(insertLog(Day.fromYMD(2020, 2, 24)));

		int throttlingMilliseconds = 200;

		long timeBefore = System.currentTimeMillis();
		new CoreLogRecordDeleter(Day.fromYMD(2020, 2, 27))//
			.setThrottlingMilliseconds(throttlingMilliseconds)
			.delete(AGUserLoginLog.LOGIN_AT);
		long timeAfter = System.currentTimeMillis();

		int expectedDeleteStatements = deletableLogs.size() - 1;
		assertTrue(timeAfter - timeBefore >= throttlingMilliseconds * expectedDeleteStatements);
	}

	private AGUserLoginLog insertLog(Day day) {

		return new AGUserLoginLog()//
			.setLoginAt(day.toDayTime())
			.setUser(password.getUser())
			.setPassword(password)
			.save();
	}
}
