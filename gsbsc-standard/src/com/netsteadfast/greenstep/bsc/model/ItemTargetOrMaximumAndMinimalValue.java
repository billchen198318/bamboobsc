/* 
 * Copyright 2012-2017 bambooCORE, greenstep of copyright Chen Xin Nien
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

/**
 * bb_perspective, bb_objective, bb_kpi ( TARGET, MAX, MIN ) 欄位的最大與最小值
 * 
 * bb_measure_data( TARGET, ACTUAL ) 欄位的最大與最小值
 *
 */
public class ItemTargetOrMaximumAndMinimalValue {
	
	public static final float MAX = 9999999.00f;
	public static final float MIN = -9999999.00f;
	
	public static float get(float value) {
		if (value > MAX) {
			return MAX;
		}
		if (value < MIN) {
			return MIN;
		}
		return value;
	}
	
}
