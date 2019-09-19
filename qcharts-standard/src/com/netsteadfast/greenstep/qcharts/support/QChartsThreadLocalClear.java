package com.netsteadfast.greenstep.qcharts.support;

//import org.apache.log4j.Logger;

import com.netsteadfast.greenstep.qcharts.utils.ManualJdbcTemplateFactory;
import com.netsteadfast.greenstep.support.CoreThreadLocalClear;

public class QChartsThreadLocalClear {
	//private static Logger logger = Logger.getLogger(QChartsThreadLocalClear.class);
	
	public static void clear() {
		//logger.info("clear ThreadLocal data.");
		CoreThreadLocalClear.clear();
		ManualJdbcTemplateFactory.clearTreadLocal();		
	}
	
}
