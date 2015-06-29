package com.netsteadfast.greenstep.test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.PageOf;
import com.netsteadfast.greenstep.base.model.QueryResult;
import com.netsteadfast.greenstep.base.model.SearchValue;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.command.LoadBscMixDataCommand;
import com.netsteadfast.greenstep.bsc.command.LoadBscStructTreeCommand;
import com.netsteadfast.greenstep.bsc.service.IScoreColorService;
import com.netsteadfast.greenstep.bsc.service.logic.IOrganizationLogicService;
//import com.netsteadfast.greenstep.model.SystemFtpData;
//import com.netsteadfast.greenstep.model.SystemFtpResultObj;
import com.netsteadfast.greenstep.po.hbm.BbScoreColor;
import com.netsteadfast.greenstep.po.hbm.TbAccount;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.po.hbm.TbSysCode;
import com.netsteadfast.greenstep.po.hbm.TbSysMailHelper;
import com.netsteadfast.greenstep.po.hbm.TbSysUpload;
import com.netsteadfast.greenstep.service.IAccountService;
import com.netsteadfast.greenstep.service.ISysCodeService;
import com.netsteadfast.greenstep.service.ISysMailHelperService;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.service.ISysUploadService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
//import com.netsteadfast.greenstep.util.SystemFtpUtils;
import com.netsteadfast.greenstep.vo.AccountVO;
import com.netsteadfast.greenstep.vo.ScoreColorVO;
import com.netsteadfast.greenstep.vo.SysCodeVO;
import com.netsteadfast.greenstep.vo.SysMailHelperVO;
import com.netsteadfast.greenstep.vo.SysUploadVO;
import com.netsteadfast.greenstep.vo.SysVO;

@Configuration
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class TestUnit {
	
//	@Test
//	public void testSystemFtp() throws Exception {
//		/*
//		 SystemFtpResultObj resultObj = SystemFtpUtils.getFileOnly("TRAN0001");		 
//		 List<File> files = resultObj.getFiles();
//		 if ( files == null || files.size() < 1 ) {
//			 System.out.println("no file.");
//			 return;
//		 }
//		 for (File file : files) {
//			 System.out.println( file.getPath() );
//		 }
//		 */
//		SystemFtpResultObj resultObj = SystemFtpUtils.getDatas("TRAN0001");
//		List<SystemFtpData> datas = resultObj.getDatas();
//		if ( datas == null ) {
//			System.out.println("no data.");
//			return;
//		}
//		for (SystemFtpData data : datas ) {
//			System.out.println( data.getContent() );
//			System.out.println( String.valueOf( data.getDatas() ) );			
//		}
//	}
	
	/*
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws Exception {
		

//--------------------------------------------------------------------------------
//	<chain name="getMeasureDatasChain">
//		<command id="LoadBscMixDataCommand" 		className="com.netsteadfast.greenstep.bsc.command.LoadBscMixDataCommand"/> 
//		<command id="LoadBscStructTreeCommand" 		className="com.netsteadfast.greenstep.bsc.command.LoadBscStructTreeCommand"/>
//		<command id="LoadMeasureDataCommand" 		className="com.netsteadfast.greenstep.bsc.command.LoadMeasureDataCommand"/>
//	</chain>
//--------------------------------------------------------------------------------			
		
		Context context = new ContextBase();
		context.put("visionOid", "1089abb5-3faf-445d-88ff-cd7690ac6743" );
		context.put("frequency", "6");
		context.put("startYearDate", "20130101");
		context.put("endYearDate", "20151230");
		context.put("dataFor", BscConstants.MEASURE_DATA_FOR_ALL);
		context.put("empId", BscConstants.MEASURE_DATA_EMPLOYEE_FULL );
		context.put("orgId", BscConstants.MEASURE_DATA_EMPLOYEE_FULL );
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("getMeasureDatasChain", context);		
		BscStructTreeObj treeObj = (BscStructTreeObj)resultObj.getValue();
		
		for (VisionVO vision : treeObj.getVisions() ) {
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				for (ObjectiveVO objective : perspective.getObjectives()) {
					for (KpiVO kpi : objective.getKpis()) {
						
						Float kpiScore1 = AggregationMethodUtils.processDefaultMode(kpi);
						System.out.println("score1=" + kpiScore1 );	
						
						Float kpiScore2 = AggregationMethodUtils.processDateRangeMode(kpi, "6");
						System.out.println("score2=" + kpiScore2 );							
						
					}
				}				
			}
		}
		
		System.exit( 1 );
	}
	 * 
	 */
	
//	public static void main(String args[]) throws Exception {
//		/*
//		String val = "                    ";
//		System.out.println(StringUtils.isEmpty(val));
//		System.out.println(StringUtils.isBlank(val));
//		*/
//		
//		/*
//		SimpleChain chain = new SimpleChain();
//		ChainResultObj result = chain.getResultFromClass(
//				new Class[]{LoadBscMixDataCommand.class, LoadBscStructTreeCommand.class});
//		System.out.println( result.getValue() );
//		System.exit(1);
//		*/
//		IScoreColorService<ScoreColorVO, BbScoreColor, String> scoreColorService =
//				(IScoreColorService<ScoreColorVO, BbScoreColor, String>)AppContext.getBean("bsc.service.ScoreColorService");
//		//System.out.println( scoreColorService.findForMaxValue() );
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		DefaultResult<List<BbScoreColor>> result = scoreColorService.ibatisSelectListByParams(paramMap);
//		List<BbScoreColor> testList = result.getValue();
//		System.out.println( testList.size() );
//	}
	
	@Test
	public void test7() throws Exception {
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);		
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("admin", "password99");
		subject.login(token);
		
		ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> sysMailHelperService = 
				(ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String>)AppContext.getBean("core.service.SysMailHelperService");
		
		SysMailHelperVO mailHelper = new SysMailHelperVO();
		mailHelper.setMailId("2014122400001");
		mailHelper.setMailFrom("root@localhost");
		mailHelper.setMailTo("chen.xin.nien@gmail.com");
		mailHelper.setSubject("測試用");
		mailHelper.setText( "<H1><font color='RED'>測試</font></H1>".getBytes() );
		mailHelper.setRetainFlag(YesNo.NO);
		mailHelper.setSuccessFlag(YesNo.NO);
		sysMailHelperService.saveObject(mailHelper);
		
		mailHelper = new SysMailHelperVO();
		mailHelper.setMailId("2014122400002");
		mailHelper.setMailFrom("root@localhost");
		mailHelper.setMailTo("chen.xin.nien@gmail.com");
		mailHelper.setSubject("測試用2");
		mailHelper.setText( "<H1><font color='RED'>測試2</font></H1>".getBytes() );
		mailHelper.setRetainFlag(YesNo.YES);
		mailHelper.setSuccessFlag(YesNo.NO);
		sysMailHelperService.saveObject(mailHelper);		
		
	}	
	
	@Test
	public void test6() throws Exception {
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);		
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("admin", "password99");
		subject.login(token);
		IOrganizationLogicService organizationLogicService = (IOrganizationLogicService)AppContext.getBean("bsc.service.logic.OrganizationLogicService");
		List<Map<String, Object>> treeData = organizationLogicService.getTreeData("", false, "");
		System.out.println(treeData);
	}
	
	@Test
	public void test5() throws Exception {
		ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService=
				(ISysUploadService<SysUploadVO, TbSysUpload, String>)AppContext.getBean("core.service.SysUploadService");
		SysUploadVO upload = new SysUploadVO();
		upload.setOid("cfe49a36-4a05-47d6-9a67-e3a91035e174");
		DefaultResult<SysUploadVO> result = sysUploadService.findObjectByOid(upload);
		if (result.getValue()==null) {
			return;
		}
		upload = result.getValue();
		System.out.println( new String(upload.getContent(), "utf-8") );
	}
	
	@Test
	public void test4() throws Exception {
		ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService=
				(ISysUploadService<SysUploadVO, TbSysUpload, String>)AppContext.getBean("core.service.SysUploadService");
		SysUploadVO upload = new SysUploadVO();
		upload.setIsFile(YesNo.NO);
		upload.setFileName("");
		upload.setType("TXT");
		upload.setSystem("CORE");
		upload.setSubDir("");
		upload.setShowName("test.txt");
		upload.setContent("TEST-DATA".getBytes("utf-8"));
		sysUploadService.saveIgnoreUK(upload);
	}
	
	@Test
	public void test3() throws Exception {
		SearchValue sv = new SearchValue();
		sv.getParameter().put("name", "SYS");
		sv.getParameter().put("sysId", "SYS");
		PageOf pageOf = new PageOf();
		@SuppressWarnings("unchecked")
		ISysService<SysVO, TbSys, String> sysService=(ISysService<SysVO, TbSys, String>)AppContext.getBean("core.service.SysService");
		QueryResult<List<SysVO>> result = sysService.findGridResult(sv, pageOf);
		if (result.getValue()==null) {
			System.out.println(result.getSystemMessage().getValue());
			return;
		}
		for (SysVO sys : result.getValue()) {
			System.out.println(sys.getSysId() + " = " + sys.getName());
		}
	}
	
	@Test
	public void testOne() throws Exception {
		
		@SuppressWarnings("unchecked")
		ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService=
				(ISysCodeService<SysCodeVO, TbSysCode, String>)AppContext.getBean("core.service.SysCodeService");
		
		Map<String, Object> params = new HashMap<String, Object>();
		List<TbSysCode> searchList = sysCodeService.findListByParams(params);
		if (searchList==null) {
			return;
		}
		for (TbSysCode entity : searchList) {
			System.out.println(entity.getName());
		}
		
		params.clear();
		params.put("account", "admin");
		@SuppressWarnings("unchecked")
		IAccountService<AccountVO, TbAccount, String> accountService=
				(IAccountService<AccountVO, TbAccount, String>)AppContext.getBean("core.service.AccountService");
		DefaultResult<List<TbAccount>> result = accountService.ibatisSelectListByParams(params);
		if (result.getValue()==null) {
			System.out.println(result.getSystemMessage().toString());
			return;
		}
		for (TbAccount account : result.getValue()) {
			System.out.println(account.getAccount());
		}
		
		TbAccount account = new TbAccount();
		account.setAccount("admin");
		DefaultResult<TbAccount> resultOne = accountService.ibatisSelectOneByValue(account);
		if (resultOne.getValue()==null) {
			System.out.println(resultOne.getSystemMessage().toString());
			return;			
		}
		System.out.println(resultOne.getValue().getOid());
		
	}
	
	
}
