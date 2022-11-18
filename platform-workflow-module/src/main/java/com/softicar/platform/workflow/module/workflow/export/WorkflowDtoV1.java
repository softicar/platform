package com.softicar.platform.workflow.module.workflow.export;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

/**
 * Data transfer object to import and export workflow definitions.
 * <p>
 * The structure of this class may <b>not</b> be changed. A new class must be
 * created to add new features.
 *
 * @author Oliver Richers
 */
public class WorkflowDtoV1 {

	public int workflowApiVersion = 1;
	public String name;
	public String entityType;
	public List<Node> nodes;
	public List<Transition> transitions;
	public int rootNode;

	public static class Node {

		public String name;
		public int x;
		public int y;
		public List<NodeAction> actions;
		public List<String> preconditions;
	}

	public static class Transition {

		public String name;
		public int sourceNode;
		public int targetNode;
		public boolean notify;
		public boolean autoTransition;
		public String requiredVotes;
		public String htmlColor;
		public String icon;
		public String sideEffect;
		public List<String> permissions;
	}

	public static class NodeAction {

		String action;
		List<String> permissions;
	}

	@Override
	public String toString() {

		return new GsonBuilder().create().toJson(this);
	}

	@Override
	public int hashCode() {

		return toString().hashCode();
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof WorkflowDtoV1) {
			return toString().equals(((WorkflowDtoV1) object).toString());
		} else {
			return false;
		}
	}

	public static WorkflowDtoV1 fromJson(String json) {

		return new Gson().fromJson(json, WorkflowDtoV1.class);
	}
}
