package com.softicar.platform.workflow.module.workflow.management.display.line;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;

/**
 * Draws an arrow using {@link WorkflowDisplayLine}.
 */
public class WorkflowDisplayArrow extends WorkflowDisplayLine {

	/**
	 * Draws an arrow.
	 *
	 * @param color
	 *            the color in format #RRGGBB
	 * @param width
	 *            how many extra-pixels should be colored (default=0)
	 */
	public WorkflowDisplayArrow(AGWorkflowTransition transition, INullaryVoidFunction refreshCallback, int x1, int y1, int x2, int y2, String color,
			int width) {

		super(transition, refreshCallback, x1, y1, x2, y2, color, width);

		double dx = x2 - x1;
		double dy = y2 - y1;

		if (dx == 0) {
			dx = 0.0001;
		}

		double len = Math.sqrt(dx * dx + dy * dy);
		double u = dx / len;
		double v = dy / len;

		double x = x1 + dx - 5 * u;
		double y = y1 + dy - 5 * v;
		this
			.appendChild(
				new WorkflowDisplayLine(
					transition,
					refreshCallback,
					(int) (x1 + dx),
					(int) (y1 + dy),
					(int) (x - 5 * v + 0.5),
					(int) (y + 5 * u + 0.5),
					color,
					width));
		this
			.appendChild(
				new WorkflowDisplayLine(
					transition,
					refreshCallback,
					(int) (x1 + dx),
					(int) (y1 + dy),
					(int) (x + 5 * v + 0.5),
					(int) (y - 5 * u + 0.5),
					color,
					width));
	}

	public WorkflowDisplayArrow(int x1, int y1, int x2, int y2, String color, int width) {

		this(null, null, x1, y1, x2, y2, color, width);
	}
}
