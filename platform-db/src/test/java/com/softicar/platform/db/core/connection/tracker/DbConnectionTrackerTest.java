package com.softicar.platform.db.core.connection.tracker;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.clock.TestClock;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class DbConnectionTrackerTest extends AbstractTest {

	private final TestClock clock;
	private final List<DbConnectionTrack> tracks;
	private final DbConnectionTracker tracker;

	public DbConnectionTrackerTest() {

		this.clock = new TestClock();
		this.tracks = new ArrayList<>();
		this.tracker = new DbConnectionTracker(tracks::addAll);

		CurrentClock.set(clock);
	}

	@After
	public void after() {

		CurrentClock.reset();
	}

	@Test
	public void testNotifyNewConnection() {

		int index1 = tracker.notifyNewConnection();
		int index2 = tracker.notifyNewConnection();

		// assert that connections get distinct indexes
		assertEquals(0, index1);
		assertEquals(1, index2);

		// assert nothing is logged
		assertTrue(tracks.isEmpty());
	}

	@Test
	public void testNotifyNewConnectionWithOldIdleConnection() {

		int oldConnectionIndex = createOldIdleConnection();
		tracker.notifyNewConnection();

		// assert that only a log for the old connection is created
		assertEquals(1, tracks.size());
		assertEquals(oldConnectionIndex, tracks.get(0).getConnectionIndex());
	}

	@Test
	public void testNotifyNewConnectionWithOldIdleConnectionAndMultipleCalls() {

		createOldIdleConnection();

		// assert something is logged on first call
		tracker.notifyNewConnection();
		assertEquals(1, tracks.size());

		// assert nothing is logged on second call
		tracker.notifyNewConnection();
		assertEquals(1, tracks.size());

		// increase time and assert something is logged again
		clock.addMillis(DbConnectionTracker.FULL_LOG_INTERVAL + 1);
		tracker.notifyNewConnection();
		assertEquals(2, tracks.size());
	}

	@Test
	public void testNotifyConnectionUsedWithOldIdleConnection() {

		int index = createOldIdleConnection();
		tracker.notifyConnectionUsed(index);
		tracker.notifyNewConnection();

		// assert nothing is logged (old connection was used)
		assertTrue(tracks.isEmpty());
	}

	private int createOldIdleConnection() {

		int index = tracker.notifyNewConnection();
		clock.addMillis(DbConnectionTracker.MAX_CONNECTION_IDLE_MILLIS + 1);
		return index;
	}
}
