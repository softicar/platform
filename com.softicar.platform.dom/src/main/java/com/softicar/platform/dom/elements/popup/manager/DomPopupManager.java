package com.softicar.platform.dom.elements.popup.manager;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class helps to prevent redundant pop-ups.
 * <p>
 * The manager insures that for each entity and each pop-up class, only one
 * instance exists.
 *
 * @author Oliver Richers
 */
public class DomPopupManager {

	private final Map<DomPopupManagerKey, DomPopup> popups;

	public DomPopupManager() {

		this.popups = new HashMap<>();
	}

	public static DomPopupManager getInstance() {

		return CurrentDomDocument.get().getDataMap().getOrPutInstance(DomPopupManager.class, DomPopupManager::new);
	}

	public <T, P extends DomPopup> P getPopup(T entity, Class<P> popupClass, Function<T, P> factory) {

		DomPopupManagerKey key = new DomPopupManagerKey(entity, popupClass);
		P popup = CastUtils.cast(popups.get(key));
		if (popup == null) {
			popup = factory.apply(entity);
			// FIXME this might override custom callbacks
			popup.getCloseManager().setCloseCallback(() -> popups.remove(key));
			popups.put(key, popup);
		}
		return popup;
	}
}
