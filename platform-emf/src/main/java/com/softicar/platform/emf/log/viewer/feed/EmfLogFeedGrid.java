package com.softicar.platform.emf.log.viewer.feed;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.label.DomPreformattedLabel;
import com.softicar.platform.emf.EmfCssClasses;

class EmfLogFeedGrid extends DomDiv {

	public EmfLogFeedGrid() {

		addCssClass(EmfCssClasses.EMF_LOG_FEED_GRID);
	}

	public EmfLogFeedGrid appendRow(IDisplayString title, IDomElement valueElement, EmfLogFeedItemType type) {

		appendChild(new LabelDiv(title, type));
		appendChild(new ValueDiv(valueElement));
		return this;
	}

	private class LabelDiv extends DomDiv {

		private final IDisplayString title;

		public LabelDiv(IDisplayString title, EmfLogFeedItemType type) {

			this.title = title;
			addCssClass(EmfCssClasses.EMF_LOG_FEED_GRID_LABEL);
			if (type.getBadgeString() != null) {
				DomPreformattedLabel badgeLabel = new DomPreformattedLabel(type.getBadgeString());
				switch (type) {
				case ADDITION:
					badgeLabel.addCssClass(DomCssPseudoClasses.SUCCESS);
					break;
				case REMOVAL:
					badgeLabel.addCssClass(DomCssPseudoClasses.ERROR);
					break;
				case UPDATE:
					badgeLabel.addCssClass(DomCssPseudoClasses.INFO);
					break;
				}
				appendChild(badgeLabel);
			}
			appendChild(new TitleContainer());
		}

		private class TitleContainer extends DomDiv {

			public TitleContainer() {

				appendText(title.concat(":"));
			}
		}
	}

	private class ValueDiv extends DomDiv {

		public ValueDiv(IDomElement valueElement) {

			addCssClass(EmfCssClasses.EMF_LOG_FEED_GRID_VALUE);
			appendChild(valueElement);
		}
	}
}
