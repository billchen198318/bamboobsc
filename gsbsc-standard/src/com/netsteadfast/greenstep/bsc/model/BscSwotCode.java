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
package com.netsteadfast.greenstep.bsc.model;

public class BscSwotCode implements java.io.Serializable {
	private static final long serialVersionUID = 3357799447871996143L;
	
	public static final String STRENGTHS_CODE = "S";
	
	public static final String WEAKNESSES_CODE = "W";
	
	public static final String OPPORTUNITIES_CODE = "O";
	
	public static final String THREATS_CODE = "T";
	
	public static String[] CODES = new String[] { 
		STRENGTHS_CODE, WEAKNESSES_CODE, OPPORTUNITIES_CODE, THREATS_CODE };
	
}
