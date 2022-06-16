package com.softicar.platform.workflow.module.demo.configuration;

import com.softicar.platform.core.module.configuration.AbstractStandardConfiguration;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixture;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.module.permission.EmfDefaultModulePermissions;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;
import com.softicar.platform.workflow.module.demo.AGWorkflowDemoObject;
import com.softicar.platform.workflow.module.demo.AGWorkflowDemoObjectTable;
import com.softicar.platform.workflow.module.demo.WorkflowDemoObjectTableReferencePoint;
import com.softicar.platform.workflow.module.demo.approver.AGWorkflowDemoObjectApprover;
import com.softicar.platform.workflow.module.demo.preconditions.FurtherApprovalIsRequiredPrecondition;
import com.softicar.platform.workflow.module.demo.preconditions.NoFurtherApprovalIsRequiredPrecondition;
import com.softicar.platform.workflow.module.demo.sideeffect.WorkflowDemoObjectApproveSideEffect;
import com.softicar.platform.workflow.module.demo.sideeffect.WorkflowDemoObjectRejectSideEffect;
import com.softicar.platform.workflow.module.test.fixture.WorkflowModuleTestFixtureMethods;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

public class WorkflowDemoConfiguration extends AbstractStandardConfiguration implements WorkflowModuleTestFixtureMethods {

	private final AGWorkflowModuleInstance moduleInstance;
	private final CoreModuleTestFixture coreModuleTestFixture;
	private AGWorkflow workflow;
	private AGWorkflowVersion workflowVersion;
	private AGWorkflowNode isFurtherApprovalNecessayNode;
	private AGWorkflowNode approvalNode;
	private AGWorkflowNode rejectedNode;
	private AGWorkflowNode approvedNode;

	public WorkflowDemoConfiguration(AGWorkflowModuleInstance moduleInstance, CoreModuleTestFixture coreModuleTestFixture) {

		this.moduleInstance = moduleInstance;
		this.coreModuleTestFixture = coreModuleTestFixture;
	}

	@Override
	public void createAndSaveAll() {

		this.workflow = insertWorkflow(moduleInstance, "Demo Workflow", WorkflowDemoObjectTableReferencePoint.class);
		this.workflowVersion = insertWorkflowVersion(workflow, false);
		this.isFurtherApprovalNecessayNode = insertWorkflowNode(workflowVersion, "Is Further Approval Necessary?")//
			.setXCoordinate(0)
			.setYCoordinate(50)
			.save();
		this.approvalNode = insertWorkflowNode(workflowVersion, "Approval")//
			.setXCoordinate(200)
			.setYCoordinate(150)
			.save();
		this.rejectedNode = insertWorkflowNode(workflowVersion, "Rejected")//
			.setXCoordinate(200)
			.setYCoordinate(250)
			.save();
		this.approvedNode = insertWorkflowNode(workflowVersion, "Approved")//
			.setXCoordinate(0)
			.setYCoordinate(150)
			.save();

		insertWorkflowAutoTransition("Auto", isFurtherApprovalNecessayNode, approvalNode);
		insertWorkflowAutoTransition("Auto", isFurtherApprovalNecessayNode, approvedNode);

		insertWorkflowTransition(//
			"Approve",
			approvalNode,
			isFurtherApprovalNecessayNode,
			"100%",
			true,
			AGWorkflowDemoObjectTable.APPROVER)//
				.setSideEffect(AGUuid.getOrCreate(WorkflowDemoObjectApproveSideEffect.class))
				.save();
		insertWorkflowTransition(//
			"Reject",
			approvalNode,
			rejectedNode,
			"1",
			false,
			AGWorkflowDemoObjectTable.APPROVER)//
				.setSideEffect(AGUuid.getOrCreate(WorkflowDemoObjectRejectSideEffect.class))
				.save();

		insertWorkflowNodePrecondition(approvalNode, FurtherApprovalIsRequiredPrecondition.class);
		insertWorkflowNodePrecondition(approvedNode, NoFurtherApprovalIsRequiredPrecondition.class);

		workflow.setCurrentVersion(workflowVersion).save();
		workflowVersion.setRootNode(isFurtherApprovalNecessayNode).save();

		AGUser demoUser = insertUser("Demo", "User")//
			.setEmailAddress("demo.user@example.com")
			.save();
		insertPassword(demoUser, "test");
		insertPermissionAssignment(demoUser, EmfDefaultModulePermissions.getModuleOperation(), moduleInstance);
		AGWorkflowDemoObject demoObject = new AGWorkflowDemoObject().setName("Test Object").setModuleInstance(moduleInstance).save();
		insertWorkflowDemoObjectApprover(demoObject, coreModuleTestFixture.getAdminUser(), 1);
		insertWorkflowDemoObjectApprover(demoObject, coreModuleTestFixture.getNormalUser(), 1);
		insertWorkflowDemoObjectApprover(demoObject, demoUser, 2);
	}

	public AGWorkflowNode getIsFurtherApprovalNecessayNode() {

		return isFurtherApprovalNecessayNode;
	}

	public AGWorkflowNode getApprovalNode() {

		return approvalNode;
	}

	public AGWorkflowNode getApprovedNode() {

		return approvedNode;
	}

	public AGWorkflowNode getRejectedNode() {

		return rejectedNode;
	}

	private void insertWorkflowDemoObjectApprover(AGWorkflowDemoObject object, AGUser user, Integer tier) {

		new AGWorkflowDemoObjectApprover()//
			.setObject(object)
			.setUser(user)
			.setApprovalTier(tier)
			.save();
	}
}
