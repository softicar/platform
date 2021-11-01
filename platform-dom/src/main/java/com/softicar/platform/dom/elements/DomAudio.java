package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

public class DomAudio extends DomParentElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.AUDIO;
	}

	public void setAutoPlay(boolean autoPlay) {

		setAttribute("autoplay", autoPlay? "autoplay" : null);
	}

	public void setControls(boolean controls) {

		setAttribute("controls", controls? "controls" : null);
	}

	public void setLoop(boolean loop) {

		setAttribute("loop", loop? "loop" : null);
	}

	public void setMuted(boolean muted) {

		setAttribute("muted", muted? "muted" : null);
	}

	public void setSrc(String url) {

		setAttribute("src", url);
	}
}
