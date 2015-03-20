/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.greenstep.publish.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.sys.UserAccountHttpSessionSupport;
import com.netsteadfast.greenstep.model.TemplateCode;
import com.netsteadfast.greenstep.model.TemplateResultObj;
import com.netsteadfast.greenstep.model.WebMessagePublishBaseObj;
import com.netsteadfast.greenstep.po.hbm.TbSysMsgNotice;
import com.netsteadfast.greenstep.publish.BaseMessagePublishService;
import com.netsteadfast.greenstep.service.ISysMsgNoticeService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.TemplateUtils;
import com.netsteadfast.greenstep.vo.AccountVO;
import com.netsteadfast.greenstep.vo.SysMsgNoticeConfigVO;
import com.netsteadfast.greenstep.vo.SysMsgNoticeVO;

/**
 * for PUBMSG0001
 *
 */
@SuppressWarnings("unchecked")
public class SystemMessagePublishServiceImpl implements BaseMessagePublishService {
	private ServletRequest request;
	private static ISysMsgNoticeService<SysMsgNoticeVO, TbSysMsgNotice, String> sysMsgNoticeService;
	private static Map<String, String> messagePublishMap = new HashMap<String, String>();
	private static Map<String, List<TbSysMsgNotice>> accountSysMsgNoticeListMap = new HashMap<String, List<TbSysMsgNotice>>();
	private static Map<String, List<TbSysMsgNotice>> globalSysMsgNoticeListMap = new HashMap<String, List<TbSysMsgNotice>>();
	private static final int SLEEP = 600000; // 10分
	private static final int CLEAR_TIME = 14400000; // 4時
	private static int c = 0;
	
	static {
		sysMsgNoticeService = (ISysMsgNoticeService<SysMsgNoticeVO, TbSysMsgNotice, String>)
				AppContext.getBean("core.service.SysMsgNoticeService");
		new Thread(new Runnable(){
			
			@Override
			public void run() {
				while (true) {
					accountSysMsgNoticeListMap.clear();
					globalSysMsgNoticeListMap.clear();
					if (c*SLEEP>=CLEAR_TIME) {
						c = 0;
						messagePublishMap.clear();
					}
					c++;
					try {
						Thread.sleep(SLEEP);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}					
				}
			}
			
		}).start();;
	}
	
	private List<TbSysMsgNotice> loadGlobalSysMsgNoticeData(String msgId, String account) throws ServiceException, Exception {
		if (globalSysMsgNoticeListMap.get(account)!=null) {
			return globalSysMsgNoticeListMap.get(account);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> orderParams = new LinkedHashMap<String, String>();
		params.put("isGlobal", YesNo.YES);
		params.put("toAccount", account);
		params.put("msgId", msgId);
		orderParams.put("date", "ASC");
		orderParams.put("time", "ASC");
		List<TbSysMsgNotice> searchList = sysMsgNoticeService.findListByParams2(params, null, null, orderParams);
		if (searchList==null) {
			searchList = new ArrayList<TbSysMsgNotice>();
		}
		globalSysMsgNoticeListMap.put(account, searchList);
		return searchList;
	}
	
	private List<TbSysMsgNotice> loadAccountSysMsgNoticeData(String msgId, String account) throws ServiceException, Exception {
		if (accountSysMsgNoticeListMap.get(account)!=null) {
			return accountSysMsgNoticeListMap.get(account);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> orderParams = new LinkedHashMap<String, String>();
		params.put("isGlobal", YesNo.NO);
		params.put("toAccount", account);
		params.put("msgId", msgId);
		orderParams.put("date", "ASC");
		orderParams.put("time", "ASC");		
		List<TbSysMsgNotice> searchList = sysMsgNoticeService.findListByParams2(params, null, null, orderParams);
		if (searchList==null) {
			searchList = new ArrayList<TbSysMsgNotice>();
		}
		accountSysMsgNoticeListMap.put(account, searchList);
		return searchList;
	}
	
	@Override
	public WebMessagePublishBaseObj execute(SysMsgNoticeConfigVO config, ServletRequest request) throws ServiceException, Exception {
		this.request = request;
		WebMessagePublishBaseObj publishObj = new WebMessagePublishBaseObj();
		String account = request.getParameter("account");
		String refreshUUID = StringUtils.defaultString(request.getParameter("refreshUUID")).trim();
		String sessionId = ((HttpServletRequest)request).getSession().getId();
		AccountVO accountObj = (AccountVO) UserAccountHttpSessionSupport.get( (HttpServletRequest)request );
		if (StringUtils.isBlank(this.getId()) || StringUtils.isBlank(account) || accountObj==null ) { // no login
			return publishObj;
		}
		if (!account.equals(accountObj.getAccount())) { // not same account request
			return publishObj;
		}
		List<TbSysMsgNotice> globalSysMsgNoticeList = this.loadGlobalSysMsgNoticeData(config.getMsgId(), "*");
		List<TbSysMsgNotice> accountSysMsgNoticeList = this.loadAccountSysMsgNoticeData(config.getMsgId(), account);
		String globalMsg = this.process(sessionId, refreshUUID, globalSysMsgNoticeList);
		String personalMsg = this.process(sessionId, refreshUUID, accountSysMsgNoticeList);
		publishObj.setIsAuthorize(YesNo.YES);
		publishObj.setLogin(YesNo.YES);
		publishObj.setSuccess(YesNo.YES);
		publishObj.setMessage(globalMsg+personalMsg);
		return publishObj;
	}
	
	private String process(String sessionId, String refreshUUID, List<TbSysMsgNotice> sysMsgNotices) throws ServiceException, Exception {
		if (sysMsgNotices==null || sysMsgNotices.size()<1) {
			return "";
		}
		Date sysDate = new Date();
		StringBuilder sb = new StringBuilder();
		for (TbSysMsgNotice msgNotice : sysMsgNotices) {
			String key = sessionId + ":uuid_" + refreshUUID + ":" + msgNotice.getNoticeId();
			if (messagePublishMap.get(key)!=null) {
				continue;
			}
			if (!this.isPublishDateTimeRange(msgNotice, sysDate)) { // 日期區間不合
				continue;
			}
			//msgNotice.setTitle( TemplateUtils.processTemplateHtmlContent(msgNotice.getTitle()) );
			//msgNotice.setMessage( TemplateUtils.processTemplateHtmlContent(msgNotice.getMessage()) );
			TbSysMsgNotice dataObj = new TbSysMsgNotice();
			dataObj.setTitle( TemplateUtils.escapeHtml4TemplateHtmlContent(msgNotice.getTitle()) );
			dataObj.setMessage( TemplateUtils.escapeHtml4TemplateHtmlContent(msgNotice.getMessage()) );
			TemplateResultObj result = TemplateUtils.getResult(TemplateCode.TPLMSG0002, dataObj);
			dataObj = null;
			sb.append( result.getContent() );
			messagePublishMap.put(key, YesNo.YES);
		}
		return sb.toString();
	}
	
	private boolean isPublishDateTimeRange(TbSysMsgNotice sysMsgNotice, Date sysDate) throws Exception {
		String timeStr = sysMsgNotice.getTime();
		String dateStr = sysMsgNotice.getDate();
		String systemDateStr = SimpleUtils.getStrYMD(sysDate, "");
		boolean f = false;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sysDate);
		int nowTimeNum = Integer.parseInt( 
				StringUtils.leftPad( String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)), 2, "0" ) + 
				StringUtils.leftPad( String.valueOf(calendar.get(Calendar.MINUTE)), 2, "0" ) );
		int nowDateNum = Integer.parseInt(systemDateStr);		
		String dateTmp[] = dateStr.split(Constants.DATETIME_DELIMITER);
		String timeTmp[] = timeStr.split(Constants.DATETIME_DELIMITER);
		int begDateNum = Integer.parseInt(dateTmp[0]);
		int endDateNum = Integer.parseInt(dateTmp[1]);
		int begTimeNum = Integer.parseInt(timeTmp[0]);
		int endTimeNum = Integer.parseInt(timeTmp[1]);
		if ( (nowDateNum>=begDateNum && nowDateNum<=endDateNum) || dateStr.equals("00000000-00000000") ) { // 日期在區間內 或 全天總事發佈的
			if ("0000-0000".equals(timeStr)) { // 全時總事發佈的
				f = true;
			}
			if (nowTimeNum>=begTimeNum && nowTimeNum<=endTimeNum) { // 時間在區間內
				f = true;
			}
		}
		return f;
	}

	@Override
	public String getId() throws Exception {
		return StringUtils.defaultString(this.request.getParameter("id")).trim();
	}
	
}
