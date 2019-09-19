package com.netsteadfast.greenstep.bsc.mobile.sys;

import javax.servlet.http.HttpSessionEvent;

import com.netsteadfast.greenstep.base.sys.WebSystemHttpSessionListenerForGeneralPackage;
import com.netsteadfast.greenstep.bsc.support.BSCThreadLocalClear;

public class BSCWebSystemHttpSessionListenerForGeneralPackage extends WebSystemHttpSessionListenerForGeneralPackage {
	
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		super.sessionDestroyed(event);
		BSCThreadLocalClear.clear();
	}	
	
}
