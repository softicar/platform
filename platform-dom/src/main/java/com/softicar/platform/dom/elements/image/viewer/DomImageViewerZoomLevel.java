package com.softicar.platform.dom.elements.image.viewer;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.math.Clamping;
import java.util.ArrayList;
import java.util.List;

class DomImageViewerZoomLevel implements IDisplayable {

	private static final List<DomImageViewerZoomLevel> ZOOM_LEVELS = createAll(25, 50, 75, 100, 125, 150, 175, 200, 300, 400);
	private static final DomImageViewerZoomLevel DEFAULT_ZOOM_LEVEL = findByPercentage(100);
	private final int index;
	private final int percentage;

	public DomImageViewerZoomLevel(int index, int percentage) {

		this.index = index;
		this.percentage = percentage;
	}

	@Override
	public IDisplayString toDisplay() {

		return IDisplayString.create(percentage + "%");
	}

	public int getIndex() {

		return index;
	}

	public int getPercentage() {

		return percentage;
	}

	public DomImageViewerZoomLevel getPrevious() {

		return getByIndex(index - 1);
	}

	public DomImageViewerZoomLevel getNext() {

		return getByIndex(index + 1);
	}

	public boolean hasPrevious() {

		return index > 0;
	}

	public boolean hasNext() {

		return index < ZOOM_LEVELS.size() - 1;
	}

	public static DomImageViewerZoomLevel getByIndex(int index) {

		return ZOOM_LEVELS.get(Clamping.clamp(0, ZOOM_LEVELS.size() - 1, index));
	}

	public static List<DomImageViewerZoomLevel> getAll() {

		return ZOOM_LEVELS;
	}

	public static DomImageViewerZoomLevel getDefault() {

		return DEFAULT_ZOOM_LEVEL;
	}

	private static List<DomImageViewerZoomLevel> createAll(int...percentages) {

		var zoomLevels = new ArrayList<DomImageViewerZoomLevel>();
		for (int i = 0; i < percentages.length; i++) {
			zoomLevels.add(new DomImageViewerZoomLevel(i, percentages[i]));
		}
		return zoomLevels;
	}

	private static DomImageViewerZoomLevel findByPercentage(int percentage) {

		return ZOOM_LEVELS//
			.stream()
			.filter(level -> level.getPercentage() == percentage)
			.findFirst()
			.orElseThrow(() -> new RuntimeException("Failed to find zoom level with %s percentage.".formatted(percentage)));
	}
}
