import java.io.*;
import java.lang.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.netsteadfast.greenstep.base.model.*;
import com.netsteadfast.greenstep.util.*;

// ===========================================================================================

TransactionTemplate transactionTemplate = DataUtils.getTransactionTemplate();
NamedParameterJdbcTemplate jdbcTemplate = DataUtils.getJdbcTemplate();

transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
transactionTemplate.setReadOnly(false);
try {
	transactionTemplate.execute( 
		new TransactionCallback() {
						
			@Override
			public Object doInTransaction(TransactionStatus status) {
				int maxSize = 10;
				String maxEmpId = jdbcTemplate.queryForObject("select max(EMP_ID) from bb_employee", new HashMap<String, Object>(), String.class);
				String empId = "";
				if ( StringUtils.isBlank(maxEmpId) ) {
					maxEmpId = StringUtils.leftPad(maxEmpId, maxSize, "0");
				}
				if ( NumberUtils.isNumber(maxEmpId) ) {
					String numStr = (NumberUtils.toInt(maxEmpId) + 1)+"";
					if (numStr.length() <= maxEmpId.length()) {
						empId = StringUtils.leftPad(numStr, maxEmpId.length(), "0");
					} else {
						empId = StringUtils.leftPad(numStr, maxSize, "0");		
					}		
					if (empId.length()>maxSize) {
						empId = "";
					}
				}
				if (StringUtils.isBlank(empId)) {
					empId = SimpleUtils.createRandomString(maxSize);
				}
				
				Map<String, Object> accParamMap = new HashMap<String, Object>();
				accParamMap.put("oid", SimpleUtils.getUUIDStr());
				accParamMap.put("account", account);
				accParamMap.put("password", transPassword);
				accParamMap.put("onJob", YesNo.YES);
				accParamMap.put("cuserid", "admin");
				accParamMap.put("cdate", new Date());
				jdbcTemplate.update("insert into tb_account(OID, ACCOUNT, PASSWORD, ON_JOB, CUSERID, CDATE) values(:oid, :account, :password, :onJob, :cuserid, :cdate)", accParamMap);
				
				Map<String, Object> uroleParamMap = new HashMap<String, Object>();
				uroleParamMap.put("oid", SimpleUtils.getUUIDStr());
				uroleParamMap.put("role", "BSC_STANDARD");
				uroleParamMap.put("account", account);
				uroleParamMap.put("description", account + " 's role, create by LDAP login!");
				uroleParamMap.put("cuserid", "admin");
				uroleParamMap.put("cdate", new Date());
				jdbcTemplate.update("insert into tb_user_role(OID, ROLE, ACCOUNT, DESCRIPTION, CUSERID, CDATE) values(:oid, :role, :account, :description, :cuserid, :cdate)", uroleParamMap);
				
				Map<String, Object> empParamMap = new HashMap<String, Object>();
				empParamMap.put("oid", SimpleUtils.getUUIDStr());
				empParamMap.put("account", account);
				empParamMap.put("empId", empId);
				empParamMap.put("fullName", "NAME - create by LDAP login!");
				empParamMap.put("jobTitle", "TITLE - create by LDAP login!");
				empParamMap.put("cuserid", "admin");
				empParamMap.put("cdate", new Date());
				jdbcTemplate.update("insert into bb_employee(OID, ACCOUNT, EMP_ID, FULL_NAME, JOB_TITLE, CUSERID, CDATE) values(:oid, :account, :empId, :fullName, :jobTitle, :cuserid, :cdate)", empParamMap);
				
				return null;
			}
						
		}
	);
} catch (Exception e) {
	throw e;
}

// ===========================================================================================
