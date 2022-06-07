package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import com.softicar.platform.emf.validation.result.EmfValidationResult;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.demo.sideeffect.WorkflowDemoObjectApproveSideEffect;
import com.softicar.platform.workflow.module.test.AbstractTestObjectWorkflowTest;
import com.softicar.platform.workflow.module.test.WorkflowTestObject;
import com.softicar.platform.workflow.module.test.WorkflowTestObjectTable;
import java.util.UUID;
import org.junit.Test;

public class WorkflowTransitionValidatorTest extends AbstractTestObjectWorkflowTest {

	private final AGWorkflowTransition transition;
	private final EmfValidationResult result;

	public WorkflowTransitionValidatorTest() {

		this.transition = insertWorkflowTransition("Transition", rootNode, rootNode, "1", false, WorkflowTestObjectTable.PERMISSION_A);
		this.result = new EmfValidationResult();
	}

	@Test
	public void testValidateWithoutSideEffect() {

		new WorkflowTransitionValidator().validate(transition, result);

		assertFalse(result.hasErrors());
	}

	@Test
	public void testValidateWithSideEffectThatIsNotSourceCodeReferencePoint() {

		AGUuid uuid = AGUuid.getOrCreate(UUID.fromString("020edfc9-432c-4c67-8fcb-42b6d4592cca"));
		transition.setSideEffect(uuid);

		new WorkflowTransitionValidator().validate(transition, result);

		result.assertError(WorkflowI18n.MISSING_SOURCE_CODE_REFERENCE_POINT);
	}

	@Test
	public void testValidateWithSideEffectThatIsReferencePointButNotSideEffect() {

		AGUuid uuid = AGUuid.getOrCreate(EmfSourceCodeReferencePoints.getUuidOrThrow(DummySourceCodeReferencePoint.class));
		transition.setSideEffect(uuid);

		new WorkflowTransitionValidator().validate(transition, result);

		result.assertError(WorkflowI18n.SOURCE_CODE_REFERENCE_POINT_IS_NOT_A_VALID_SIDE_EFFECT);
	}

	@Test
	public void testValidateWithSideEffectWithIncompatibleWorkflowableItem() {

		AGUuid uuid = AGUuid.getOrCreate(EmfSourceCodeReferencePoints.getUuidOrThrow(WorkflowDemoObjectApproveSideEffect.class));
		transition.setSideEffect(uuid);

		new WorkflowTransitionValidator().validate(transition, result);

		result.assertError(WorkflowI18n.SIDE_EFFECT_IS_NOT_COMPATIBLE_WITH_ARG1.toDisplay(WorkflowTestObject.TABLE.getTitle()));
	}
}
