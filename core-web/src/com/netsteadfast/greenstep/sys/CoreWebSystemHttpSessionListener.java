package com.netsteadfast.greenstep.sys;

import javax.servlet.http.HttpSessionEvent;

import com.netsteadfast.greenstep.base.sys.WebSystemHttpSessionListener;
import com.netsteadfast.greenstep.support.CoreThreadLocalClear;

public class CoreWebSystemHttpSessionListener extends WebSystemHttpSessionListener {
	
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		super.sessionDestroyed(event);
		CoreThreadLocalClear.clear();
	}

}
