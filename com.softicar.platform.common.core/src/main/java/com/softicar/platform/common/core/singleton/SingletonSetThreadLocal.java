package com.softicar.platform.common.core.singleton;

/**
 * An {@link InheritableThreadLocal} used in {@link CurrentSingletonSet}.
 *
 * @author Oliver Richers
 */
class SingletonSetThreadLocal extends InheritableThreadLocal<SingletonSet> {

	@Override
	protected SingletonSet initialValue() {

		return new SingletonSet();
	}

	@Override
	protected SingletonSet childValue(SingletonSet parentSet) {

		return new SingletonSet(parentSet);
	}
}
