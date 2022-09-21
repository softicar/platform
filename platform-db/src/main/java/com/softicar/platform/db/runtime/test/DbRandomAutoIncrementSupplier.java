package com.softicar.platform.db.runtime.test;

import java.util.Random;
import java.util.function.Supplier;

public class DbRandomAutoIncrementSupplier implements Supplier<Integer> {

	private static final int RANDOM_SEED = 12345;
	private static final int MAXIUM = 100;
	private final Random random;

	public DbRandomAutoIncrementSupplier() {

		this.random = new Random(RANDOM_SEED);
	}

	@Override
	public Integer get() {

		return random.nextInt(MAXIUM) + 1;
	}
}
