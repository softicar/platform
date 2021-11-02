package com.softicar.platform.emf.attribute.field.duration;

import com.softicar.platform.dom.elements.DomDiv;
import java.time.Duration;
import java.util.Objects;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class EmfDurationDisplay extends DomDiv {

	public EmfDurationDisplay(Duration value) {

		this(value, "HH:mm:ss");
	}

	public EmfDurationDisplay(Duration value, String format) {

		Objects.requireNonNull(format);
		appendChild(DurationFormatUtils.formatDuration(value.toMillis(), format));
	}
}
