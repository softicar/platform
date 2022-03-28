package com.softicar.platform.demo.module.moment;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.demo.module.AGDemoModuleInstance;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.attribute.dependency.EmfAttributeDependencyMap;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoMomentTable extends EmfObjectTable<AGDemoMoment, AGDemoModuleInstance> {

	public AGDemoMomentTable(IDbObjectTableBuilder<AGDemoMoment> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoMoment, Integer, AGDemoModuleInstance> configuration) {

		configuration.setScopeField(AGDemoMoment.MODULE_INSTANCE);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGDemoMoment> attributes) {

		attributes//
			.editAttribute(AGDemoMoment.DAY)
			.setInputFactory(DemoMomentDayInput::new);
		attributes//
			.editAttribute(AGDemoMoment.POINT_IN_TIME)
			.setInputFactoryByEntity(DemoMomentPointInTimeInput::new);
	}

	@Override
	public void customizeAttributeDependencies(EmfAttributeDependencyMap<AGDemoMoment> dependencyMap) {

		dependencyMap//
			.editAttribute(AGDemoMoment.POINT_IN_TIME)
			.setDependsOn(AGDemoMoment.DAY);
	}
}
