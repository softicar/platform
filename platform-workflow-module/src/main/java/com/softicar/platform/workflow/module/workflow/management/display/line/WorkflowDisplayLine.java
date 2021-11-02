package com.softicar.platform.workflow.module.workflow.management.display.line;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.styles.CssCursor;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.workflow.module.WorkflowCssClasses;
import com.softicar.platform.workflow.module.workflow.transition.AGWorkflowTransition;

/**
 * Draws a line in the browser using a lot of DIV elements.
 * <p>
 * This uses a modified version of Bresenham's line algorithm.
 */
public class WorkflowDisplayLine extends DomDiv implements IDomClickEventHandler {

	private final AGWorkflowTransition transition;
	private final INullaryVoidFunction refreshCallback;
	private int width = 0;

	public WorkflowDisplayLine(AGWorkflowTransition transition, INullaryVoidFunction refreshCallback, int x1, int y1, int x2, int y2, String color, int width) {

		this.transition = transition;
		this.refreshCallback = refreshCallback;
		this.width = width;
		if (transition != null) {
			setStyle(CssCursor.POINTER);
		}

		if (x1 > x2) {
			int x2Switch = x2;
			int y2Switch = y2;
			x2 = x1;
			y2 = y1;
			x1 = x2Switch;
			y1 = y2Switch;
		}

		int dx = x2 - x1;
		int dy = Math.abs(y2 - y1);
		int x = x1;
		int y = y1;
		int yIncr = y1 > y2? -1 : 1;

		int s = 1;
		double sDouble;

		if (dx >= dy) {
			if (dx > 0 && s - 3 > 0) {
				sDouble = (s * dx * Math.sqrt(1 + dy * dy / (dx * dx)) - dx - (s >> 1) * dy) / dx;
				sDouble = (s - 4 != 0? Math.ceil(sDouble) : Math.round(sDouble)) + 1;
			} else {
				sDouble = s;
			}
			double ad = Math.ceil(s / 2);

			int pr = dy << 1, pru = pr - (dx << 1), p = pr - dx, ox = x;
			while (dx > 0) {
				--dx;
				++x;
				if (p > 0) {
					drawRectangle(ox, y, x - ox + ad, sDouble, color);
					y += yIncr;
					p += pru;
					ox = x;
				} else {
					p += pr;
				}
			}
			drawRectangle(ox, y, x2 - ox + ad + 1, sDouble, color);
		} else {
			if (s - 3 > 0) {
				sDouble = (s * dy * Math.sqrt(1 + dx * dx / (dy * dy)) - (s >> 1) * dx - dy) / dy;
				sDouble = (s - 4 != 0? Math.ceil(sDouble) : Math.round(sDouble)) + 1;
			} else {
				sDouble = s;
			}
			double ad = Math.round(s / 2);

			int pr = dx << 1, pru = pr - (dy << 1), p = pr - dy, oy = y;
			if (y2 <= y1) {
				++ad;
				while (dy > 0) {
					--dy;
					if (p > 0) {
						drawRectangle(x++, y, sDouble, oy - y + ad, color);
						y += yIncr;
						p += pru;
						oy = y;
					} else {
						y += yIncr;
						p += pr;
					}
				}
				drawRectangle(x2, y2, sDouble, oy - y2 + ad, color);
			} else {
				while (dy > 0) {
					--dy;
					y += yIncr;
					if (p > 0) {
						drawRectangle(x++, oy, sDouble, y - oy + ad, color);
						p += pru;
						oy = y;
					} else {
						p += pr;
					}
				}
				drawRectangle(x2, oy, sDouble, y2 - oy + ad + 1, color);
			}
		}
	}

	private void drawRectangle(int x, int y, double widthDouble, double heightDouble, String color) {

		int width = (int) Math.round(widthDouble) + this.width;
		int height = (int) Math.round(heightDouble) + this.width;

		DomDiv rectangleDiv = new DomDiv();
		rectangleDiv.addCssClass(WorkflowCssClasses.WORKFLOW_DISPLAY_LINE_PIXEL);
		rectangleDiv.setStyle(CssStyle.LEFT, "" + x + "px");
		rectangleDiv.setStyle(CssStyle.TOP, "" + y + "px");
		rectangleDiv.setStyle(CssStyle.WIDTH, "" + width + "px");
		rectangleDiv.setStyle(CssStyle.HEIGHT, "" + height + "px");
		rectangleDiv.setStyle(CssStyle.CLIP, "rect(0," + width + "px, " + height + "px,0)");
		rectangleDiv.setStyle(CssStyle.BACKGROUND_COLOR, color);
		appendChild(rectangleDiv);
	}

	@Override
	public void handleClick(IDomEvent event) {

		if (transition != null) {
			DomPopupManager//
				.getInstance()
				.getPopup(transition, EditTransitionPopup.class, EditTransitionPopup::new)
				.show();
		}
	}

	private class EditTransitionPopup extends EmfFormPopup<AGWorkflowTransition> {

		public EditTransitionPopup(AGWorkflowTransition transition) {

			super(transition);
			setDirectEditing(false);
		}

		@Override
		public void hide() {

			super.hide();
			if (refreshCallback != null) {
				refreshCallback.apply();
			}
		}
	}
}
