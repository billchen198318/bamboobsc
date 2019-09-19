package com.netsteadfast.greenstep.bsc.support;

//import org.apache.log4j.Logger;

import com.netsteadfast.greenstep.bsc.util.AggregationMethodUtils;
import com.netsteadfast.greenstep.bsc.util.BscReportPropertyUtils;
import com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils;
import com.netsteadfast.greenstep.bsc.util.BscScoreColorUtils;
import com.netsteadfast.greenstep.support.CoreThreadLocalClear;

public class BSCThreadLocalClear {
	//private static Logger logger = Logger.getLogger(BSCThreadLocalClear.class);
	
	public static void clear() {
		//logger.info("clear ThreadLocal data.");
		CoreThreadLocalClear.clear();
		AggregationMethodUtils.clearThreadLocal();
		BscReportPropertyUtils.clearThreadLocal();
		BscReportSupportUtils.clearThreadLocal();
		BscScoreColorUtils.clearThreadLocal();		
	}
	
}
