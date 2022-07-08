package com.softicar.platform.emf.test.module.alpha;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.emf.module.AbstractEmfModule;
import java.util.Collection;
import java.util.Optional;

@TestingOnly
@SourceCodeReferencePointUuid(TestModuleAlpha.UUID)
public class TestModuleAlpha extends AbstractEmfModule<TestModuleAlphaInstance> {

	public static final String UUID = "18e0984b-c8ae-4ab8-b431-c831a23c6d19";

	@Override
	public Collection<TestModuleAlphaInstance> getActiveModuleInstances() {

		return TestModuleAlphaInstance.TABLE.loadAll();
	}

	@Override
	public Optional<TestModuleAlphaInstance> getModuleInstance(Integer moduleInstanceId) {

		return Optional.of(TestModuleAlphaInstance.TABLE.get(moduleInstanceId));
	}
}
