package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.task.AGWorkflowTask;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;

// TODO add tests with exception in side effect
public class WorkflowAutoTransitionsExecutorTest extends AbstractTestDocumentWorkflowTest {

	private final WorkflowTestDocument documentA;
	private final WorkflowTestDocument documentB;
	private final TestDocumentWorkflowInserter inserter;

	public WorkflowAutoTransitionsExecutorTest() {

		this.documentA = insertTestDocument("Document A", 3);
		this.documentB = insertTestDocument("Document B", 5);
		this.inserter = new TestDocumentWorkflowInserter();
	}

	@Test
	public void testProcessBurst() {

		// setup
		inserter.insert();
		AGWorkflowItem itemA = initializeWorkflowItem(documentA);
		AGWorkflowItem itemB = initializeWorkflowItem(documentB);
		assertInNode(rootNode, itemA, itemB);

		// execute and assert
		executeTransitions();
		assertInNode(inserter.approvedInternallyNode, itemA);
		assertInNode(inserter.approvedInternallyNode, itemB);
		var tasks = assertTasks(//
			"TestDocument - Document A :: Approved internally",
			"TestDocument - Document B :: Approved internally");

		// execute and assert again, to ensure idempotence
		executeTransitions();
		assertInNode(inserter.approvedInternallyNode, itemA);
		assertInNode(inserter.approvedInternallyNode, itemB);
		assertSameTasks(tasks);
	}

	@Test
	public void testProcessBurstWithEmptyWorkflow() {

		// setup
		AGWorkflowItem itemA = initializeWorkflowItem(documentA);
		AGWorkflowItem itemB = initializeWorkflowItem(documentB);
		assertInNode(rootNode, itemA, itemB);

		// execute and assert
		executeTransitions();
		assertInNode(rootNode, itemA);
		assertInNode(rootNode, itemB);
		assertNoTasks();
	}

	@Test
	public void testProcessBurstWithLengthLimitEncountered() {

		// setup
		inserter.insert().addInfiniteLoopTransition();
		AGWorkflowItem itemA = initializeWorkflowItem(documentA);
		AGWorkflowItem itemB = initializeWorkflowItem(documentB);
		AGWorkflowItem itemX = initializeWorkflowItem(insertTestDocument("Document X", 99));
		assertInNode(rootNode, itemA, itemB, itemX);

		// execute and assert
		assertExceptionMessageContains(IDisplayString.create("Exceeded the maximum auto transition cascade length of 10"), () -> executeTransitions(10));
		assertInNode(inserter.approvedInternallyNode, itemA);
		assertInNode(inserter.approvedInternallyNode, itemB);
		assertInNode(inserter.approvedExternallyNode, itemX);
		assertTasks(//
			"TestDocument - Document A :: Approved internally",
			"TestDocument - Document B :: Approved internally");
	}

	@Test
	public void testProcessBurstWithSpecificItem() {

		// setup
		inserter.insert();
		AGWorkflowItem itemA = initializeWorkflowItem(documentA);
		AGWorkflowItem itemB = initializeWorkflowItem(documentB);
		assertInNode(rootNode, itemA, itemB);

		// execute and assert
		executeTransitions(itemA);
		assertInNode(inserter.approvedInternallyNode, itemA);
		assertInNode(rootNode, itemB);
		var tasks = assertTasks("TestDocument - Document A :: Approved internally");

		// execute and assert again, to ensure idempotence
		executeTransitions(itemA);
		assertInNode(inserter.approvedInternallyNode, itemA);
		assertInNode(rootNode, itemB);
		assertSameTasks(tasks);
	}

	@Test
	public void testProcessBurstWithSpecificItemAndWithEmptyWorkflow() {

		// setup
		AGWorkflowItem itemA = initializeWorkflowItem(documentA);
		AGWorkflowItem itemB = initializeWorkflowItem(documentB);
		assertInNode(rootNode, itemA, itemB);

		// execute and assert
		executeTransitions(itemA);
		assertInNode(rootNode, itemA);
		assertInNode(rootNode, itemB);
		assertNoTasks();
	}

	@Test
	public void testProcessBurstWithSpecificItemAndWithLengthLimitEncountered() {

		// setup
		inserter.insert().addInfiniteLoopTransition();
		AGWorkflowItem itemA = initializeWorkflowItem(documentA);
		AGWorkflowItem itemB = initializeWorkflowItem(documentB);
		AGWorkflowItem itemX = initializeWorkflowItem(insertTestDocument("Document X", 99));
		assertInNode(rootNode, itemA, itemB, itemX);

		// execute and assert
		assertExceptionMessageContains(IDisplayString.create("Exceeded the maximum auto transition cascade length of 10"), () -> executeTransitions(itemX, 10));
		assertInNode(rootNode, itemA);
		assertInNode(rootNode, itemB);
		assertInNode(inserter.approvedExternallyNode, itemX);
		assertNoTasks();
	}

	private void executeTransitions() {

		new WorkflowAutoTransitionsExecutor().executeTransitions();
	}

	private void executeTransitions(int cascadeLengthLimit) {

		new WorkflowAutoTransitionsExecutor().setCascadeLengthLimit(cascadeLengthLimit).executeTransitions();
	}

	private void executeTransitions(AGWorkflowItem item) {

		new WorkflowAutoTransitionsExecutor().setWorkflowItemWhitelist(item).executeTransitions();
	}

	private void executeTransitions(AGWorkflowItem item, int cascadeLengthLimit) {

		new WorkflowAutoTransitionsExecutor().setWorkflowItemWhitelist(item).setCascadeLengthLimit(cascadeLengthLimit).executeTransitions();
	}

	private AGWorkflowItem initializeWorkflowItem(WorkflowTestDocument testDocument) {

		Objects.requireNonNull(testDocument);
		insertPermissionA(user, testDocument);
		initializeItemAndObject(testDocument);
		return testDocument.getWorkflowItem();
	}

	private WorkflowTestDocument insertTestDocument(String name, int quantity) {

		return new WorkflowTestDocument()//
			.setName(name)
			.setQuantity(quantity)
			.save();
	}

	private static void assertInNode(AGWorkflowNode node, AGWorkflowItem...items) {

		Objects.requireNonNull(node);
		for (var item: items) {
			assertSame(node, item.getWorkflowNode());
		}
	}

	private static void assertNoTasks() {

		assertTasks();
	}

	private static Collection<AGWorkflowTask> assertTasks(String...expectedDisplayNames) {

		List<AGWorkflowTask> tasks = AGWorkflowTask.TABLE.loadAll();
		assertEquals(expectedDisplayNames.length, tasks.size());

		Set<String> taskDisplayNames = tasks.stream().map(task -> task.toDisplayWithoutId().toString()).collect(Collectors.toSet());
		Arrays
			.asList(expectedDisplayNames)
			.forEach(
				expectedDisplayName -> assertTrue(
					"Failed to find a task with display name: '%s'".formatted(expectedDisplayName),
					taskDisplayNames.contains(expectedDisplayName)));

		return tasks;
	}

	private static void assertSameTasks(Collection<AGWorkflowTask> expectedTasks) {

		var tasks = AGWorkflowTask.TABLE.loadAll();
		assertTrue(expectedTasks.equals(tasks));
	}

	/**
	 * Creates a test workflow as displayed below.
	 * <p>
	 * Transitions labeled "A" are automatic. Transitions labeled "M" are
	 * manual.
	 *
	 * <pre>
	 *                 ┌───────┐
	 *                 │Created│
	 *                 └───┬───┘
	 *                     │ A
	 *            ┌────────▼──────────┐
	 *            │Approved externally│
	 *            └───────┬─┬─────────┘
	 *                    │ │
	 *   A: quantity < 10 │ │ A: quantity >= 10
	 *                    │ │
	 *    ┌─────────────┐ │ │ ┌─────────────┐
	 *    │Checks passed│◄┘ └►│Checks failed│
	 *    └──────┬──────┘     └─────────────┘
	 *           │ A
	 * ┌─────────▼─────────┐
	 * │Approved internally│
	 * └───────────────────┘
	 *           │ M
	 *      ┌────▼───┐
	 *      │Archived│
	 *      └────────┘
	 * </pre>
	 */
	private class TestDocumentWorkflowInserter {

		public AGWorkflowNode createdNode;
		public AGWorkflowNode approvedExternallyNode;
		public AGWorkflowNode checksPassedNode;
		public AGWorkflowNode checksFailedNode;
		public AGWorkflowNode approvedInternallyNode;
		public AGWorkflowNode archivedNode;

		public TestDocumentWorkflowInserter insert() {

			this.createdNode = insertWorkflowNode(workflowVersion, "Created");
			this.approvedExternallyNode = insertWorkflowNode(workflowVersion, "Approved externally");
			this.checksPassedNode = insertWorkflowNode(workflowVersion, "Checks passed");
			this.checksFailedNode = insertWorkflowNode(workflowVersion, "Checks failed");
			this.approvedInternallyNode = insertWorkflowNode(workflowVersion, "Approved internally");
			this.archivedNode = insertWorkflowNode(workflowVersion, "Archived");

			insertWorkflowNodePrecondition(createdNode, WorkflowTestDocumentTruePrecondition.class);
			insertWorkflowNodePrecondition(approvedExternallyNode, WorkflowTestDocumentTruePrecondition.class);
			insertWorkflowNodePrecondition(checksPassedNode, WorkflowTestDocumentQuantityLowPrecondition.class);
			insertWorkflowNodePrecondition(checksFailedNode, WorkflowTestDocumentQuantityHighPrecondition.class);
			insertWorkflowNodePrecondition(approvedInternallyNode, WorkflowTestDocumentTruePrecondition.class);
			insertWorkflowNodePrecondition(archivedNode, WorkflowTestDocumentTruePrecondition.class);

			insertWorkflowAutoTransition("Auto 1", rootNode, createdNode);
			insertWorkflowAutoTransition("Auto 2", createdNode, approvedExternallyNode);
			insertWorkflowAutoTransition("Auto 3", approvedExternallyNode, checksPassedNode);
			insertWorkflowAutoTransition("Auto 4", approvedExternallyNode, checksFailedNode);
			insertWorkflowAutoTransition("Auto 5", checksPassedNode, approvedInternallyNode);

			insertWorkflowTransition("Move to Archive", approvedInternallyNode, archivedNode, "1", true, WorkflowTestDocumentTable.PERMISSION_A);

			return this;
		}

		/**
		 * Inserts an auto transition from the "Checks failed" node back to the
		 * "Created" node, to intentionally cause an infinite loop for all items
		 * that enter "Checks failed".
		 */
		public void addInfiniteLoopTransition() {

			insertWorkflowAutoTransition("Auto Loop", checksFailedNode, createdNode);
		}
	}
}
