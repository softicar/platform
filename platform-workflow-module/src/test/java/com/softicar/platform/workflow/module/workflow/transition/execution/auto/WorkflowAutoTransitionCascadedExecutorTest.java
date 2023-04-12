package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.workflow.module.test.AbstractTestObjectWorkflowTest;
import com.softicar.platform.workflow.module.test.TruePrecondition;
import com.softicar.platform.workflow.module.test.WorkflowTestObject;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

public class WorkflowAutoTransitionCascadedExecutorTest extends AbstractTestObjectWorkflowTest {

	private final WorkflowTestObject testObject;

	public WorkflowAutoTransitionCascadedExecutorTest() {

		this.testObject = new WorkflowTestObject()//
			.setName("TestName")
			.save();
	}

	@Test
	public void testEvaluateAndExecuteCascaded() {

		// setup
		AGWorkflowNode lastNode = new TrivialCascadingWorkflowInserter().insertAll(10).getLastNode();
		workflow.startWorkflow(testObject);
		AGWorkflowItem item = testObject.getWorkflowItem();
		assertSame(rootNode, item.getWorkflowNode());

		// execute and assert
		evaluateAndExecuteCascaded(item);
		assertSame(lastNode, item.getWorkflowNode());

		evaluateAndExecuteCascaded(item);
		assertSame(lastNode, item.getWorkflowNode());
	}

	@Test
	public void testEvaluateAndExecuteCascadedWithSingleTransition() {

		// setup
		AGWorkflowNode lastNode = new TrivialCascadingWorkflowInserter().insertAll(1).getLastNode();
		workflow.startWorkflow(testObject);
		AGWorkflowItem item = testObject.getWorkflowItem();
		assertSame(rootNode, item.getWorkflowNode());

		evaluateAndExecuteCascaded(item);
		assertSame(lastNode, item.getWorkflowNode());

		evaluateAndExecuteCascaded(item);
		assertSame(lastNode, item.getWorkflowNode());
	}

	@Test
	public void testEvaluateAndExecuteCascadedWithoutTransitions() {

		// setup
		workflow.startWorkflow(testObject);
		AGWorkflowItem item = testObject.getWorkflowItem();

		evaluateAndExecuteCascaded(item);
		assertSame(rootNode, item.getWorkflowNode());
	}

	@Test
	public void testEvaluateAndExecuteCascadedWithLengthLimitEncountered() {

		// setup
		new TrivialCascadingWorkflowInserter().insertAll(10);
		workflow.startWorkflow(testObject);
		AGWorkflowItem item = testObject.getWorkflowItem();
		assertSame(rootNode, item.getWorkflowNode());

		// execute and assert
		var executor = new WorkflowAutoTransitionCascadedExecutor(item).setCascadeLengthLimit(5);
		assertExceptionMessage("Exceeded the maximum cascade length of 5.", executor::evaluateAndExecuteCascaded);
	}

	private void evaluateAndExecuteCascaded(AGWorkflowItem item) {

		new WorkflowAutoTransitionCascadedExecutor(item).evaluateAndExecuteCascaded();
	}

	private class TrivialCascadingWorkflowInserter {

		private final List<AGWorkflowNode> nodes;

		public TrivialCascadingWorkflowInserter() {

			this.nodes = new ArrayList<>();
		}

		/**
		 * Attaches the given number of nodes, trivial preconditions and
		 * auto-transitions to the workflow root node.
		 */
		public TrivialCascadingWorkflowInserter insertAll(int nodeCount) {

			AGWorkflowNode lastNode = null;
			for (int i = 0; i < nodeCount; i++) {
				AGWorkflowNode node = insertWorkflowNode(workflowVersion, "Node " + i);
				nodes.add(node);

				insertWorkflowNodePrecondition(node, TruePrecondition.class);

				AGWorkflowNode autoTransitionSource = Optional.ofNullable(lastNode).orElse(rootNode);
				insertWorkflowAutoTransition(autoTransitionSource, node, "Auto Transition " + i);

				lastNode = node;
			}
			return this;
		}

		public AGWorkflowNode getLastNode() {

			if (nodes.isEmpty()) {
				throw new AssertionError();
			}
			return nodes.get(nodes.size() - 1);
		}
	}
}
