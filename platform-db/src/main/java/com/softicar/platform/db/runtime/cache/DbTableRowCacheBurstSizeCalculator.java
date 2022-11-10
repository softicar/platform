package com.softicar.platform.db.runtime.cache;

import com.softicar.platform.common.date.DayTime;
import java.time.Duration;
import java.time.Instant;

class DbTableRowCacheBurstSizeCalculator {

	private static final int DEFAULT_MIN_BURST_SIZE = 1000;
	private static final int DEFAULT_MAX_BURST_SIZE = 1000000;
	private static final int DEFAULT_BURST_SIZE_FACTOR = 2;
	private static final Duration DEFAULT_BURST_SIZE_COOLDOWN = Duration.ofSeconds(3);

	private int minBurstSize;
	private int maxBurstSize;
	private int burstSizeFactor;
	private Duration burstSizeCooldown;

	private Instant lastRefresh;
	private Integer nextBurstSize;

	public DbTableRowCacheBurstSizeCalculator() {

		this.minBurstSize = DEFAULT_MIN_BURST_SIZE;
		this.maxBurstSize = DEFAULT_MAX_BURST_SIZE;
		this.burstSizeFactor = DEFAULT_BURST_SIZE_FACTOR;
		this.burstSizeCooldown = DEFAULT_BURST_SIZE_COOLDOWN;
		this.lastRefresh = Instant.ofEpochMilli(0);
		this.nextBurstSize = null;
	}

	public DbTableRowCacheBurstSizeCalculator setMinBurstSize(int minBurstSize) {

		this.minBurstSize = minBurstSize;
		return this;
	}

	public DbTableRowCacheBurstSizeCalculator setMaxBurstSize(int maxBurstSize) {

		this.maxBurstSize = maxBurstSize;
		return this;
	}

	public DbTableRowCacheBurstSizeCalculator setBurstSizeFactor(int burstSizeFactor) {

		this.burstSizeFactor = burstSizeFactor;
		return this;
	}

	public DbTableRowCacheBurstSizeCalculator setBurstSizeCooldown(Duration burstSizeCooldown) {

		this.burstSizeCooldown = burstSizeCooldown;
		return this;
	}

	public int calculateNextBurstSize() {

		beforeBurst();
		int size = nextBurstSize;
		afterBurst();
		return size;
	}

	private void beforeBurst() {

		if (isBurstSizeCooldownElapsed()) {
			resetNextBurstSize();
		}
	}

	private void afterBurst() {

		this.nextBurstSize = Math.min(nextBurstSize * burstSizeFactor, maxBurstSize);
		this.lastRefresh = DayTime.now().toInstant();
	}

	private boolean isBurstSizeCooldownElapsed() {

		return DayTime.now().toInstant().isAfter(lastRefresh.plus(burstSizeCooldown));
	}

	private void resetNextBurstSize() {

		this.nextBurstSize = minBurstSize;
	}
}
