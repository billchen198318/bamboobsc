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
package com.netsteadfast.greenstep.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.MenuItemType;
import com.netsteadfast.greenstep.model.MenuResultObj;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.po.hbm.TbSysMenu;
import com.netsteadfast.greenstep.po.hbm.TbSysMultiName;
import com.netsteadfast.greenstep.po.hbm.TbSysProg;
import com.netsteadfast.greenstep.po.hbm.TbSysProgMultiName;
import com.netsteadfast.greenstep.po.hbm.TbSysTwitter;
import com.netsteadfast.greenstep.service.ISysMenuService;
import com.netsteadfast.greenstep.service.ISysMultiNameService;
import com.netsteadfast.greenstep.service.ISysProgMultiNameService;
import com.netsteadfast.greenstep.service.ISysProgService;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.service.ISysTwitterService;
import com.netsteadfast.greenstep.vo.SysMenuVO;
import com.netsteadfast.greenstep.vo.SysMultiNameVO;
import com.netsteadfast.greenstep.vo.SysProgMultiNameVO;
import com.netsteadfast.greenstep.vo.SysProgVO;
import com.netsteadfast.greenstep.vo.SysTwitterVO;
import com.netsteadfast.greenstep.vo.SysVO;

/**
 * 提供產生選單資料
 * 目錄只有兩層而已, 父目錄 -> 子項目 
 *
 */
@SuppressWarnings("unchecked")
public class MenuSupportUtils {
	private static final String MENU_ITEM_JAVASCRIPT = "javascript";
	private static final String MENU_ITEM_HTML = "html";
	private static final String MENU_ITEM_DIALOG = "dialog";
	private static ISysService<SysVO, TbSys, String> sysService;
	private static ISysMenuService<SysMenuVO, TbSysMenu, String> sysMenuService;
	private static ISysProgService<SysProgVO, TbSysProg, String> sysProgService;
	private static ISysTwitterService<SysTwitterVO, TbSysTwitter, String> sysTwitterService;
	private static ISysMultiNameService<SysMultiNameVO, TbSysMultiName, String> sysMultiNameService;
	private static ISysProgMultiNameService<SysProgMultiNameVO, TbSysProgMultiName, String> sysProgMultiNameService;
	
	static {		
		sysService = (ISysService<SysVO, TbSys, String>)AppContext.getBean("core.service.SysService");		
		sysMenuService = (ISysMenuService<SysMenuVO, TbSysMenu, String>)AppContext.getBean("core.service.SysMenuService");
		sysProgService = (ISysProgService<SysProgVO, TbSysProg, String>)AppContext.getBean("core.service.SysProgService");
		sysTwitterService = (ISysTwitterService<SysTwitterVO, TbSysTwitter, String>)
				AppContext.getBean("core.service.SysTwitterService");
		sysMultiNameService = (ISysMultiNameService<SysMultiNameVO, TbSysMultiName, String>)
				AppContext.getBean("core.service.SysMultiNameService");
		sysProgMultiNameService = (ISysProgMultiNameService<SysProgMultiNameVO, TbSysProgMultiName, String>)
				AppContext.getBean("core.service.SysProgMultiNameService");
	}
	
	public MenuSupportUtils() {
		
	}
	
	public static ISysService<SysVO, TbSys, String> getSysService() {
		return sysService;
	}

	public static void setSysService(ISysService<SysVO, TbSys, String> sysService) {
		MenuSupportUtils.sysService = sysService;
	}

	public static ISysMenuService<SysMenuVO, TbSysMenu, String> getSysMenuService() {
		return sysMenuService;
	}

	public static void setSysMenuService(
			ISysMenuService<SysMenuVO, TbSysMenu, String> sysMenuService) {
		MenuSupportUtils.sysMenuService = sysMenuService;
	}
	
	public static TbSysProg loadSysProg(String progId) throws ServiceException, Exception {
		
		TbSysProg tbSysProg = new TbSysProg();
		tbSysProg.setProgId(progId);
		tbSysProg = sysProgService.findByEntityUK(tbSysProg);
		if (null == tbSysProg) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
		}
		return tbSysProg;
	}

	public static String getUrl(String basePath, TbSys sys, TbSysProg sysProg, String jsessionId) throws Exception {
		String url = "";
		if (YesNo.YES.equals(sys.getIsLocal())) {
			url = basePath + "/" + sysProg.getUrl() + ( (sysProg.getUrl().indexOf("?")>0 || sysProg.getUrl().indexOf("&")>0) ? "&" : "?" ) + Constants.IS_DOJOX_CONTENT_PANE_XHR_LOAD + "=" + YesNo.YES;
		} else {
			String head = "http://";
			if (basePath.startsWith("https")) {
				head = "https://";
			}
			url = head + sys.getHost() + "/" + sys.getContextPath() + "/" + sysProg.getUrl()
					+ ( (sysProg.getUrl().indexOf("?")>0 || sysProg.getUrl().indexOf("&")>0) ? "&" : "?" ) + Constants.IS_DOJOX_CONTENT_PANE_XHR_LOAD + "=" + YesNo.YES;
					//+ "&" + Constants.APP_SITE_CROSS_JSESS_ID_PARAM + "=" + jsessionId;
			
			// 2015-09-07 rem
			//url += "&" + Constants.SYS_CURRENT_ID + "=" + UserAccountHttpSessionSupport.getSysCurrentId(ServletActionContext.getRequest());
		}			
		if ( sysProg.getUrl().indexOf(Constants._COMMON_LOAD_FORM_ACTION) > -1 ) { // common form 要用到參數 prog_id
			url += "&prog_id=" + sysProg.getProgId();
		}		
		if ( YesNo.YES.equals(sysProg.getIsWindow()) ) {
			url += "&" + Constants.IS_IFRAME_MODE + "=" + YesNo.YES;
		}
		return url;
	}
	
	protected static List<TbSysProg> loadSysProgData(String system) throws ServiceException, Exception {
		List<TbSysProg> progList = null;
		TbSys sys = new TbSys();
		sys.setSysId(system);
		if (sysService.countByEntityUK(sys)!=1) { // 必需要有 TB_SYS 資料
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
		}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> oderParams = new HashMap<String, String>();
		params.put("progSystem", system);
		oderParams.put("name", "asc");
		progList = sysProgService.findListByParams(params, null, oderParams);
		if (progList==null) {
			progList = new ArrayList<TbSysProg>();
		}
		return progList;
	}
	
	protected static List<SysMenuVO> loadSysMenuData(String system) throws ServiceException, Exception {
		List<SysMenuVO> menuList = null;
		TbSys sys = new TbSys();
		sys.setSysId(system);
		if (sysService.countByEntityUK(sys)!=1) { // 必需要有 TB_SYS 資料
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
		}
		Subject subject = SecurityUtils.getSubject();
		String account = (String)subject.getPrincipal();
		if (StringUtils.isBlank(account)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS)); 
		}		
		if (subject.hasRole(Constants.SUPER_ROLE_ADMIN) || subject.hasRole(Constants.SUPER_ROLE_ALL)) {
			account = null;
		} 
		DefaultResult<List<SysMenuVO>> result = sysMenuService.findForMenuGenerator(system, account);
		if (result.getValue()!=null) {
			menuList = result.getValue();
		}
		if (menuList==null) {
			menuList = new ArrayList<SysMenuVO>();
		}
		return menuList;
	}
	
	/**
	 * 取是目錄選單的資料
	 * 
	 * @param sysMenuList
	 * @return
	 * @throws Exception
	 */
	protected static List<SysMenuVO> searchFolder(List<SysMenuVO> sysMenuList) throws Exception {
		List<SysMenuVO> folderList = new ArrayList<SysMenuVO>();
		for (SysMenuVO sysMenu : sysMenuList) {
			if (MenuItemType.FOLDER.equals(sysMenu.getItemType()) && YesNo.YES.equals(sysMenu.getEnableFlag()) ) {
				folderList.add(sysMenu);
			}
		}
		return folderList;
	}
	
	/**
	 * 取目錄下的選單項目
	 * 
	 * @param parentOid
	 * @param sysMenuList
	 * @return
	 * @throws Exception
	 */
	protected static List<SysMenuVO> searchItem(String parentOid, List<SysMenuVO> sysMenuList) throws Exception {
		List<SysMenuVO> folderList = new ArrayList<SysMenuVO>();
		for (SysMenuVO sysMenu : sysMenuList) {
			if (MenuItemType.ITEM.equals(sysMenu.getItemType()) && parentOid.equals(sysMenu.getParentOid())
					&& YesNo.YES.equals(sysMenu.getEnableFlag()) ) {
				folderList.add(sysMenu);
			}
		}
		return folderList;		
	}
	
	/**
	 * 取回下拉選單(預設系統)
	 * 
	 * @param basePath
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public static MenuResultObj getMenuData(String basePath, String jsessionId, String localeCode) throws ServiceException, Exception {
		
		if (LocaleLanguageUtils.getMap().get(localeCode) == null) {
			localeCode = LocaleLanguageUtils.getDefault();
		}
		Map<String, String> orderParams = new HashMap<String, String>();
		orderParams.put("name", "asc");
		List<TbSys> sysList = sysService.findListByParams(null, null, orderParams);
		if (sysList==null || sysList.size()<1) { // 必需要有 TB_SYS 資料
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
		}
		MenuResultObj resultObj = new MenuResultObj();
		StringBuilder jsSb = new StringBuilder();
		StringBuilder htmlSb = new StringBuilder();
		StringBuilder dlgSb = new StringBuilder();
		jsSb.append("var ").append(Constants.GS_GET_APPLICATION_NAME_SCRIPT_OBJ).append(" = new Object();	").append("\n");
		jsSb.append("function ").append(Constants.GS_GET_APPLICATION_NAME_SCRIPT_FN).append(" {				").append("\n");
		jsSb.append("	var name = ").append(Constants.GS_GET_APPLICATION_NAME_SCRIPT_OBJ).append("[progId];").append("\n");
		jsSb.append("	if (name == null) {																	").append("\n");
		jsSb.append("		return progId;																	").append("\n");
		jsSb.append("	}																					").append("\n");
		jsSb.append("	return name;																		").append("\n");
		jsSb.append("}																						").append("\n");
		for (TbSys sys : sysList) {
			Map<String, String> menuData = getMenuData(basePath, sys, jsessionId, localeCode);
			jsSb.append(StringUtils.defaultString(menuData.get(MENU_ITEM_JAVASCRIPT)) );
			htmlSb.append(StringUtils.defaultString(menuData.get(MENU_ITEM_HTML)) );
			dlgSb.append(StringUtils.defaultString(menuData.get(MENU_ITEM_DIALOG)) );
		}		
		resultObj.setJavascriptData( jsSb.toString() );
		resultObj.setHtmlData( htmlSb.toString() );
		resultObj.setDialogHtmlData( dlgSb.toString() );
		return resultObj;
	}	
	
	/**
	 * 取回Tree選單資料
	 * 
	 * @param basePath
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public static List<Map<String, Object>> getMenuTreeJsonData(String basePath, String localeCode) throws ServiceException, Exception {
		Map<String, String> orderParams = new HashMap<String, String>();
		orderParams.put("name", "asc");
		List<TbSys> sysList = sysService.findListByParams(null, null, orderParams);
		if (sysList==null || sysList.size()<1) { // 必需要有 TB_SYS 資料
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
		}
		return getMenuTreeJsonData(basePath, sysList, localeCode);
	}
	
	/**
	 * 取回Tree選單資料
	 * 
	 * @param basePath
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public static String getMenuTreeJsonDataStr(String basePath, String localeCode) throws ServiceException, Exception {
		List<Map<String, Object>> menuJsonList = getMenuTreeJsonData(basePath, localeCode);
		return JSONArray.fromObject(menuJsonList).toString();
	}
	
	/**
	 * 取回下拉選單
	 * 
	 * @param basePath
	 * @param sys
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public static Map<String, String> getMenuData(String basePath, TbSys sys, String jsessionId, String localeCode) throws ServiceException, Exception {
		Map<String, String> menuData = new HashMap<String, String>();			
		List<SysMenuVO> menuList = loadSysMenuData(sys.getSysId());
		if (menuList==null || menuList.size()<1) {
			return menuData;
		}
		StringBuilder htmlSb = new StringBuilder();
		StringBuilder jsSb = new StringBuilder();
		StringBuilder dlgSb = new StringBuilder();
		Map<String, String> jsFunctionMap = new HashMap<String, String>();		
				
		/*
		for (SysMenuVO sysMenu : menuList) { // create combobox menu javascript
			if (!YesNo.YES.equals(sysMenu.getEnableFlag())) {
				continue;
			}
			if (TYPE_IS_FOLDER.equals(sysMenu.getItemType())) { // 目錄不須要 
				continue;
			}
			String openTabFn = sysMenu.getProgId() + "_TabShow()";
			String refreshTabFn = sysMenu.getProgId() + "_TabRefresh()";
			String closeTabFn = sysMenu.getProgId() + "_TabClose()";
			String tabId = sysMenu.getProgId() + "_ChildTab";	
			jsSb.append("function ").append( openTabFn ).append(" { ");
			jsSb.append("	viewPage.addOrUpdateContentPane(	");
			jsSb.append("		'").append(Constants.MAIN_TabContainer_ID).append("',	");
			jsSb.append("		'").append(tabId).append("',	");
			jsSb.append("		'").append( StringEscapeUtils.escapeEcmaScript(IconUtils.getMenuIcon(basePath, sysMenu.getIcon()) ) ).append(StringEscapeUtils.escapeEcmaScript(sysMenu.getName())).append("',	");
			jsSb.append("		'").append( getUrl(basePath, sys, sysMenu, jsessionId) ).append("',	");			
			jsSb.append("		true,	");
			jsSb.append("		true");
			jsSb.append("	);	");
			jsSb.append("}	");
			jsSb.append("\n");
			jsSb.append("function ").append( refreshTabFn ).append(" { ");
			jsSb.append("	viewPage.refreshContentPane('").append(tabId).append("'); ");
			jsSb.append("}	");
			jsSb.append("\n");
			jsSb.append("function ").append( closeTabFn ).append(" { ");
			jsSb.append("	viewPage.closeContentPane('").append(Constants.MAIN_TabContainer_ID).append("', '").append(tabId).append("'); ");
			jsSb.append("}	");
			jsSb.append("\n");			
			jsFunctionMap.put(sysMenu.getProgId(), openTabFn );
		}
		*/
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("progSystem", sys.getSysId());
		List<TbSysProg> sysProgList = sysProgService.findListByParams(params);
		for (int i=0; sysProgList!=null && i<sysProgList.size(); i++) {
			TbSysProg sysProg = sysProgList.get(i);						
			if (MenuItemType.FOLDER.equals(sysProg.getItemType())) { // 目錄不須要 
				continue;
			}
			String openTabFn = sysProg.getProgId() + "_TabShow()";
			if ( YesNo.YES.equals( sysProg.getEditMode()) ) {
				openTabFn = sysProg.getProgId() + "_TabShow(oid)";
			}
			String refreshTabFn = sysProg.getProgId() + "_TabRefresh()";
			String closeTabFn = sysProg.getProgId() + "_TabClose()";
			String tabId = sysProg.getProgId() + "_ChildTab";	
			boolean iframeMode = false; 
			if ( YesNo.YES.equals(sysProg.getIsWindow()) ) {
				iframeMode = true;
			}			
			String progMultiName = getProgramMultiName(sysProg, localeCode);
			jsSb.append("function ").append( openTabFn ).append(" { ");
			jsSb.append("	viewPage.addOrUpdateContentPane(	");
			jsSb.append("		'").append(Constants.MAIN_TabContainer_ID).append("',	");
			jsSb.append("		'").append(tabId).append("',	");
			//jsSb.append("		'").append( StringEscapeUtils.escapeEcmaScript(IconUtils.getMenuIcon(basePath, sysProg.getIcon()) ) ).append(StringEscapeUtils.escapeEcmaScript(sysProg.getName())).append("',	");
			jsSb.append("		'").append( StringEscapeUtils.escapeEcmaScript(IconUtils.getMenuIcon(basePath, sysProg.getIcon()) ) ).append(StringEscapeUtils.escapeEcmaScript(progMultiName)).append("',	"); // 改用讀取多語系名稱
			if ( YesNo.YES.equals( sysProg.getEditMode()) ) { // 編輯資料Edit 模式
				jsSb.append("		'").append( getUrl(basePath, sys, sysProg, jsessionId) ).append("&fields.oid=' + oid,	");
			} else { // 查詢Query 或 新增Create 模式
				jsSb.append("		'").append( getUrl(basePath, sys, sysProg, jsessionId) ).append("',	");
			}						
			jsSb.append("		true,	");
			jsSb.append("		true,	");
			jsSb.append("	").append(String.valueOf(iframeMode)).append("	");
			jsSb.append("	);	");
			jsSb.append("}	");
			jsSb.append("\n");
			jsSb.append("function ").append( refreshTabFn ).append(" { ");
			jsSb.append("	viewPage.refreshContentPane('").append(tabId).append("'); ");
			jsSb.append("}	");
			jsSb.append("\n");
			jsSb.append("function ").append( closeTabFn ).append(" { ");
			jsSb.append("	viewPage.closeContentPane('").append(Constants.MAIN_TabContainer_ID).append("', '").append(tabId).append("'); ");
			jsSb.append("}	");
			jsSb.append("\n");			
			//jsSb.append(Constants.GS_GET_APPLICATION_NAME_SCRIPT_OBJ).append("['").append( sysProg.getProgId() ).append("'] = '").append( StringEscapeUtils.escapeEcmaScript( sysProg.getName() ) ).append("';");
			jsSb.append(Constants.GS_GET_APPLICATION_NAME_SCRIPT_OBJ).append("['").append( sysProg.getProgId() ).append("'] = '").append( StringEscapeUtils.escapeEcmaScript( progMultiName ) ).append("';"); // 改用讀取多語系名稱
			jsSb.append("\n");
			jsFunctionMap.put(sysProg.getProgId(), openTabFn );	
			
			String dlgId = "";
			if (YesNo.YES.equals(sysProg.getIsDialog())) {
				dlgId = sysProg.getProgId() + "_Dlg";
			}
			if (!StringUtils.isBlank(dlgId)) { // 有 dialog 
				dlgSb
					.append("<div id=\"" + dlgId + "\" data-dojo-type=\"dojox.widget.DialogSimple\" ")
					.append("data-dojo-props='href:\"").append( getUrl(basePath, sys, sysProg, jsessionId) ).append("\",")
					.append("	style:\"width: ").append(sysProg.getDialogW()).append("px; height: ").append(sysProg.getDialogH()).append("px\",")
					//.append("	title:\"").append( IconUtils.getMenuIcon(basePath, sysProg.getIcon()).replaceAll("'", "\\\\\"") ).append( StringEscapeUtils.escapeEcmaScript(sysProg.getName()) ).append("\",")
					.append("	title:\"").append( IconUtils.getMenuIcon(basePath, sysProg.getIcon()).replaceAll("'", "\\\\\"") ).append( StringEscapeUtils.escapeEcmaScript(progMultiName) ).append("\",") // 改用讀取多語系名稱
					.append("	refreshOnShow:\"true\", executeScripts:\"true\" ")
					.append(" '></div>")
					.append("\n");
				String dlgOpenFn = sysProg.getProgId() + "_DlgShow()";
				String dlgHideFn = sysProg.getProgId() + "_DlgHide()";
				if ( YesNo.YES.equals( sysProg.getEditMode()) ) {
					dlgOpenFn = sysProg.getProgId() + "_DlgShow(oid)";
				}
				jsSb.append("function ").append( dlgOpenFn ).append(" { ");				
				String dlgUrl = "";
				if ( YesNo.YES.equals( sysProg.getEditMode()) ) { // 編輯資料Edit 模式					
					dlgUrl = "'" + getUrl(basePath, sys, sysProg, jsessionId) + "&fields.oid=' + oid; ";
					jsSb.append("	var dlgUrl=").append(dlgUrl);
					jsSb.append("	dijit.byId('").append(dlgId).append("').set('href', dlgUrl); ");					
				}				
				jsSb.append("	dijit.byId('").append(dlgId).append("').show();	");
				jsSb.append("}	");
				jsSb.append("\n");	
				jsSb.append("function ").append( dlgHideFn ).append(" { ");
				jsSb.append("	dijit.byId('").append(dlgId).append("').hide();	");
				jsSb.append("}	");
				jsSb.append("\n");					
			}
			
		}
		
		// create combobox menu html
		htmlSb.append("<div data-dojo-type='dijit.PopupMenuItem' >");
		//htmlSb.append("	<span>").append( IconUtils.getMenuIcon(basePath, sys.getIcon()) ).append(sys.getName()).append("</span>");		
		htmlSb.append("	<span>").append( IconUtils.getMenuIcon(basePath, sys.getIcon()) ).append( getSystemMultiName(sys, localeCode) ).append("</span>"); // 改用找多語言設定
		List<SysMenuVO> parentSysMenuList = searchFolder(menuList);
		htmlSb.append("<div dojoType='dijit.Menu' >");
		for (SysMenuVO sysMenu : parentSysMenuList) {			
			List<SysMenuVO> itemSysMenuList = searchItem(sysMenu.getOid(), menuList);
			if (itemSysMenuList==null || itemSysMenuList.size()<1) {
				continue;
			}			
			String multiProgName = getProgramMultiName(sysMenu, localeCode);
			htmlSb.append("<div data-dojo-type='dijit.PopupMenuItem' >");
			//htmlSb.append("<span>").append( IconUtils.getMenuIcon(basePath, sysMenu.getIcon()) ).append(sysMenu.getName()).append("</span>");
			htmlSb.append("<span>").append( IconUtils.getMenuIcon(basePath, sysMenu.getIcon()) ).append(multiProgName).append("</span>"); // 改用找多語言設定
			htmlSb.append("<div data-dojo-type='dijit.Menu' >");
			for (SysMenuVO itemMenu : itemSysMenuList) {
				String itemMultiProgName = getProgramMultiName(itemMenu, localeCode);
				htmlSb.append("<div data-dojo-type='dijit.MenuItem' data-dojo-props='onClick:function(){ ")
				.append( StringUtils.defaultString( jsFunctionMap.get(itemMenu.getProgId()) ) )				
				.append(" }'>")
				.append( IconUtils.getMenuIcon(basePath, itemMenu.getIcon()) )
				//.append(itemMenu.getName())
				.append( itemMultiProgName ) // 改用找多語言設定
				.append("</div>");
			}
			htmlSb.append("</div>");
			htmlSb.append("</div>");			
		}		
		htmlSb.append("</div>");
		htmlSb.append("</div>");
		
		menuData.put(MENU_ITEM_JAVASCRIPT, jsSb.toString());
		menuData.put(MENU_ITEM_HTML, htmlSb.toString());
		menuData.put(MENU_ITEM_DIALOG, dlgSb.toString());
		return menuData;
	}
	
	/**
	 * 取回Tree選單資料
	 * 
	 * @param basePath
	 * @param sysList
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public static List<Map<String, Object>> getMenuTreeJsonData(String basePath, List<TbSys> sysList, String localeCode) throws ServiceException, Exception {
		
		if (LocaleLanguageUtils.getMap().get(localeCode) == null) {
			localeCode = LocaleLanguageUtils.getDefault();
		}
		List<Map<String, Object>> treeMenuList = new LinkedList<Map<String, Object>>();
		for (TbSys sys : sysList) {
			List<SysMenuVO> sysMenuList = loadSysMenuData(sys.getSysId());
			if (sysMenuList==null || sysMenuList.size()<1) {
				continue;
			}			
			String multiSysName = getSystemMultiName(sys,localeCode); // 改用去找多語言設定檔
			Map<String, Object> systemMap = new HashMap<String, Object>();
			systemMap.put("id", sys.getSysId());
			systemMap.put("progId", sys.getSysId());
			//systemMap.put("name", sys.getName());
			systemMap.put("name", multiSysName ); // 改用去找多語言設定檔
			systemMap.put("type", MenuItemType.FOLDER);
			//systemMap.put("url", " ");
			systemMap.put("onclick", " ");
			//systemMap.put("label", IconUtils.getMenuIcon(basePath, sys.getIcon()) + StringEscapeUtils.escapeHtml4(sys.getName()) );
			systemMap.put("label", IconUtils.getMenuIcon(basePath, sys.getIcon()) + StringEscapeUtils.escapeHtml4(multiSysName) ); // 改用去找多語言設定檔
			List<Map<String, Object>> sysItemList = new LinkedList<Map<String, Object>>();
			List<SysMenuVO> folderList = searchFolder(sysMenuList);
			for (SysMenuVO sysMenu : folderList) {
				List<SysMenuVO> menuItemList = searchItem(sysMenu.getOid(), sysMenuList);
				if (menuItemList==null || menuItemList.size()<1) {
					continue;
				}
				String menuProgMultiName = getProgramMultiName(sysMenu, localeCode);
				Map<String, Object> sysMenuMap = new HashMap<String, Object>();
				sysMenuMap.put("id", sysMenu.getOid());
				sysMenuMap.put("progId", sysMenu.getProgId());
				//sysMenuMap.put("name", sysMenu.getName());
				sysMenuMap.put("name", menuProgMultiName); // 改用去找多語言設定檔
				sysMenuMap.put("type", sysMenu.getItemType());
				//sysMenuMap.put("url", " ");
				sysMenuMap.put("onclick", " ");
				//sysMenuMap.put("label", IconUtils.getMenuIcon(basePath, sysMenu.getIcon()) + StringEscapeUtils.escapeHtml4(sysMenu.getName()) );
				sysMenuMap.put("label", IconUtils.getMenuIcon(basePath, sysMenu.getIcon()) + StringEscapeUtils.escapeHtml4(menuProgMultiName) ); // 改用去找多語言設定檔
				List<Map<String, Object>> childItemList = new LinkedList<Map<String, Object>>();
				for (SysMenuVO menuItem : menuItemList) {					
					String menuItemProgMultiName = getProgramMultiName(menuItem, localeCode);
					Map<String, Object> itemMap = new HashMap<String, Object>();
					itemMap.put("id", menuItem.getOid());
					itemMap.put("progId", menuItem.getProgId());
					//itemMap.put("name", menuItem.getName());
					itemMap.put("name", menuItemProgMultiName); // 改用去找多語言設定檔
					itemMap.put("type", menuItem.getItemType());
					itemMap.put("parent", menuItem.getParentOid());
					//itemMap.put("url", getUrl(basePath, sys, menuItem) );			
					itemMap.put("onclick", menuItem.getProgId()+"_TabShow()");
					//itemMap.put("label", IconUtils.getMenuIcon(basePath, menuItem.getIcon()) + StringEscapeUtils.escapeHtml4(menuItem.getName()) );
					itemMap.put("label", IconUtils.getMenuIcon(basePath, menuItem.getIcon()) + StringEscapeUtils.escapeHtml4(menuItemProgMultiName) ); // 改用去找多語言設定檔
					childItemList.add(itemMap);
				}
				sysMenuMap.put("children", childItemList);
				sysItemList.add(sysMenuMap);
			}
			if (sysItemList.size()>0) {
				systemMap.put("children", sysItemList);
				treeMenuList.add(systemMap);
			}
		}
		return treeMenuList;
	}
	
	public static String getProgramName(String progId) {
		String name = "unknown-program";
		if (StringUtils.isBlank(progId)) {
			return name;
		}
		try {
			name = sysProgService.findNameForProgId(progId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
	
	public static String getProgramName(String progId, String localeCode) {
		if (StringUtils.isBlank(progId)) {
			return "unknown-program";
		}
		String defaultName = "unknown-program";
		String multiName = "unknown-program";		
		defaultName = getProgramName(progId);
		if (LocaleLanguageUtils.getMap().get(localeCode) == null) {
			return defaultName;
		}
		TbSysProg sysProg = new TbSysProg();
		sysProg.setName(defaultName);
		sysProg.setProgId(progId);
		try {
			multiName = getProgramMultiName(sysProg, localeCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(multiName)) {
			return multiName;
		}
		return defaultName;
	}	
	
	public static String getTwitterAccordionPane() throws ServiceException, Exception {
		Map<String, Object> settingsMap = Constants.getSettingsMap();
		if ( SimpleUtils.getStr((String)settingsMap.get("twitter.enable"), YesNo.NO).toUpperCase().equals( YesNo.NO ) ) {
			return "";
		}
		List<TbSys> sysList = sysService.findListByParams(null);
		if ( sysList == null || sysList.size() < 1 ) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (TbSys sys : sysList) {
			sb.append( getTwitterAccordionPane(sys.getSysId()) );
		}		
		return sb.toString();
	}
	
	public static String getTwitterAccordionPane(String systemId) throws ServiceException, Exception {
		if ( StringUtils.isBlank(systemId) ) {
			return "";
		}
		SysTwitterVO sysTwitter = new SysTwitterVO();
		sysTwitter.setSystem( systemId );
		DefaultResult<SysTwitterVO> result = sysTwitterService.findByUK(sysTwitter);
		if ( result.getValue()==null ) {
			return "";
		}
		sysTwitter = result.getValue();
		if ( !YesNo.YES.equals(sysTwitter.getEnableFlag()) ) {
			return "";
		}
		String content = new String( sysTwitter.getContent(), "utf8" );
		if ( StringUtils.isBlank(content) ) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		String title = StringEscapeUtils.escapeHtml4( sysTwitter.getTitle() );
		String id = "_" + systemId + "_twitter_AccordionPane";
		sb.append("<div data-dojo-type=\"dijit/layout/AccordionPane\" title=\"<img src='./icons/twitter.png' border='0'/>&nbsp;" + title + "\" id=\"" + id + "\"> \n");
		sb.append(content);
		sb.append("\n");
		sb.append("</div>");
		return sb.toString();		
	}
	
	/**
	 * 預設要載入的 TabContainer , 一般都是設定為 workspace , 目前是 BSC_PROG001D0005Q_TabShow();
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public static String getFirstLoadJavascript() throws ServiceException, Exception {		
		return SystemSettingConfigureUtils.getFirstLoadJavascriptValue();
	}
	
	public static String getSystemMultiName(TbSys system, String localeCode) throws ServiceException, Exception {
		if (null == system) {
			return "unknown-system";
		}
		if (LocaleLanguageUtils.getMap().get(localeCode) == null) {
			return system.getName();
		}
		TbSysMultiName multiName = new TbSysMultiName();
		multiName.setSysId(system.getSysId());
		multiName.setLocaleCode(localeCode);
		multiName = sysMultiNameService.findByEntityUK(multiName);
		if (multiName != null && !StringUtils.isBlank(multiName.getName()) 
				&& YesNo.YES.equals(multiName.getEnableFlag())) {
			return multiName.getName();
		}
		return system.getName();
	}
	
	public static String getProgramMultiName(TbSysProg sysProg, String localeCode) throws ServiceException, Exception {
		if (null == sysProg) {
			return "unknown-program";
		}
		if (LocaleLanguageUtils.getMap().get(localeCode) == null) {
			return sysProg.getName();
		}
		TbSysProgMultiName multiName = new TbSysProgMultiName();
		multiName.setProgId(sysProg.getProgId());
		multiName.setLocaleCode(localeCode);
		multiName = sysProgMultiNameService.findByEntityUK(multiName);
		if (multiName != null && !StringUtils.isBlank(multiName.getName())
				&& YesNo.YES.equals(multiName.getEnableFlag())) {
			return multiName.getName();
		}
		return sysProg.getName();
	}
	
	public static String getProgramMultiName(SysMenuVO menu, String localeCode) throws ServiceException, Exception {
		TbSysProg sysProg = new  TbSysProg();
		sysProg.setProgId(menu.getProgId());
		sysProg.setName(menu.getName());
		return getProgramMultiName(sysProg, localeCode);
	}

}
