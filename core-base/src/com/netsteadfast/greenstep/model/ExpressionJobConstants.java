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
package com.netsteadfast.greenstep.model;

public class ExpressionJobConstants {
	
	/**
	 * 每次都符合時間的符號
	 */
	public static final String DATEOFWEEK_HOUR_MINUTE_ALL = "*";
	
	/**
	 * R-執行中
	 */
	public static final String RUNSTATUS_PROCESS_NOW = "R";
	
	/**
	 * Y-結束
	 */
	public static final String RUNSTATUS_SUCCESS = "Y";
	
	/**
	 * F-錯誤
	 */
	public static final String RUNSTATUS_FAULT = "F";
	
	/**
	 * Y-成功
	 */
	public static final String LOGSTATUS_SUCCESS = "Y";
	
	/**
	 * F-執行失敗
	 */
	public static final String LOGSTATUS_FAULT = "F";
	
	/**
	 * N-沒有執行expression
	 */
	public static final String LOGSTATUS_NO_EXECUTE = "N";
	
	/**
	 * 0-不通知
	 */
	public static final String CONTACT_MODE_NO = "0";
	
	/**
	 * 1-只有失敗fault時通知
	 */
	public static final String CONTACT_MODE_ONLY_FAULT = "1";
	
	/**
	 * 2-只有成功通知
	 */
	public static final String CONTACT_MODE_ONLY_SUCCESS = "2";
	
	/**
	 * 3-成功/失敗都通知
	 */
	public static final String CONTACT_MODE_ALL = "3";
	
}
