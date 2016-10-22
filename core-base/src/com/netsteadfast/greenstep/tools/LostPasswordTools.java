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
package com.netsteadfast.greenstep.tools;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.po.hbm.TbAccount;
import com.netsteadfast.greenstep.service.IAccountService;
import com.netsteadfast.greenstep.util.MailClientUtils;
import com.netsteadfast.greenstep.vo.AccountVO;

@SuppressWarnings("unchecked")
public class LostPasswordTools {
	private static IAccountService<AccountVO, TbAccount, String> accountService;
	
	static {
		accountService = (IAccountService<AccountVO, TbAccount, String>)
				AppContext.getBean("core.service.AccountService");		
	}
	
	public LostPasswordTools() {
		
	}
	
	private static void printDetails() {
		System.out.println("LostPasswordTools [OP] [ACCOUNT] [MAIL]");
		System.out.println("OP:");
		System.out.println("	1: show new password.");
		System.out.println("	2: send new password to recive mail.");
		System.out.println(" ");
		System.out.println("ACCOUNT:");
		System.out.println("	input user's account id.");
		System.out.println(" ");
		System.out.println("Example:");
		System.out.println("	LostPasswordTools 1 admin");
		System.out.println("	LostPasswordTools 2 admin chen.xin.nien@gmail.com");
	}
	
	private static void generateNewPassword(String operation, 
			String account, String mail) throws ServiceException, Exception {
		String newPassword = accountService.generateNewPassword(account);
		if ("1".equals(operation)) {
			System.out.println("===================================");
			System.out.println("new password: " + newPassword);
		} else {
			sendToMail(mail, account, newPassword);
		}
	}
	
	private static void sendToMail(String mail, String account, String password) throws Exception {
		System.out.println("send new password mail...");
		String subject = "Hi " + account + " new password for bambooBSC!";
		String text = "This is bambooBSC generate new password tools<BR/>";
		text += "Account: " + account + "<BR/>";
		text += "Password: " + password + "<BR/>";
		MailClientUtils.send(MailClientUtils.getDefaultFrom(), mail, subject, text);
	}
	
	public static void main(String args[]) {
		if (args==null || args.length<2) {
			printDetails();
			System.exit(1);
		}
		String op = StringUtils.defaultString(args[0]).trim();
		String account = StringUtils.defaultString(args[1]).trim();
		String mail = "";
		if ( "".equals(op) || "".equals(account) ) {
			printDetails();
			System.exit(1);
		}
		if ( "2".equals(op) && args.length!=3 ) {
			printDetails();
			System.exit(1);
		}
		if ( "2".equals(op) ) {
			mail = StringUtils.defaultString(args[2]).trim();
		}
		System.out.println("account: " + account);
		System.out.println("operation: " + op);
		System.out.println("mail: " + mail);
		try {
			generateNewPassword(op, account, mail);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

}
