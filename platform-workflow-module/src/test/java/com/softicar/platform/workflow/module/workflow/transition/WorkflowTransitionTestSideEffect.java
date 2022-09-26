package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.interfaces.BiConsumers;
import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.workflow.module.test.WorkflowTestObject;
import com.softicar.platform.workflow.module.workflow.transition.side.effect.IWorkflowTransitionSideEffect;
import java.util.function.BiConsumer;

@SourceCodeReferencePointUuid("25cdaf2d-3056-4579-8e02-88fb543e175b")
public class WorkflowTransitionTestSideEffect implements IWorkflowTransitionSideEffect<WorkflowTestObject> {

	private static final Singleton<BiConsumer<WorkflowTestObject, AGWorkflowTransition>> CONSUMER = new Singleton<>(BiConsumers::noOperation);

	@Override
	public void execute(WorkflowTestObject object, AGWorkflowTransition transition) {

		CONSUMER.get().accept(object, transition);
	}

	@Override
	public Class<WorkflowTestObject> getValueClass() {

		return WorkflowTestObject.class;
	}

	public static void setConsumer(BiConsumer<WorkflowTestObject, AGWorkflowTransition> consumer) {

		CONSUMER.set(consumer);
	}
}
