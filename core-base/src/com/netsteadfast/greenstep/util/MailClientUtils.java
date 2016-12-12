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

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.YesNo;

public class MailClientUtils {
	private static JavaMailSender mailSender;
	private static ThreadLocal<String> formTL = new ThreadLocal<String>();
	private static ThreadLocal<String> enableTL = new ThreadLocal<String>();
	
	static {
		mailSender = (JavaMailSender)AppContext.getBean("mailSender");
	}
	
	public static String getDefaultFrom() {
		if (formTL.get()==null) {
			formTL.set( SystemSettingConfigureUtils.getMailDefaultFromValue() );			
		}		
		return StringUtils.defaultString(formTL.get());
	}
	
	public static boolean getEnable() {
		if (enableTL.get()==null) {
			enableTL.set( SystemSettingConfigureUtils.getMailEnableValue() );			
		}		
		return StringUtils.defaultString( enableTL.get() ).trim().equals(YesNo.YES);		
	}	
	
	public static void send(
			String from, String to, 
			String subject, String text) throws MailException, Exception {
		send(from, to, null, null, null, null, subject, text);
	}		
	
	public static void send(
			String from, String to, 
			String cc[], 
			String subject, String text) throws MailException, Exception {
		send(from, to, cc, null, null, null, subject, text);
	}	
	
	public static void send(
			String from, String to, 
			String cc[], String bcc[], 
			String subject, String text) throws MailException, Exception {
		send(from, to, cc, bcc, null, null, subject, text);
	}
	
	public static void send(
			String from, String to, 
			String cc, String bcc, 
			String subject, String text) throws MailException, Exception {
		
		String mailCc[] = null;
		String mailBcc[] = null;
		if (!StringUtils.isBlank(cc)) {
			mailCc = cc.split(Constants.ID_DELIMITER);
		}
		if (!StringUtils.isBlank(bcc)) {
			mailBcc = bcc.split(Constants.ID_DELIMITER);
		}
		send(from, to, mailCc, mailBcc, subject, text);
	}
	
	public static void send(
			String from, String to, 
			String cc[], String bcc[], 
			String fileNames[], File files[],
			String subject, String text) throws MailException, Exception {
		
		if (mailSender==null) {
			throw new Exception("null mailSender!");
		}
		if (StringUtils.isBlank(from) || StringUtils.isBlank(to)) {
			throw new Exception("from and to is required!");
		}
		if (fileNames!=null && files!=null) {
			if (fileNames.length != files.length) {
				throw new Exception("File parameter error!");
			}
		}
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, Constants.BASE_ENCODING);
		helper.setFrom(from);
		helper.setTo( to.endsWith(";") ? to.substring(0, to.length()-1) : to );
		helper.setSubject(subject);
		helper.setText(text, true);
		if (null!=cc && cc.length>0) {
			helper.setCc(cc);
		}
		if (null!=bcc && bcc.length>0) {
			helper.setBcc(bcc);
		}
		for (int i=0; fileNames!=null && i<fileNames.length; i++) {
			helper.addAttachment(fileNames[i], files[i]);
		}
		mailSender.send(message);
	}

}
