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

import com.netsteadfast.greenstep.sys.BackgroundProgramUserUtils;
import com.netsteadfast.greenstep.util.BusinessProcessManagementUtils;

public class BusinessProcessManagementDeleteTools {
	
	public BusinessProcessManagementDeleteTools() {
		
	}
	
	private static void printDetails() {
		System.out.println("BusinessProcessManagementDeleteTools [true/false] [resource-Id]");
		System.out.println("	true is force delete.");
		System.out.println("	false is normal delete.");
		System.out.println("Example:");
		System.out.println("	BusinessProcessManagementDeleteTools true testProcess");
		System.out.println("	BusinessProcessManagementDeleteTools false testProcess");
	}	
	
	public static void main(String args[]) {
		if (args==null || args.length<1) {
			printDetails();
			System.exit(1);
		}
		boolean force = false;
		String resourceId = args[1];
		String forceStr = StringUtils.defaultString( args[0] ).trim().toLowerCase();
		if ( "true".equals(forceStr) ) {
			force = true;
		}
		try {
			BackgroundProgramUserUtils.login();
			BusinessProcessManagementUtils.deleteDeployment(resourceId, force);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				BackgroundProgramUserUtils.logout();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.exit(1);
	}

}
