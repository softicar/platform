package com.softicar.platform.emf.test.module.beta;

import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.emf.module.AbstractEmfModule;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.util.Collection;
import java.util.Optional;

@TestingOnly
@EmfSourceCodeReferencePointUuid(TestModuleBeta.UUID)
public class TestModuleBeta extends AbstractEmfModule<TestModuleBetaInstance> {

	public static final String UUID = "e43ac392-a29a-46d9-a05a-3ebea8b7c78a";

	@Override
	public Collection<TestModuleBetaInstance> getActiveModuleInstances() {

		return TestModuleBetaInstance.TABLE.loadAll();
	}

	@Override
	public Optional<TestModuleBetaInstance> getModuleInstance(Integer moduleInstanceId) {

		return Optional.of(TestModuleBetaInstance.TABLE.get(moduleInstanceId));
	}
}
