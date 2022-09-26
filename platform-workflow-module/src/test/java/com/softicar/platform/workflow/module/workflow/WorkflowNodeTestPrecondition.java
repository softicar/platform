package com.softicar.platform.workflow.module.workflow;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.Predicates;
import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.workflow.module.test.WorkflowTestObject;
import com.softicar.platform.workflow.module.workflow.node.precondition.IWorkflowPrecondition;
import java.util.function.Predicate;

@SourceCodeReferencePointUuid("b517d86a-4057-4d1f-b32b-67cf4a50320b")
public class WorkflowNodeTestPrecondition implements IWorkflowPrecondition<WorkflowTestObject> {

	private static final Singleton<Predicate<WorkflowTestObject>> PREDICATE = new Singleton<>(Predicates::always);

	public static void setPredicate(Predicate<WorkflowTestObject> predicate) {

		PREDICATE.set(predicate);
	}

	@Override
	public IDisplayString getTitle() {

		return IDisplayString.create("Precondition for Testing");
	}

	@Override
	public boolean test(WorkflowTestObject object) {

		return PREDICATE.get().test(object);
	}

	@Override
	public IDisplayString getErrorMessage() {

		return IDisplayString.create("Precondition Error Message for Testing");
	}
}
