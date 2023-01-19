package com.softicar.platform.workflow.module.workflow.export;

interface ExampleWorkflow {

	String WORKFLOW_JSON = """
			{
				name=test,
				entityType='99b9d13d-2425-4313-ac77-9df7648aa99d',
				nodes=[
					{name=start,x=5,y=7,actions=[],preconditions=['b517d86a-4057-4d1f-b32b-67cf4a50320b']},
					{name=end,x=7,y=11,actions=[],preconditions=['80a7305f-7b3e-4080-9212-a086511f9f40']}
				],
				transitions=[
					{name='do it',sourceNode=0,targetNode=1,notify=true,autoTransition=false,requiredVotes='100%',icon='',sideEffect='',permissions=[]}
				],
				rootNode=0
			}
			""";

	WorkflowDtoV1 WORKFLOW_DTO = WorkflowDtoV1.fromJson(WORKFLOW_JSON);
}
