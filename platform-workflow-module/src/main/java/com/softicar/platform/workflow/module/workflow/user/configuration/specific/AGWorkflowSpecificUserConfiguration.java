package com.softicar.platform.workflow.module.workflow.user.configuration.specific;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AGWorkflowSpecificUserConfiguration extends AGWorkflowSpecificUserConfigurationGenerated
		implements IEmfObject<AGWorkflowSpecificUserConfiguration> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getUser()//
			.toDisplay()
			.concat(" :: ")
			.concat(getWorkflow().toDisplay());
	}

	public static Map<AGUser, AGWorkflowSpecificUserConfiguration> loadAll(AGWorkflow workflow) {

		return AGWorkflowSpecificUserConfiguration//
			.createSelect()
			.where(AGWorkflowSpecificUserConfiguration.WORKFLOW.isEqual(workflow))
			.orderBy(AGWorkflowSpecificUserConfiguration.ID)
			.stream()
			.collect(Collectors.toMap(AGWorkflowSpecificUserConfiguration::getUser, Function.identity()));
	}

	public static Collection<AGUser> loadSubscribedUsers(AGWorkflow workflow) {

		var configurations = loadAll(workflow);

		return AGUser//
			.getAllActive()
			.stream()
			.filter(user -> {
				var configuration = configurations.get(user);
				return configuration == null || configuration.isSubscribed();
			})
			.collect(Collectors.toList());
	}
}
