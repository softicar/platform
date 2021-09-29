package com.softicar.platform.core.module.log.process;

import com.softicar.platform.core.module.log.process.detail.LogProcessDetailsScope;
import com.softicar.platform.core.module.log.process.group.LogProcessGroupOverview;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.styles.CssDisplay;

public class LogProcessViewPageDiv extends DomDiv {

	private final LogProcessGroupOverview logProcessGroupOverview;
	private final LogProcessOverview m_logClassDetailsScope;
	private final LogProcessDetailsScope m_logProcessDetailsScope;

	public LogProcessViewPageDiv() {

		logProcessGroupOverview = new LogProcessGroupOverview(this);
		m_logClassDetailsScope = new LogProcessOverview(this);
		m_logProcessDetailsScope = new LogProcessDetailsScope(this);

		appendChild(logProcessGroupOverview);
		appendChild(m_logClassDetailsScope);
		appendChild(m_logProcessDetailsScope);

		logProcessGroupOverview.setStyle(CssDisplay.INLINE);
		m_logClassDetailsScope.setStyle(CssDisplay.NONE);
		m_logProcessDetailsScope.setStyle(CssDisplay.NONE);
	}

	public void showClassDetailsScopeWithRefresh(AGLogProcess logClass) {

		logProcessGroupOverview.setStyle(CssDisplay.NONE);
		m_logProcessDetailsScope.setStyle(CssDisplay.NONE);
		m_logClassDetailsScope.refresh(logClass);
		m_logClassDetailsScope.setStyle(CssDisplay.INLINE);
	}

	public void showClassDetailsScope() {

		logProcessGroupOverview.setStyle(CssDisplay.NONE);
		m_logProcessDetailsScope.setStyle(CssDisplay.NONE);
		m_logClassDetailsScope.setStyle(CssDisplay.INLINE);
	}

	public void showProcessDetailsScope(AGLogProcess logProcess) {

		logProcessGroupOverview.setStyle(CssDisplay.NONE);
		m_logClassDetailsScope.setStyle(CssDisplay.NONE);
		m_logProcessDetailsScope.refresh(logProcess);
		m_logProcessDetailsScope.setStyle(CssDisplay.INLINE);
	}

	public void showOverviewScope() {

		m_logClassDetailsScope.setStyle(CssDisplay.NONE);
		m_logProcessDetailsScope.setStyle(CssDisplay.NONE);
		logProcessGroupOverview.setStyle(CssDisplay.INLINE);
	}
}
