package com.netsteadfast.greenstep.qcharts.sys;

import javax.servlet.http.HttpSessionEvent;

import com.netsteadfast.greenstep.base.sys.WebSystemHttpSessionListenerForGeneralPackage;
import com.netsteadfast.greenstep.qcharts.support.QChartsThreadLocalClear;

public class QChartsWebSystemHttpSessionListenerForGeneralPackage extends WebSystemHttpSessionListenerForGeneralPackage {
	
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		super.sessionDestroyed(event);
		QChartsThreadLocalClear.clear();
	}
	
}
