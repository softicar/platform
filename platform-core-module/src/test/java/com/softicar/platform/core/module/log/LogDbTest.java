package com.softicar.platform.core.module.log;

import com.softicar.platform.common.core.logging.LogBuffer;
import com.softicar.platform.common.core.logging.LogOutputScope;
import com.softicar.platform.core.module.log.configuration.CurrentLogDbConfiguration;
import com.softicar.platform.core.module.log.message.AGLogMessage;
import com.softicar.platform.core.module.test.AbstractSofticarDbTest;
import com.softicar.platform.core.module.test.LogDbTestConfiguration;
import com.softicar.platform.db.core.connection.connector.DbConnectionFailureException;
import com.softicar.platform.db.core.database.DbDatabaseScope;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.utils.DbAssertUtils;
import java.sql.SQLException;
import java.time.Duration;
import org.junit.Test;

public class LogDbTest extends AbstractSofticarDbTest {

	@Test
	public void testPanicWithConnectionFailureExceptionDuringUpTime() {

		clock.add(Duration.ofSeconds(1));

		LogDb.panic(new DbConnectionFailureException(new SQLException()));

		assertLogMessage();
	}

	@Test
	public void testPanicWithConnectionFailureException() {

		var throwable = new DbConnectionFailureException(new SQLException());
		try (LogOutputScope scope = new LogOutputScope(new LogBuffer())) {
			LogDb.panic(throwable);
		}

		String logText = assertLogMessage().getLogText();
		assertTrue(logText.contains(throwable.getClass().getCanonicalName()));
	}

	@Test
	public void testLoggingWithTransactionRollback() {

		try (DbTransaction transaction = new DbTransaction()) {
			LogDb.info("foo");
			transaction.rollback();
		}

		String logText = assertLogMessage().getLogText();
		assertEquals("foo", logText);
	}

	@Test
	public void testLogTest() {

		LogDb.info("foo");

		String logText = assertLogMessage().getLogText();
		assertEquals("foo", logText);
	}

	@Test
	public void testImplicitProcessClassName() {

		LogDb.info("foo");

		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		assertEquals(stackTrace[stackTrace.length - 1].getClassName(), assertLogMessage().getProcess().getClassName());
	}

	@Test
	public void testExplicitProcessClassName() {

		LogDb.setProcessClass(LogDbTest.class);
		LogDb.info("foo");

		assertEquals(LogDbTest.class.getCanonicalName(), assertLogMessage().getProcess().getClassName());
	}

	@Test
	public void testProcessClassNameOfChildThread() throws InterruptedException {

		LogDb.setProcessClass(LogDbTest.class);

		Thread thread = new Thread(() -> {//
			try (DbDatabaseScope scope = new DbDatabaseScope(testDatabase)) {
				CurrentLogDbConfiguration.set(new LogDbTestConfiguration());
				LogDb.info("foo");
			}
		});
		thread.start();
		thread.join();

		String processClassName = assertLogMessage().getProcess().getClassName();
		assertEquals(LogDbTest.class.getCanonicalName(), processClassName);
	}

	// ------------------------------ private ------------------------------ //

	private AGLogMessage assertLogMessage() {

		return DbAssertUtils.assertOne(AGLogMessage.TABLE);
	}
}
