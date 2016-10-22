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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.netsteadfast.greenstep.bsc.vo.BscMixDataVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class BscStructTreeObj implements java.io.Serializable {
	private static final long serialVersionUID = -6076521559545104623L;
	private List<VisionVO> visions = new LinkedList<VisionVO>();
	private List<BscMixDataVO> bscMixDatas = new ArrayList<BscMixDataVO>();
	
	public List<VisionVO> getVisions() {
		return visions;
	}
	
	public void setVisions(List<VisionVO> visions) {
		this.visions = visions;
	}

	public List<BscMixDataVO> getBscMixDatas() {
		return bscMixDatas;
	}

	public void setBscMixDatas(List<BscMixDataVO> bscMixDatas) {
		this.bscMixDatas = bscMixDatas;
	}	
	
}
