package com.netsteadfast.greenstep.support;

//import org.apache.log4j.Logger;

import com.netsteadfast.greenstep.util.MailClientUtils;
import com.netsteadfast.greenstep.util.ScriptExpressionUtils;

public class CoreThreadLocalClear {
	//private static Logger logger = Logger.getLogger(CoreThreadLocalClear.class);
	
	public static void clear() {
		//logger.info("clear ThreadLocal data.");
		MailClientUtils.clearThreadLocal();
		ScriptExpressionUtils.clearThreadLocal();		
	}

}
